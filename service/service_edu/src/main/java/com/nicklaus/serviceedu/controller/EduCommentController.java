package com.nicklaus.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.commonutils.JwtUtils;
import com.nicklaus.commonutils.Result;
import com.nicklaus.commonutils.UcenterMember;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceedu.client.UCenterClient;
import com.nicklaus.serviceedu.entity.EduComment;
import com.nicklaus.serviceedu.service.EduCommentService;
import com.nicklaus.serviceedu.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-23
 */
@RestController
@RequestMapping("/serviceEdu/comment")
//@CrossOrigin
@Api(tags = "课程评论管理")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UCenterClient uCenterClient;

    @GetMapping("pageComment/{courseId}/{current}/{limit}")
    public Result pageComment(
            @PathVariable String courseId,
            @PathVariable Long current,
            @PathVariable Long limit
    ){
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        Page<EduComment> commentPage = new Page<EduComment>(current, limit);

        //分页查询
        commentService.page(commentPage,wrapper);
        PageUtils<EduComment> pageUtils = new PageUtils<>();
        //封装数据
        return Result.ok().data(pageUtils.getPageMap(commentPage));
    }

    @PostMapping("auth/addComment")
    @ApiOperation("添加课程评论")
    public Result addComment(@RequestBody EduComment comment, HttpServletRequest request){
        //判断请求是否携带token字符串
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)){
            return Result.error().message("请先登录");
        }

        comment.setMemberId(memberId);

        //调用ucenter-serivce中的服务
        UcenterMember member = uCenterClient.getMemberById(memberId);


        comment.setAvatar(member.getAvatar());
        comment.setNickname(member.getNickname());
        //保存评论
        commentService.save(comment);
        return Result.ok();
    }
}

