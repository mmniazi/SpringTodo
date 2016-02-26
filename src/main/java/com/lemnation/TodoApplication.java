package com.lemnation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class TodoApplication implements CommandLineRunner {
    ArrayList<Task> tasks = new ArrayList<>();

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public void updateTasks(@RequestBody ArrayList<Task> newTasks) {
        tasks = newTasks;
        jdbcTemplate.execute("TRUNCATE TABLE tasks");
        tasks.forEach(task -> jdbcTemplate.update("INSERT INTO tasks(string, done) VALUES (?,?)",
                task.getString(), task.isDone()));
    }

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS tasks(string VARCHAR(255), done BOOLEAN)");

        jdbcTemplate.query("SELECT string, done FROM tasks",
                (rs, rowNum) -> new Task(rs.getString("string"), rs.getBoolean("done")))
                .forEach(task -> tasks.add(task));

        tasks.forEach(System.out::println);
    }

}