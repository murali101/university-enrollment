package com.university.enrollment.model.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SemesterRequest {

    @ApiModelProperty(notes = "name of the semester")
    private String name;
    @ApiModelProperty(notes = "semester description")
    private String description;

}
