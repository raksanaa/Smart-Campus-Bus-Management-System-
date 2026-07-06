package com.project.eveningshuttle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.eveningshuttle.model.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
}
