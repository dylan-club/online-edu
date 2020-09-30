package com.nicklaus.serviceedu.controller;


import com.nicklaus.commonutils.Result;
import com.nicklaus.serviceedu.entity.EduChapter;
import com.nicklaus.serviceedu.entity.vo.ChapterVO;
import com.nicklaus.serviceedu.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RestController
@RequestMapping("/serviceEdu/chapter")
//@CrossOrigin
@Api(tags = "课程大纲管理")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @GetMapping("findChapterAndVideo/{courseId}")
    @ApiOperation("查询课程大纲")
    public Result findChapterAndVideo(@PathVariable String courseId){
        List<ChapterVO> chapterVOList = chapterService.findChapterAndVideoByCourseId(courseId);
        return Result.ok().data("items",chapterVOList);
    }

    @PostMapping("addChapter")
    @ApiOperation("添加课程章节")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return Result.ok();
    }

    @GetMapping("findChapter/{chapterId}")
    @ApiOperation("查询课程章节")
    public Result findChapter(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return Result.ok().data("chapter",chapter);
    }

    @PutMapping("updateChapter")
    @ApiOperation("修改课程章节")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return Result.ok();
    }

    @DeleteMapping("removeChapter/{chapterId}")
    @ApiOperation("删除课程章节")
    public Result removeChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteById(chapterId);
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}

