package com.nicklaus.serviceedu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.commonutils.Result;
import com.nicklaus.commonutils.ordervo.OrderCourseVo;
import com.nicklaus.serviceedu.entity.EduCourse;
import com.nicklaus.serviceedu.entity.EduTeacher;
import com.nicklaus.serviceedu.entity.vo.*;
import com.nicklaus.serviceedu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/serviceEdu/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("添加课程信息")
    @PostMapping("addCourse")
    public Result addCourseInfo(@RequestBody CourseInfo courseInfo){

        //添加课程信息并返回课程id
        String id = courseService.addCourseInfo(courseInfo);
        return Result.ok().data("courseId",id);
    }

    @ApiOperation("回显课程信息")
    @GetMapping("findCourseInfo/{courseId}")
    public Result findCourseInfo(@PathVariable String courseId){
        CourseInfo courseInfo = courseService.findCoursesInfoByCourseId(courseId);
        return Result.ok().data("courseInfo",courseInfo);
    }

    @ApiOperation("更新课程信息")
    @PutMapping("updateCourse")
    public Result updateCourseInfo(@RequestBody CourseInfo courseInfo){
        courseService.updateCourseInfo(courseInfo);
        return Result.ok();
    }

    @ApiOperation("显示发布课程信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public Result getPublishCourseInfo(@PathVariable String courseId){
        PublishCourseVO publishCourseVO = courseService.getPublishCourseInfoById(courseId);
        return Result.ok().data("publishCourse",publishCourseVO);
    }

    @ApiOperation("发布课程")
    @PutMapping("publishCourse/{courseId}")
    public Result publishCourseById(@PathVariable String courseId){
        courseService.publishCourseById(courseId);
        return Result.ok();
    }

    @GetMapping("pageCourse/{current}/{limit}")
    @ApiOperation("分页显示课程信息")
    public Result pageCourse(
            @ApiParam(name = "current", value = "当前页",required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "条目数",required = true)
            @PathVariable Long limit
    ){

        //查询数据
        IPage<EduCourse> page = courseService.page(new Page<EduCourse>(current, limit), null);

        List<EduCourse> rows = page.getRecords();
        long total = page.getTotal();

        return Result.ok().data("total",total).data("rows",rows);
    }

    @PostMapping("pageQueryCourse/{current}/{limit}")
    @ApiOperation("多条件查询分页显示讲师列表")
    public Result pageQueryCourse(
            @ApiParam(name = "current", value = "当前页",required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页条目数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "多条件查询封装对象",required = false)
            @RequestBody(required = false) CourseQuery courseQuery
    ){

        Page<EduCourse> eduCoursePage = new Page<>(current, limit);

        //查询数据
        courseService.pageQuery(eduCoursePage,courseQuery);

        //获取数据
        List<EduCourse> rows = eduCoursePage.getRecords();
        long total = eduCoursePage.getTotal();

        return Result.ok().data("total",total).data("rows",rows);
    }

    @DeleteMapping("removeCourse/{courseId}")
    @ApiOperation("根据id删除课程")
    public Result removeCourseById(@PathVariable String courseId){
        boolean flag = courseService.removeCourseById(courseId);
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @GetMapping("findOrderCourseInfo/{courseId}")
    @ApiOperation("根据id查询订单所需要的课程信息")
    public OrderCourseVo findOrderCourseInfo(@PathVariable String courseId){
        CourseWebVo courseWebVo = courseService.getCourseInfoByCourseId(courseId);
        OrderCourseVo orderCourseVo = new OrderCourseVo();
        BeanUtils.copyProperties(courseWebVo,orderCourseVo);

        return orderCourseVo;
    }
}

