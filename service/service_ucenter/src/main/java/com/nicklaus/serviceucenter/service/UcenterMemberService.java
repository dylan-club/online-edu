package com.nicklaus.serviceucenter.service;

import com.nicklaus.serviceucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nicklaus.serviceucenter.entity.vo.LoginVo;
import com.nicklaus.serviceucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-19
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember findByOpenId(String openid);

    int countRegistrationByDay(String day);
}
