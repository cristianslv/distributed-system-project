package server;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HealthCheckUsecases {
    static ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(2);
    static ScheduledFuture<?> scheduledFuture;

    private HealthCheckUsecases() {}

    public static void execute(RemoteObjectRegistryUsecases remoteObjectRegistryUsecases, RemoteObject remoteObject) {
        var healthCheckTask = new ElectionUsecases(remoteObjectRegistryUsecases, remoteObject);

        scheduledFuture = threadPoolExecutor.scheduleAtFixedRate(healthCheckTask, 0, 15, TimeUnit.SECONDS);
    }

    public static void stop() {
        scheduledFuture.cancel(true);
    }
}
