package com.university.enrollment.model.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateRequest {

    @ApiModelProperty(notes = "id the user")
    private long id;
    @ApiModelProperty(notes = "className the user")
    private String className;
    @ApiModelProperty(notes = "semester the user")
    private String semester;
}
