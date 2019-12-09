package com.company.model;

import com.google.common.base.Objects;

/**
 * Represents a Repository.
 */
public class Repo implements Identifiable {
    private Long id;
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    public Repo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Repo setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repo repo = (Repo) o;
        return Objects.equal(id, repo.id) &&
                Objects.equal(name, repo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }

    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
