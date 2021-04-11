import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable { //Server = coada de asteptare
    private BlockingQueue<Task>  clients;
    private AtomicInteger waitingPeriod; // nr clienti in coada
    private Integer totalWaitingPeriod;
    private Integer totalProcessingPeriod;
    private int serverID;

    public Server(int number, int serverID) {
        this.clients = new ArrayBlockingQueue<Task>(number);
        this.waitingPeriod = new AtomicInteger(0);
        this.totalProcessingPeriod = 0;
        this.totalWaitingPeriod = 0;
        this.serverID = serverID;
    }

    public BlockingQueue<Task> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Task> clients) {
        this.clients = clients;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public Integer getTotalWaitingPeriod() {
        return totalWaitingPeriod;
    }

    public void setTotalWaitingPeriod(Integer totalWaitingPeriod) {
        this.totalWaitingPeriod = totalWaitingPeriod;
    }

    public Integer getTotalProcessingPeriod() {
        return totalProcessingPeriod;
    }

    public void setTotalProcessingPeriod(Integer totalProcessingPeriod) {
        this.totalProcessingPeriod = totalProcessingPeriod;
    }

    public void addTask(Task newTask) throws InterruptedException {
        newTask.setWaitingPeriod(this.waitingPeriod.get());
        newTask.setQueueID(this.serverID);
        this.clients.put(newTask);
        this.waitingPeriod.getAndAdd(newTask.getProcessingPeriod());
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public void run() {
        while (true) {
            try {
                if (!this.clients.isEmpty()) {
                    Task client = this.clients.peek();
                    client.setFinishTime();
                    this.totalWaitingPeriod += client.getFinishTime();
                    this.totalProcessingPeriod += client.getProcessingPeriod();
                    this.waitingPeriod.set(this.waitingPeriod.get()-client.getProcessingPeriod());
                    Thread.sleep(1000*client.getProcessingPeriod());
                    this.clients.take();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString() {
      String s = "Queue " + this.serverID +": ";
      if(this.clients.isEmpty())
          s = s + "Closed";
      else {
          for(Task t : this.clients)
              s = s + t.toString() + ";";
      }
      return s;
    }
}
