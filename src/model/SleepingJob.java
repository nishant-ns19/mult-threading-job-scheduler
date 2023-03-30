package model;

public class SleepingJob extends Job {
    private final long sleepForInMillis;

    public SleepingJob(String jobId, long durationInMillis, int priority, long sleepForInMillis) {
        super(jobId, durationInMillis, priority);
        this.sleepForInMillis = sleepForInMillis;
    }

    @Override
    public void process() throws InterruptedException {
        System.out.println("Job: " + getJobId() + " going to sleep for " + sleepForInMillis + " milliseconds.");
        Thread.sleep(sleepForInMillis);
        System.out.println("Job: " + getJobId() + " is completed.");
    }
}
