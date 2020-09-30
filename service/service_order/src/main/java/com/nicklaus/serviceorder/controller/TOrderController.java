package com.nicklaus.serviceorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nicklaus.commonutils.JwtUtils;
import com.nicklaus.commonutils.Result;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceorder.entity.TOrder;
import com.nicklaus.serviceorder.service.TOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-24
 */
@RestController
@RequestMapping("/serviceOrder/order")
//@CrossOrigin
@Api(tags = "订单支付管理")
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    @GetMapping("createOrder/{courseId}")
    @ApiOperation("创建订单")
    public Result createOrder(@PathVariable String courseId, HttpServletRequest request){
        //根据token获取memberId
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        if(StringUtils.isEmpty(memberId)){
            throw new GuliException(28004,"请先登录");
        }

        //保存订单
        String orderId = orderService.createOrder(courseId, memberId);
        return Result.ok().data("orderId",orderId);
    }

    @GetMapping("findOrder/{orderNo}")
    @ApiOperation("根据订单号查询订单信息")
    public Result findOrderByOrderNo(@PathVariable String orderNo){
        //创建wrapper
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);

        //查询订单
        TOrder order = orderService.getOne(wrapper);
        return Result.ok().data("item", order);
    }

    @GetMapping("isBuyCourse/{memberId}/{courseId}")
    @ApiOperation("根据用户id和课程id查询订单支付状态")
    public Boolean isBuyCourse(@PathVariable(name = "memberId") String memberId,
                               @PathVariable(name = "courseId") String courseId){

        if (StringUtils.isEmpty(memberId)){
            return false;
        }

        //创建查询条件
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId)
                .eq("course_id", courseId)
                .eq("status",1);
        //查询该记录是否存在
        int count = orderService.count(wrapper);

        if (count > 0){
            //课程已支付
            return true;
        }else{
            return false;
        }
    }
}

