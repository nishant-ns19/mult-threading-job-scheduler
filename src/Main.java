import model.Job;
import model.SleepingJob;
import service.SchedulingService;
import strategy.SJFStrategy;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        SchedulingService schedulingService = new SchedulingService(2, new SJFStrategy());
        Job jobs[] = {
                new SleepingJob("1", 10, 0, 10000),
                new SleepingJob("2", 10, 0, 2000),
                new SleepingJob("3", 10, 3, 4000),
                new SleepingJob("4", 10, 1, 4000)
        };
        ArrayList<Callable<Void>> addjobs=new ArrayList<>();
        for(int i=0;i<4;i++) {
            int finalI = i;
            addjobs.add(() -> {
                schedulingService.addJob(jobs[finalI]);
                return null;
            });
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4);
       executorService.invokeAll(addjobs);
       executorService.shutdown();
       Thread.sleep(15000);
       schedulingService.stopScheduling();
    }

}