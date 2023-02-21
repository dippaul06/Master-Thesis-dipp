package magma.system;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public enum Executor implements java.util.concurrent.Executor {
    fixed(Executors.newFixedThreadPool(122));

    private final ExecutorService pool;
    Executor(ExecutorService _pool) { pool = _pool; }
    public void execute(Runnable command) { pool.execute(command);}
    public void shutdown() { pool.shutdown(); }
    public List<Runnable> shutdownNow() { return pool.shutdownNow(); }
    public boolean isShutdown() { return pool.isShutdown(); }
    public boolean isTerminated() { return pool.isTerminated(); }
    public <T> Future<T> submit(Callable<T> task) { return pool.submit(task); }
    public <T> Future<T> submit(Runnable task, T result) { return pool.submit(task,result); }
    public Future<?> submit(Runnable task) { return pool.submit(task); }
    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        return pool.awaitTermination(timeout, unit);
    }
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        return pool.invokeAll(tasks);
    }
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return pool.invokeAll(tasks, timeout, unit);
    }
    public  <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return pool.invokeAny(tasks);
    }
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return pool.invokeAny(tasks, timeout, unit);
    }
}