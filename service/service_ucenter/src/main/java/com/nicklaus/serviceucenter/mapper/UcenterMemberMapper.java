package com.nicklaus.serviceucenter.mapper;

import com.nicklaus.serviceucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-19
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    int countRegistrationByDay(String day);
}
