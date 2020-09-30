package com.nicklaus.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nicklaus.serviceedu.entity.vo.CourseInfo;
import com.nicklaus.serviceedu.entity.vo.CourseQuery;
import com.nicklaus.serviceedu.entity.vo.CourseWebVo;
import com.nicklaus.serviceedu.entity.vo.PublishCourseVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfo courseInfo);

    CourseInfo findCoursesInfoByCourseId(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    PublishCourseVO getPublishCourseInfoById(String courseId);

    void publishCourseById(String courseId);

    void pageQuery(Page<EduCourse> eduCoursePage, CourseQuery courseQuery);

    boolean removeCourseById(String courseId);

    List<EduCourse> findTopEightCourses();

    Map<String, Object> getPageQueryCourseList(Page<EduCourse> coursePage, CourseQuery courseQuery);

    CourseWebVo getCourseInfoByCourseId(String courseId);
}
