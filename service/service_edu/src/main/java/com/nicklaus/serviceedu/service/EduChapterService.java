package com.nicklaus.serviceedu.service;

import com.nicklaus.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nicklaus.serviceedu.entity.vo.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> findChapterAndVideoByCourseId(String courseId);

    boolean deleteById(String chapterId);

    void removeByCourseId(String courseId);
}
