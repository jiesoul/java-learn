package jcip;

import java.util.concurrent.*;

public class CancellingExecutor extends ThreadPoolExecutor {

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    protected <T>RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof  CancellableTask) {
            return ((CancellableTask<T>) callable).newTask();
        } else {
            return super.newTaskFor(callable);
        }
    }
}
