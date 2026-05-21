package com.example.komtekProject.repository;

import com.example.komtekProject.entity.Research;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearchRepository extends JpaRepository<Research, Long> {

    List<Research> findAllByOrderId(Long orderId);
}