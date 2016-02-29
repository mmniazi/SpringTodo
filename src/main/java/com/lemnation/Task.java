package com.lemnation;


import org.springframework.data.annotation.Id;

/**
 * Created by Muhammad on 2/26/2016.
 */
public class Task {

    @Id
    private String id;

    private String string;
    private boolean done;

    public Task() {

    }

    public Task(String string, boolean done, String id) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
