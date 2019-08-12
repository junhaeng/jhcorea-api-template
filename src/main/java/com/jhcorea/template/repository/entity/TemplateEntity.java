package com.jhcorea.template.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "test")
public class TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increase라서 auto했는데 아님
    private Long id;

    @Column(length = 45, nullable = true)
    private String message;

    public TemplateEntity() {

    }

    public TemplateEntity(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
