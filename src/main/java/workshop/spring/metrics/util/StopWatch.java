package workshop.spring.metrics.util;

import java.util.concurrent.TimeUnit;

public class StopWatch {

    final long started;

    private StopWatch() {
        started = System.nanoTime();
    }

    public long elapsed() {
        return System.nanoTime() - started;
    }

    public long elapsedInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(elapsed());
    }

    public static StopWatch time() {
        return new StopWatch();
    }
}
