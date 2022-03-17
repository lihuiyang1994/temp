package demo.docker.temp.util;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * lihui
 * 2022/3/17
 * ComparableFutureTask
 *
 * @description
 */
public class ComparableFutureTask<V> extends FutureTask<V> implements Comparable<ComparableFutureTask<V>> {


    private Object object;


    public ComparableFutureTask(Callable<V> callable) {
        super(callable);
        object = callable;
    }

    public ComparableFutureTask(Runnable runnable, V result) {
        super(runnable, result);
        object = runnable;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(ComparableFutureTask<V> o) {
        if (this == o) {
            return 0;
        }
        if (o == null) {
            return -1; // high priority
        }
        if (object != null && o.object != null) {
            if (object.getClass().equals(o.object.getClass())) {
                if (object instanceof Comparable) {
                    return ((Comparable) object).compareTo(o.object);
                }
            }
        }
        return 0;
    }
}
