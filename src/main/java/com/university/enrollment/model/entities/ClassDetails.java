package com.university.enrollment.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ClassDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="CLASS_ID")
    private int id;

    private String name;

    private int credits;

    private String description;

    //  private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassDetails that = (ClassDetails) o;

        if (id != that.id) return false;
        if (credits != that.credits) return false;
        if (!name.equals(that.name)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + credits;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClassDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", description='" + description + '\'' +
                '}';
    }
}
