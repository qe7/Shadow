package me.shae.shadow.utils;

import java.util.concurrent.TimeUnit;

/**
 * High-precision timing utility using nanosecond resolution.
 * Provides elapsed time checking with configurable time units.
 */
public class Stopwatch {

    // Last recorded timestamp in nanoseconds
    private long lastNS = System.nanoTime();

    /**
     * Checks if specified time has elapsed without resetting the timer.
     *
     * @param time Duration to check
     * @param unit TimeUnit of the duration
     * @return true if the time has elapsed
     */
    public boolean hasTimeElapsed(long time, TimeUnit unit) {
        return hasTimeElapsed(time, unit, false);
    }

    /**
     * Checks if specified time has elapsed with optional reset.
     *
     * @param time  Duration to check
     * @param unit  TimeUnit of the duration
     * @param reset Whether to reset the timer if elapsed
     * @return true if the time has elapsed
     */
    public boolean hasTimeElapsed(long time, TimeUnit unit, boolean reset) {
        long elapsedNS = System.nanoTime() - lastNS;
        long timeoutNS = unit.toNanos(time);

        if (elapsedNS >= timeoutNS) {
            if (reset) {
                reset();
            }
            return true;
        }
        return false;
    }

    /**
     * Resets the stopwatch to current time.
     */
    public void reset() {
        lastNS = System.nanoTime();
    }

    /**
     * Gets elapsed time since last reset in specified units.
     *
     * @param unit Desired output time unit
     * @return Elapsed time in requested units
     */
    public long getTime(TimeUnit unit) {
        long elapsedNS = System.nanoTime() - lastNS;
        return unit.convert(elapsedNS, TimeUnit.NANOSECONDS);
    }
}