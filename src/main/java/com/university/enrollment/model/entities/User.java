package com.university.enrollment.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER_ID")
    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String password;

    private LocalDateTime createdTime;

    private RoleTypes role;

    private String nationality;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_CLASS", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "CLASS_ID") })
    private Set<ClassDetails> classDetails;

    @OneToOne
    @JoinTable(name="USER_SEMESTER", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "SEMESTER_ID")})
    private Semester semester;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdTime=" + createdTime +
                ", role=" + role +
                ", nationality='" + nationality + '\'' +
                ", classDetails=" + classDetails +
                '}';
    }
}
