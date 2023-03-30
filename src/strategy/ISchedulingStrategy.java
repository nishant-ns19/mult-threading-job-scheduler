package strategy;

import model.Job;

public interface ISchedulingStrategy {
    Boolean addJob(Job job);
    Job getNextJob();
}
