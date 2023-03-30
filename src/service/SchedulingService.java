package service;

import model.Job;
import strategy.ISchedulingStrategy;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SchedulingService {
    private final int numberOfThreads;
    private final ISchedulingStrategy schedulingStrategy;
    private final ArrayList<ExecutorService> threads;

    private final AtomicBoolean startStopService;

    public SchedulingService(int numberOfThreads, ISchedulingStrategy schedulingStrategy) {
        this.numberOfThreads = numberOfThreads;
        this.schedulingStrategy = schedulingStrategy;
        this.startStopService = new AtomicBoolean(true);
        threads = new ArrayList<>(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(i, Executors.newSingleThreadExecutor());
            threads.get(i).execute(processJob(i));
        }
    }

    private Runnable processJob(int i) {
        return () -> {
            while (startStopService.get()) {
                Job jobToProcess = schedulingStrategy.getNextJob();
                if (jobToProcess != null) {
                    System.out.println("Starting job: " + jobToProcess.getJobId() + " using thread: " + i);
                    try {
                        jobToProcess.process();
                        System.out.println("Job: " + jobToProcess.getJobId() + " processed using thread: " + i);
                    } catch (Exception e) {
                        System.out.println("Job: " + jobToProcess.getJobId() + " could not be completed due to: " + e.getMessage());
                    }
                }
            }
        };
    }

    public void stopScheduling() {
        startStopService.compareAndSet(true, false);
        for (int i = 0; i < numberOfThreads; i++) {
            threads.get(i).shutdown();
        }
        System.out.println("Stopping Scheduling.");
    }

    public boolean addJob(Job job) {
        return schedulingStrategy.addJob(job);
    }
}
