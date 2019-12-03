package com.example.justeatit;

/**
 * (c) Samuel Ahjoniemi
 */
public class StepCounter {

    private int steps;

    public StepCounter(int start) {
        steps = start;
    }
    public void setValue(int luku) {
        this.steps = luku;
    }

    public int stepsNow() {
        return steps;
    }

    public void addStep() {
        steps++;
    }
    public void reset() {
        steps = 0;
    }

}
