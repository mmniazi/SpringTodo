package com.lemnation;

/**
 * Created by Muhammad on 2/26/2016.
 */
public class Task {
    private String string;
    private boolean done;

    public Task() {

    }

    public Task(String string, boolean done) {
        this.string = string;
        this.done = done;
    }


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
