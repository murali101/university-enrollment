package com.university.enrollment.model.domain;

import com.university.enrollment.model.entities.RoleTypes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    @ApiModelProperty(notes = "userName, use to call the university apis under regular user role")
    private String userName;
    @ApiModelProperty(notes = "first name of the user")
    private String firstName;
    @ApiModelProperty(notes = "last name of the user")
    private String lastName;
    @ApiModelProperty(notes = "semester of the user")
    private String semester;
    @ApiModelProperty(notes = "password, use to call the university apis under regular user role")
    private String password;
    @ApiModelProperty(notes = "ROLE used to define the api access level")
    private RoleTypes role = RoleTypes.ROLE_USER;
    @ApiModelProperty(notes = "user class name, seperated by comas if more than one class")
    private String className;
    @ApiModelProperty(notes = "nationality of the user")
    private String nationality;

    @Override
    public String toString() {
        return "UserRequest{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", semester='" + semester + '\'' +
                ", role=" + role +
                ", className='" + className + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
