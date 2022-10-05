package vlad.skiba.javacore.consoleshop.model;

/**
 * Copyright Vlad Skiba (c) 2021.
 */
public abstract class BaseEntity {

    private Long id;

    public BaseEntity() {

    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}