package com.lexicalscope.svm.vm;

/**
 * Search limit that ensures that the execution does not exceed a specified time.
 */
public class TimerSearchLimit implements SearchLimits {
    /**
     * Time in milliseconds when the execution started.
     */
    private long startTime;

    /**
     * Threshold in milliseconds that the execution should not exceed.
     */
    private final long thresholdMillis;

    private int count;

    public TimerSearchLimit(final long thresholdMillis) {
        this.thresholdMillis = thresholdMillis;
        reset();
    }

    public static TimerSearchLimit limitByTime(final int seconds) {
        return new TimerSearchLimit(seconds * 1000);
    }

    @Override
    public boolean withinLimits() {
       if(count++ % 1000 == 0) {
          final long currentTime = System.currentTimeMillis();
          return currentTime - startTime <= thresholdMillis;
       }
       return true;
    }

    @Override
    public void searchedState() {
    }

    @Override
    public void reset() {
       count = 0;
       startTime = System.currentTimeMillis();
    }
}
