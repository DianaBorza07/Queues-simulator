public class Task implements Comparable<Task>{ // Task = Client
    private Integer clientID;
    private Integer queueID;
    private Integer arrivalTime; // timp sosire
    private Integer finishTime; // timp plecare = timp sosire + timp asteptare + timp procesare
    private Integer processingPeriod; // timp procesare
    private Integer waitingPeriod; // timp asteptare

    public Task(int clientID, int arrivalTime, int processingPeriod) {
        this.clientID = clientID;
        this.arrivalTime = arrivalTime;
        this.processingPeriod = processingPeriod;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Integer getQueueID() {
        return queueID;
    }

    public void setQueueID(Integer queueID) {
        this.queueID = queueID;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getFinishTime() {
        return finishTime;
    }

    public void setFinishTime() {
        this.finishTime = this.arrivalTime+this.processingPeriod+this.waitingPeriod;
    }

    public Integer getProcessingPeriod() {
        return processingPeriod;
    }

    public void decrementProcessingPeriod(Integer processingPeriod) {
        this.processingPeriod -= processingPeriod;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(Integer waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public int compareTo(Task o) {
        if(this.arrivalTime > o.arrivalTime)
            return 1;
        else if (this.arrivalTime == o.arrivalTime)
            return 0;
        else return -1;
    }

    public String toString() {
        return "(" + this.clientID + ", " + this.arrivalTime + ", " + this.processingPeriod + ')';
    }
}
