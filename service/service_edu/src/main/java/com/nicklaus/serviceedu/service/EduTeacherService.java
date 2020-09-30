package com.nicklaus.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nicklaus.serviceedu.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-07
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> teacherPage, TeacherQuery teacherQuery);

    List<EduTeacher> findTopFourTeachers();

    Map<String, Object> findTeacherFrontList(Page<EduTeacher> teacherPage);
}
