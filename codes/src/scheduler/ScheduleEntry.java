package scheduler;

public class ScheduleEntry {
    private int jobID;
    private int serverID;
    private double start;
    private double end;
    private int freq;

    /* ================ CONSTRUCTORS ================ */
    public ScheduleEntry(int jobID, int serverID, double start, double end, int freq){
        this.jobID = jobID;
        this.serverID = serverID;
        this.start = start;
        this.end = end;
        this.freq = freq;
    }

    /* ================ GETTERS ================ */
    public int getJobID() { return jobID; }
    public int getServerID() { return serverID; }
    public double getStart() { return start; }
    public double getEnd() { return end; }
    public int getFreq() { return freq; }

    /* ================ PRINTERS ================ */
    public void print(){
        System.out.printf("  %-5d | %-8d | %-5.1f | %-5.1f | %-9d  \n", jobID, serverID, start, end, freq);
    }
}