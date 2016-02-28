package com.lemnation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class TodoApplication implements CommandLineRunner {
    ArrayList<Task> tasks = new ArrayList<>();
    AtomicInteger counter = new AtomicInteger();

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public int addTask(@RequestBody Task newTask) {

        newTask.setId(counter.getAndIncrement());
        tasks.add(newTask);

//        Clients.update(newTask);

        jdbcTemplate.update("INSERT INTO tasks(string, done, id) VALUES (?,?,?)",
                newTask.getString(), newTask.isDone(), newTask.getId());

        return newTask.getId();
    }

    @RequestMapping(value = "/tasks/remove", method = RequestMethod.POST)
    public void removeTask(@RequestBody Task newTask) {

        tasks.removeIf(task -> task.getId() == newTask.getId());

//        Clients.update(newTask);

        jdbcTemplate.update("DELETE FROM tasks WHERE id=?", newTask.getId());
    }

    @RequestMapping(value = "/tasks/update", method = RequestMethod.POST)
    public void updateTask(@RequestBody Task newTask) {

        // Find the task whose id is equal to newTask id and then replace that task with newTask
        tasks.stream()
                .filter(task -> task.getId() == newTask.getId())
                .findFirst().ifPresent(oldTask -> {
            tasks.set(tasks.indexOf(oldTask), newTask);
        });

//        Clients.update(newTask);

        jdbcTemplate.update("UPDATE tasks SET string=?, done=? WHERE id=?",
                newTask.getString(), newTask.isDone(), newTask.getId());
    }

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS tasks(string VARCHAR(255), done BOOLEAN, id INT, PRIMARY KEY (id))");

        jdbcTemplate.query("SELECT string, done, id FROM tasks",
                (rs, rowNum) -> new Task(rs.getString("string"), rs.getBoolean("done"), rs.getInt("id")))
                .forEach(task -> tasks.add(task.getId(), task));

//        Find highest value of Id in tasks list and if list is not empty assign highest value to counter
        tasks.stream().max(Comparator.comparing(Task::getId))
                .ifPresent(task -> counter.set(task.getId()));

//        TODO: check counter
    }

}