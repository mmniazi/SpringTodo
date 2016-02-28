package com.lemnation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class TodoApplication implements CommandLineRunner {
    @Autowired
    private TaskRepository repository;

    ArrayList<Task> tasks = new ArrayList<>();
    AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        tasks.addAll(repository.findAll());

//        Find highest value of Id in tasks list and if list is not empty assign highest value to counter
        tasks.stream().max(Comparator.comparing(Task::getId))
                .ifPresent(task -> counter.set(task.getId()));
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @RequestMapping(value = "/tasks/add", method = RequestMethod.POST)
    public int addTask(@RequestBody Task insertedTask) {

        insertedTask.setId(counter.getAndIncrement());
        tasks.add(insertedTask);

        repository.insert(insertedTask);

//        Clients.update(insertedTask);

        return insertedTask.getId();
    }

    @RequestMapping(value = "/tasks/remove", method = RequestMethod.POST)
    public void removeTask(@RequestBody Task removedTask) {

        tasks.removeIf(task -> task.getId() == removedTask.getId());

//        Clients.update(removedTask);

        repository.delete(removedTask);
    }

    @RequestMapping(value = "/tasks/update", method = RequestMethod.POST)
    public void updateTask(@RequestBody Task updatedTask) {

        // Find the task whose id is equal to updatedTask id and then replace that task with updatedTask
        tasks.stream()
                .filter(task -> task.getId() == updatedTask.getId())
                .findFirst().ifPresent(oldTask ->
                tasks.set(tasks.indexOf(oldTask), updatedTask)
        );

//        Clients.update(newTask);

        repository.save(updatedTask);
    }

}