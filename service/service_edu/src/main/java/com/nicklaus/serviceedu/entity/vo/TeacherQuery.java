package com.nicklaus.serviceedu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Teacher查询对象",description = "Teacher查询字段的封装")
@Data
public class TeacherQuery {

    @ApiModelProperty("讲师名称")
    private String name;
    @ApiModelProperty("讲师职称")
    private Integer level;
    @ApiModelProperty("查询开始时间")
    private String begin;
    @ApiModelProperty("查询结束时间")
    private String end;
}
