package me.xerces.eventbus.test.utils;

import java.util.concurrent.TimeUnit;

/**
 * Time manager used for waiting for a set period of time or checking how long since the last update
 * @author Xerces
 */
public class TimeManager {

    public TimeManager()
    {
        updateLastMS();
    }

    /**
     * The last updated time set from {@link TimeManager#updateLastMS()}
     */
    private long lastMS = 0;

    /**
     * Update the timer
     */
    public synchronized void updateLastMS()
    {
        this.lastMS = System.nanoTime();
    }

    /**
     * Check if the time has elapsed since the last {@link TimeManager#updateLastMS()}
     * @param timeInMiliseconds time in miliseconds since the last update
     * @return true if the time has elapsed false if it hasnt
     */
    public synchronized boolean hasTimeElapsedMS(long timeInMiliseconds)
    {
        return TimeUnit.MILLISECONDS.convert(lastMS, TimeUnit.NANOSECONDS) + timeInMiliseconds <= TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    /**
     * Check if the time has elapsed since the last {@link TimeManager#updateLastMS()}
     * @param nanoSeconds time in nanoseconds since the last update
     * @return true if the time has elapsed false if it hasnt
     */
    public synchronized boolean hasTimeElapsedNS(long nanoSeconds)
    {
        return lastMS + nanoSeconds <= System.nanoTime();
    }

    /**
     * Returns the elapsed amount of time since the last update {@link TimeManager#updateLastMS()} in {@link java.util.concurrent.TimeUnit#MILLISECONDS}
     * @return the elapsed amount of time in {@link java.util.concurrent.TimeUnit#MILLISECONDS}
     */
    public synchronized long getElapsedTime()
    {
        return getElapsedTime(TimeUnit.MILLISECONDS);
    }

    /**
     * Returns the elapsed amount of time since the last update {@link TimeManager#updateLastMS()}
     * @param timeUnit The {@link java.util.concurrent.TimeUnit} that you want the elapsed time converted too
     * @return the elapsed amount of time
     */
    public synchronized long getElapsedTime(TimeUnit timeUnit)
    {
        return timeUnit.convert(System.nanoTime() - lastMS, TimeUnit.NANOSECONDS);
    }

}
