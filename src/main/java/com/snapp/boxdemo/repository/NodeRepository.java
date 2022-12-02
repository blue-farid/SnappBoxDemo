package com.snapp.boxdemo.repository;

import com.snapp.boxdemo.model.entity.node.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, Long> {
}

