package com.nicklaus.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.commonutils.Result;
import com.nicklaus.serviceedu.entity.EduCourse;
import com.nicklaus.serviceedu.entity.EduTeacher;
import com.nicklaus.serviceedu.service.EduCourseService;
import com.nicklaus.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("serviceEdu/teacherFront")
//@CrossOrigin
@Api(tags = "前台讲师管理")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @GetMapping("getTeacherFrontList/{page}/{limit}")
    @ApiOperation("分页查询讲师列表")
    public Result getTeacherFrontList(@PathVariable Long page, @PathVariable Long limit){
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = teacherService.findTeacherFrontList(teacherPage);
        return Result.ok().data(map);
    }

    @GetMapping("getTeacherInfo/{teacherId}")
    @ApiOperation("根据讲师id查询讲师信息和所讲课程")
    public Result getTeacherInfoById(@PathVariable String teacherId){
        //查询讲师信息
        EduTeacher teacher = teacherService.getById(teacherId);
        //查询讲师所讲课程
        List<EduCourse> courseList = courseService.list(new QueryWrapper<EduCourse>().eq("teacher_id", teacherId));
        return Result.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
