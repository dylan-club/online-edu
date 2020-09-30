package com.nicklaus.serviceedu.service;

import com.nicklaus.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nicklaus.serviceedu.entity.vo.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-11
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file, EduSubjectService subjectService);

    List<FirstSubject> findAllSubject();
}
