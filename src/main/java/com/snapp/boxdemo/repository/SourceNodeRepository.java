package com.snapp.boxdemo.repository;

import com.snapp.boxdemo.model.entity.node.SourceNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceNodeRepository extends JpaRepository<SourceNode, Long> {
}
