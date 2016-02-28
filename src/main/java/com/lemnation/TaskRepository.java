package com.lemnation;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Muhammad on 2/28/2016.
 */
public interface TaskRepository extends MongoRepository<Task, Integer> {
    public Task findById(Integer id);
}
