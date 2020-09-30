package com.nicklaus.serviceedu.mapper;

import com.nicklaus.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nicklaus.serviceedu.entity.vo.CourseWebVo;
import com.nicklaus.serviceedu.entity.vo.PublishCourseVO;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    PublishCourseVO getPublishCourseInfo(String courseId);

    CourseWebVo getCourseInfoByCourseId(String courseId);
}
