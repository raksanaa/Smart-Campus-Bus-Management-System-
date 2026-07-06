package com.project.eveningshuttle.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    private int suid;

    public StudentEntity() {
    }

    public StudentEntity(int suid) {
        this.suid = suid;
    }

    public int getSuid() {
        return suid;
    }

    public void setSuid(int suid) {
        this.suid = suid;
    }
}
