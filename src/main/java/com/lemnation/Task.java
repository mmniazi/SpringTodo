package com.lemnation;

/**
 * Created by Muhammad on 2/26/2016.
 */
public class Task {
    private String string;
    private boolean done;
    private int id;

    public Task() {

    }

    public Task(String string, boolean done, int id) {
        this.string = string;
        this.done = done;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
