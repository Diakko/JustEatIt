package com.example.justeatit;

/**
 * The class StepCounter represents steps of our app.
 * @author  Samuel Ahjoniemi
 */
public class StepCounter {
    /**
     * int steps is variable for steps in StepCounter
     */
    private int steps;

    /**
     * Constructor of StepCounter class
     * @param start This is parameter for StepCounter and sets value of steps.
     */
    public StepCounter(int start) {
        steps = start;
    }

    /**
     * step setter method
     * @param luku This parameter sets steps.
     */
    public void setValue(int luku) {
        this.steps = luku;
    }

    /**
     * get the step count.
     * @return int This returns current int value of steps.
     */

    public int stepsNow() {
        return steps;
    }


    /**
     * This method is used to add one step to counter.
     */

    public void addStep() {
        steps++;
    }

    /**
     * This method is used to reset step count to 0.
     */
    public void reset() {
        steps = 0;
    }

}
