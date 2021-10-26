package com.university.enrollment.model.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassDetailsRequest {

    @ApiModelProperty(notes = "name of the class")
    private String name;
    @ApiModelProperty(notes = "credits for the class")
    private int credits;
    @ApiModelProperty(notes = "description of the class")
    private String description;

}
