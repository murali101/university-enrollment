package com.university.enrollment.model.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDeleteRequest {

    @ApiModelProperty(notes = "user id")
    private long id;

}
