package com.impact.brain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_type")
public class TransactionType {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Lob
    @Column(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}