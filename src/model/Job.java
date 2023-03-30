package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Job {
    final private String jobId;
    final private long durationInMillis;
    final private int priority; // P0 > P1

    public abstract void process() throws Exception;

}
