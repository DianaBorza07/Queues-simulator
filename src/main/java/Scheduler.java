import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoOfServers;
    private int maxNoOfTasksPerServer;

    public Scheduler(int maxNoOfServers, int maxNoOfTasksPerServer) {
        this.maxNoOfServers = maxNoOfServers;
        this.maxNoOfTasksPerServer = maxNoOfTasksPerServer;
        this.servers = Collections.synchronizedList(new ArrayList<Server>());
        int n = 0;
        while(maxNoOfServers > 0){
            this.servers.add(new Server(maxNoOfTasksPerServer,n));
            Thread thread = new Thread(this.servers.get(n));
            thread.start();
            n++;
            maxNoOfServers--;
        }
    }

    public List<Server> getServers() {
        return servers;
    }

    public void dispatchTask(Task client) throws InterruptedException {
        int minWaitingTime = Integer.MAX_VALUE;
        int idx = 0;
        for(int i=0; i<this.servers.size(); i++){
            if(this.servers.get(i).getWaitingPeriod().get() < minWaitingTime) {
                minWaitingTime = this.servers.get(i).getWaitingPeriod().get();
                idx= i;
            }
        }
        this.servers.get(idx).addTask(client);

    }
}
