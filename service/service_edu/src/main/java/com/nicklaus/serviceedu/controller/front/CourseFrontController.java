package com.nicklaus.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.commonutils.JwtUtils;
import com.nicklaus.commonutils.Result;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceedu.client.OrderClient;
import com.nicklaus.serviceedu.entity.EduCourse;
import com.nicklaus.serviceedu.entity.vo.ChapterVO;
import com.nicklaus.serviceedu.entity.vo.CourseQuery;
import com.nicklaus.serviceedu.entity.vo.CourseWebVo;
import com.nicklaus.serviceedu.service.EduChapterService;
import com.nicklaus.serviceedu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("serviceEdu/courseFront")
//@CrossOrigin
@Api(tags = "前台课程管理")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("getPageQueryCourseList/{current}/{limit}")
    @ApiOperation("多条件组合查询课程列表带分页")
    public Result getPageQueryCourseList(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody(required = false)CourseQuery courseQuery
            ){

        Page<EduCourse> coursePage = new Page<EduCourse>(current, limit);
        //将组合查询和带分页的课程数据用map封装
        Map<String, Object> courseMap = courseService.getPageQueryCourseList(coursePage, courseQuery);
        return Result.ok().data(courseMap);
    }

    @GetMapping("getCourseInfo/{courseId}")
    @ApiOperation("根据课程id查询课程详情")
    public Result getCourseInfoByCourseId(@PathVariable String courseId, HttpServletRequest request){
        //查询课程、描述、讲师详情
        CourseWebVo courseWebVo = courseService.getCourseInfoByCourseId(courseId);

        //查询课程章节信息
        List<ChapterVO> chapterVOList = chapterService.findChapterAndVideoByCourseId(courseId);

        //查询课程是否已经购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //用户未登录，提示用户登录
        if (StringUtils.isEmpty(memberId)){
            throw new GuliException(28004, "请先登录");
        }

        boolean isBuy = false;

        //如果是收费课程查询是否已经购买
        if (courseWebVo.getPrice().compareTo(new BigDecimal("0.00")) != 0){
            isBuy = orderClient.isBuyCourse(memberId, courseId);
            System.out.println("*****是否购买："+isBuy);
        }

        return Result.ok().data("courseWebVo",courseWebVo).data("chapterVOList",chapterVOList).
                data("isBuy",isBuy);
    }
}
