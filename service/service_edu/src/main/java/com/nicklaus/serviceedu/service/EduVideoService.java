package com.nicklaus.serviceedu.service;

import com.nicklaus.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
