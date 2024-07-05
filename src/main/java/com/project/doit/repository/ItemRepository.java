package com.project.doit.repository;

import com.project.doit.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findAllBytaskId(Long taskId);
}
