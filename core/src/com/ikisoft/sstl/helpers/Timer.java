package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Max on 28.5.2017.
 */

public class Timer {

    private boolean timerStarted, toggled;
    private long nanoSeconds;
    private long startTime;

    public Timer() {

        timerStarted = false;
        nanoSeconds = 0;
        startTime = 0;
        toggled = false;
    }

    public boolean waitFor(long x) {

        if (!timerStarted) {
            nanoSeconds = x * 1000000000L;
            startTime = TimeUtils.nanoTime();
            timerStarted = true;
        }
        if (TimeUtils.timeSinceNanos(startTime) > nanoSeconds) {
            return true;
        }
        return false;
    }

    public boolean doSomethingFor(long x){

        if (!timerStarted) {
            nanoSeconds = x * 1000000000L;
            startTime = TimeUtils.nanoTime();
            timerStarted = true;
        }
        if (TimeUtils.timeSinceNanos(startTime) > nanoSeconds) {
            return false;
        }
        return true;

    }

    public boolean doSomethingEvery(long x) {

        if (!timerStarted) {
            nanoSeconds = x * 1000000000L;
            startTime = TimeUtils.nanoTime();
            timerStarted = true;
        }
        if (TimeUtils.timeSinceNanos(startTime) > nanoSeconds) {
            return true;
        }
        return false;

    }

    public boolean toggleSomethingEvery(long x) {

        if (!timerStarted) {
            nanoSeconds = x * 1000000000L;
            startTime = TimeUtils.nanoTime();
            timerStarted = true;
        }else if (TimeUtils.timeSinceNanos(startTime) > nanoSeconds) {

            if(!toggled){
                toggled = true;
            } else {
                toggled = false;
            }

            startTime = TimeUtils.nanoTime();

            return toggled;
        }

        return toggled;

    }

    public void resetTime() {
        startTime = 0;
        timerStarted = false;
    }
}
