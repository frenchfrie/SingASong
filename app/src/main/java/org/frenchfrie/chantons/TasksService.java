package org.frenchfrie.chantons;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TasksService {

    private static TasksService INSTANCE;

    public static TasksService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TasksService();
        }
        return INSTANCE;
    }

    private ExecutorService executor;

    private TasksService() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public <V> Future<V> submitTask(Callable<V> task) {
        return executor.submit(task);
    }

}
