package com.nicklaus.serviceedu.service.impl;

import com.nicklaus.serviceedu.entity.EduComment;
import com.nicklaus.serviceedu.mapper.EduCommentMapper;
import com.nicklaus.serviceedu.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-23
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
}
