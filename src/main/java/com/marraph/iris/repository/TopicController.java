package com.marraph.iris.repository;

import com.marraph.iris.model.task.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicController extends JpaRepository<Topic, Integer> {
}