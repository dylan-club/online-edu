package com.nicklaus.serviceedu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.commonutils.Result;
import com.nicklaus.serviceedu.entity.EduTeacher;
import com.nicklaus.serviceedu.entity.vo.TeacherQuery;
import com.nicklaus.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/serviceEdu/teacher")
//@CrossOrigin
@Api(tags = "讲师管理")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("findAll")
    @ApiOperation("所有讲师列表")
    public Result findAll(){
        return Result.ok().data("items",teacherService.list(null));
    }

    @DeleteMapping("removeById/{id}")
    @ApiOperation("根据id删除讲师")
    public Result removeById(
            @ApiParam(name = "id", value = "讲师id",required = true)
            @PathVariable String id){
        Boolean flag = teacherService.removeById(id);

        //删除成功
        if (flag){
           return Result.ok();
        }else{
            //删除失败
            return Result.error();
        }
    }

    @PostMapping("addTeacher")
    @ApiOperation("添加讲师")
    public Result addTeacher(
            @ApiParam(name = "eduTeacher", value = "讲师对象",required = true)
            @RequestBody EduTeacher eduTeacher
    ){
        //添加讲师
        boolean flag = teacherService.save(eduTeacher);

        //添加成功
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @GetMapping("findTeacherById/{id}")
    @ApiOperation("根据id查询讲师")
    public Result findTeacherById(
            @ApiParam(name = "id", value = "讲师id",required = true)
            @PathVariable String id
    ){
        EduTeacher teacher = teacherService.getById(id);
        return Result.ok().data("teacher", teacher);
    }

    @PostMapping("updateTeacher")
    @ApiOperation("根据id更新讲师")
    public Result updateTeacher(
            @ApiParam(name = "teacher", value = "讲师对象",required = true)
            @RequestBody EduTeacher teacher
    ){
        boolean flag = teacherService.updateById(teacher);

        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation("分页显示讲师列表")
    public Result pageTeacher(
            @ApiParam(name = "current", value = "当前页",required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "条目数",required = true)
            @PathVariable Long limit
    ){
        
        //查询数据
        IPage<EduTeacher> page = teacherService.page(new Page<EduTeacher>(current, limit), null);

        List<EduTeacher> rows = page.getRecords();
        long total = page.getTotal();

        return Result.ok().data("total",total).data("rows",rows);
    }

    @PostMapping("pageQueryTeacher/{current}/{limit}")
    @ApiOperation("多条件查询分页显示讲师列表")
    public Result pageQueryTeacher(
            @ApiParam(name = "current", value = "当前页",required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页条目数",required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherQuery", value = "多条件查询封装对象",required = false)
            @RequestBody(required = false) TeacherQuery teacherQuery
    ){

        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);

        //查询数据
        teacherService.pageQuery(eduTeacherPage,teacherQuery);

        //获取数据
        List<EduTeacher> rows = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();

        return Result.ok().data("total",total).data("rows",rows);
    }
}

