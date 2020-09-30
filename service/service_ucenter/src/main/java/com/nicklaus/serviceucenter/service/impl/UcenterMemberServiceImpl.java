package com.nicklaus.serviceucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nicklaus.commonutils.JwtUtils;
import com.nicklaus.commonutils.MD5;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceucenter.entity.UcenterMember;
import com.nicklaus.serviceucenter.entity.vo.LoginVo;
import com.nicklaus.serviceucenter.entity.vo.RegisterVo;
import com.nicklaus.serviceucenter.mapper.UcenterMemberMapper;
import com.nicklaus.serviceucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-19
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {

        String phone = loginVo.getPhoneNumber();
        String password = loginVo.getPassword();

        //判断手机号或密码是否为空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"用户名或密码为空！");
        }

        //查询用户
        QueryWrapper<UcenterMember> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",phone);
        UcenterMember member = baseMapper.selectOne(memberQueryWrapper);

        //判断用户是否存在
        if (member == null){
            throw new GuliException(20001,"用户不存在");
        }

        //通过MD5加密比较密码是否正确
        if (!MD5.encrypt(password).equals(member.getPassword())){
            throw new GuliException(20001, "密码错误");
        }

        //判断用户是否被禁用
        if (member.getIsDisabled()){
            throw new GuliException(20001,"该用户已被禁用，请联系管理员！");
        }

        //获取jwt
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {

        //注册参数
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //判断输入是否非空
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile)
            || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)){
            throw new GuliException(20001, "注册失败，输入不能为空！");
        }

        //判断验证码是否正确
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobileCode)){
            throw new GuliException(20001,"注册失败，验证码错误！");
        }

        //查询该手机号是否已经注册
        QueryWrapper<UcenterMember> memberQueryWrapper = new QueryWrapper<UcenterMember>();
        memberQueryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(memberQueryWrapper);

        if (count > 0){
            throw new GuliException(20001,"注册失败，该手机号已被注册！");
        }

        //将用户数据写入数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://nicklaus-edu.oss-cn-beijing.aliyuncs.com/template/user-avatar.jpg");

        baseMapper.insert(member);
    }

    @Override
    public UcenterMember findByOpenId(String openid) {
        return baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("openid", openid));
    }

    @Override
    public int countRegistrationByDay(String day) {
        return baseMapper.countRegistrationByDay(day);
    }
}
