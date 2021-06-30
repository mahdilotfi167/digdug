package ir.ac.kntu;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static Executor executor;
    static {
        executor = Executors.newSingleThreadExecutor();
    }
    public static void execute(Runnable task) {
        executor.execute(task);
    }
}
