package strategy;

import model.Job;

import java.util.concurrent.ConcurrentSkipListSet;

public class SJFStrategy implements ISchedulingStrategy {

    final private ConcurrentSkipListSet<Job> priorityQueue;

    public SJFStrategy() {
        this.priorityQueue = new ConcurrentSkipListSet<>((o1, o2) -> {
            if (o1.getDurationInMillis() == o2.getDurationInMillis()) {
                return Integer.compare(o1.getPriority(), o2.getPriority());
            }
            return Long.compare(o1.getDurationInMillis(), o2.getDurationInMillis());
        });
    }

    public SJFStrategy(final ConcurrentSkipListSet<Job> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    @Override
    public Boolean addJob(Job job) {
        return priorityQueue.add(job);
    }

    @Override
    public Job getNextJob() {
        return priorityQueue.pollFirst();
    }
}
