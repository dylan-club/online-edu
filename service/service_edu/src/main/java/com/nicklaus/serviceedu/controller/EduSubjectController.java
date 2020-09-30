package com.nicklaus.serviceedu.controller;


import com.nicklaus.commonutils.Result;
import com.nicklaus.serviceedu.entity.vo.FirstSubject;
import com.nicklaus.serviceedu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-11
 */
@RestController
@RequestMapping("/serviceEdu/subject")
//@CrossOrigin
@Api(tags = "课程分类管理")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @PostMapping("addSubject")
    @ApiOperation("添加课程分类")
    public Result addSubject(MultipartFile file){
        eduSubjectService.addSubject(file, eduSubjectService);
        return Result.ok();
    }

    //查询课程分类列表（树形结构）
    @GetMapping("findAllSubject")
    @ApiOperation("查询所有课程分类信息")
    public Result findAllSubject(){
        List<FirstSubject> subjectList = eduSubjectService.findAllSubject();
        return Result.ok().data("items",subjectList);
    }
}

