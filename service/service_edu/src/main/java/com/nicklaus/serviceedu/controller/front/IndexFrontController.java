package com.nicklaus.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nicklaus.commonutils.Result;
import com.nicklaus.serviceedu.entity.EduCourse;
import com.nicklaus.serviceedu.entity.EduTeacher;
import com.nicklaus.serviceedu.service.EduCourseService;
import com.nicklaus.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("serviceEdu/indexFront")
@Api(tags = "前台首页面数据管理")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("index")
    @ApiOperation("查询热门课程和名师")
    public Result index(){
        //查询前8个热门课程
        List<EduCourse> courses = courseService.findTopEightCourses();

        //查询前四个名师
        List<EduTeacher> teachers = teacherService.findTopFourTeachers();

        return Result.ok().data("courseList",courses).data("teacherList",teachers);
    }
}
