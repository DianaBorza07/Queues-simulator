import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame{
    private JFrame frame;
    private JTextField tfSimulationTime;
    private JTextField tfClients;
    private JTextField tfQueues;
    private JTextField tfMinArrivalTime;
    private JTextField tfMaxArrivalTime;
    private JTextField tfMinProcessTime;
    private JTextField tfMaxProcessTime;
    private JButton btnStart;
    private JTextArea taDisplay;
    private JPanel infoPanel;
    private JPanel displayPanel;


    public View(){
        frame = new JFrame("Queues Simulator");
        frame.setBounds(100, 100, 1058, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 2, 5, 10));
        frame.setVisible(true);

        infoPanel = new JPanel();
        infoPanel.setBackground(new Color(255, 228, 225));
        frame.getContentPane().add(infoPanel);
        infoPanel.setLayout(new GridLayout(0, 2, 5, 10));
        infoPanel.setVisible(true);

        JLabel lblSimulation = new JLabel("Simulation Time");
        lblSimulation.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(lblSimulation);

        tfSimulationTime = new JTextField();
        tfSimulationTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(tfSimulationTime);
        tfSimulationTime.setColumns(10);

        JLabel lblClients = new JLabel("Number of clients");
        lblClients.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(lblClients);

        tfClients = new JTextField();
        tfClients.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(tfClients);
        tfClients.setColumns(10);

        JLabel lblQueues = new JLabel("Number of queues");
        lblQueues.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(lblQueues);

        tfQueues = new JTextField();
        tfQueues.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(tfQueues);
        tfQueues.setColumns(10);

        JLabel lblMinArrivalTime = new JLabel("Min arrival time");
        lblMinArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(lblMinArrivalTime);

        tfMinArrivalTime = new JTextField();
        tfMinArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(tfMinArrivalTime);
        tfMinArrivalTime.setColumns(10);

        JLabel lblMaxArrivalTime = new JLabel("Max arrival time");
        lblMaxArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(lblMaxArrivalTime);

        tfMaxArrivalTime = new JTextField();
        tfMaxArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(tfMaxArrivalTime);
        tfMaxArrivalTime.setColumns(10);

        JLabel lblMinProcessTime = new JLabel("Min processing time");
        lblMinProcessTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(lblMinProcessTime);

        tfMinProcessTime = new JTextField();
        tfMinProcessTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(tfMinProcessTime);
        tfMinProcessTime.setColumns(10);

        JLabel lblMaxProcessTime = new JLabel("Max processing time");
        lblMaxProcessTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(lblMaxProcessTime);

        tfMaxProcessTime = new JTextField();
        tfMaxProcessTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(tfMaxProcessTime);
        tfMaxProcessTime.setColumns(10);

        btnStart = new JButton("Start");
        btnStart.setBackground(new Color(230, 230, 250));
        btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
        infoPanel.add(btnStart);

        displayPanel = new JPanel();
        displayPanel.setBackground(new Color(255, 228, 225));
        frame.getContentPane().add(displayPanel);
        displayPanel.setLayout(new GridLayout(1, 0, 0, 0));
        displayPanel.setVisible(true);

        taDisplay = new JTextArea();
        taDisplay.setBackground(new Color(255, 250, 250));
        taDisplay.setFont(new Font("Tahoma", Font.PLAIN, 14));
        taDisplay.setEditable(false);
        taDisplay.setLineWrap(true);
        taDisplay.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(taDisplay);
        displayPanel.add(scrollPane);
    }

    public String getTfSimulationTime() {
        return tfSimulationTime.getText();
    }

    public String getTfClients() {
        return tfClients.getText();
    }

    public String getTfQueues() {
        return tfQueues.getText();
    }

    public String getTfMinArrivalTime() {
        return tfMinArrivalTime.getText();
    }

    public String getTfMaxArrivalTime() {
        return tfMaxArrivalTime.getText();
    }

    public String getTfMinProcessTime() {
        return tfMinProcessTime.getText();
    }

    public String getTfMaxProcessTime() {
        return tfMaxProcessTime.getText();
    }

    public String getTaDisplay() {
        return taDisplay.getText();
    }

    public void setTaDisplay(String taDisplay) {
        this.taDisplay.setText(taDisplay);
    }

    public void appendTaDisplay(String taDisplay){
        this.taDisplay.append(taDisplay);
    }

    void addStartListener(ActionListener a){
        this.btnStart.addActionListener(a);
    }
}
