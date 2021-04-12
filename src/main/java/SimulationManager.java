import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SimulationManager implements Runnable{
    private Integer simulationTime;
    private Integer numberOfTasks;
    private Integer numberOfServers;
    private Integer minArrivalTime;
    private Integer maxArrivalTime;
    private Integer minProcessingTime;
    private Integer maxProcessingTime;
    private Scheduler scheduler;
    private View simulationFrame;
    private List<Task> generatedTasks;
    private boolean isInvoked;
    private FileWriter writer;

    public SimulationManager() throws InterruptedException {
        this.simulationFrame = new View();
        this.simulationFrame.addStartListener(new StartListener());
        while(isInvoked == false ){
            Thread.sleep(1);
        }
        generateNRandomTasks();
        this.scheduler = new Scheduler(this.numberOfServers, this.numberOfTasks);
    }

    private void generateNRandomTasks(){
        this.generatedTasks = Collections.synchronizedList(new ArrayList<Task>());
        for(int i=0; i<this.numberOfTasks; i++){
            int arrivalTime = (int) (Math.random() * (this.maxArrivalTime - this.minArrivalTime) + this.minArrivalTime);
            int processingTime = (int) (Math.random() * (this.maxProcessingTime - this.minProcessingTime) + this.minProcessingTime);
            Task newTask = new Task(i,arrivalTime,processingTime);
            this.generatedTasks.add(newTask);
        }
        Collections.sort(this.generatedTasks); // sortare in functie de arrivalTime
    }
    public void getDataFromUI(){
        this.simulationTime = Integer.parseInt(this.simulationFrame.getTfSimulationTime());
        this.numberOfTasks = Integer.parseInt(this.simulationFrame.getTfClients());
        this.numberOfServers = Integer.parseInt(this.simulationFrame.getTfQueues());
        this.minArrivalTime = Integer.parseInt(this.simulationFrame.getTfMinArrivalTime());
        this.maxArrivalTime = Integer.parseInt(this.simulationFrame.getTfMaxArrivalTime());
        this.minProcessingTime = Integer.parseInt(this.simulationFrame.getTfMinProcessTime());
        this.maxProcessingTime = Integer.parseInt(this.simulationFrame.getTfMaxProcessTime());
    }
    public void run() {
        int currentTime = 0;
        openFile();
        while(currentTime < this.simulationTime){
            for (Iterator<Task> t = generatedTasks.iterator(); t.hasNext();) {
                Task task = t.next();
                if(task.getArrivalTime() == currentTime){
                    try {
                        scheduler.dispatchTask(task);
                        t.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            updateUI(currentTime);
            writeFileLog(currentTime);
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       writeAverageTimes();
       closeFile();
    }

    public void updateUI(int currentTime){
        String message = "Time " + currentTime + "\n";
        message += "Waiting clients :";
        for(Task t: this.generatedTasks) {
            message += t.toString();
            message += ";";
        }
        message += "\n";
        for(Server s: this.scheduler.getServers()) {
            message += s.toString();
            message += "\n";
        }
        this.simulationFrame.setTaDisplay(message);
    }

    public void writeFileLog(int currentTime) {
        String message = "Time " + currentTime + "\n";
        message += "Waiting clients :";
        for(Task t: this.generatedTasks) {
            message += t.toString();
            message += ";";
        }
        message += "\n";
        for(Server s: this.scheduler.getServers()) {
            message += s.toString();
            message += "\n";
        }
        message += "\n";
        try {
            this.writer.append(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile(){
        try {
            writer = new FileWriter("fileLog.txt");
            //writer = new FileWriter("test3.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void closeFile(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double averageWaitingTime(){
        int totalWaitingTime = 0;
        for (Iterator<Server> s = scheduler.getServers().iterator(); s.hasNext();) {
            Server server = s.next();
            totalWaitingTime += server.getTotalWaitingPeriod();
        }
        double averageWaitingTime = ((double)totalWaitingTime /(double) (this.numberOfTasks-this.generatedTasks.size()));
        return Math.round(averageWaitingTime*100.0)/100.0;
    }
    private double averageProcessingTime(){
        int totalProcessingTime = 0;
        for (Iterator<Server> s = scheduler.getServers().iterator(); s.hasNext();) {
            Server server = s.next();
            totalProcessingTime += server.getTotalProcessingPeriod();
        }
        double averageProcessingTime = ((double) totalProcessingTime / (double) (this.numberOfTasks-this.generatedTasks.size()));
        return Math.round(averageProcessingTime*100.0)/100.0;
    }
    private void writeAverageTimes(){
        try {
            writer.append("Average waiting time: "+averageWaitingTime()+"\nAverage processing time: "+averageProcessingTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.simulationFrame.appendTaDisplay("Average waiting time: "+averageWaitingTime()+"\nAverage processing time: "+averageProcessingTime());
    }

    class StartListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(simulationFrame.getTfSimulationTime().isEmpty() || simulationFrame.getTfClients().isEmpty() || simulationFrame.getTfQueues().isEmpty() || simulationFrame.getTfMinArrivalTime().isEmpty()
                    || simulationFrame.getTfMaxArrivalTime().isEmpty() || simulationFrame.getTfMinProcessTime().isEmpty() || simulationFrame.getTfMaxProcessTime().isEmpty())
                JOptionPane.showMessageDialog(null,"Fields can't be empty");
            else {
                isInvoked = true;
                getDataFromUI();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        SimulationManager simulation = new SimulationManager();
        Thread t = new Thread(simulation);
        t.start();
        t.join();
    }

}
