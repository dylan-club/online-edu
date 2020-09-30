package com.nicklaus.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceedu.entity.EduChapter;
import com.nicklaus.serviceedu.entity.EduVideo;
import com.nicklaus.serviceedu.entity.vo.ChapterVO;
import com.nicklaus.serviceedu.entity.vo.VideoVO;
import com.nicklaus.serviceedu.mapper.EduChapterMapper;
import com.nicklaus.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nicklaus.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVO> findChapterAndVideoByCourseId(String courseId) {

        //查找出该课程中所有的章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        //根据sort排序
        chapterQueryWrapper.orderByAsc("sort");
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);

        List<ChapterVO> finalChapters = new ArrayList<ChapterVO>();
        //将查询出来的数据赋值给章节vo对象
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            //新建chapter的vo对象
            ChapterVO chapterVO = new ChapterVO();
            //封装数据
            BeanUtils.copyProperties(eduChapter,chapterVO);
            finalChapters.add(chapterVO);

            //查询出章节中的所有小结
            QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("chapter_id",eduChapter.getId());
            List<EduVideo> eduVideos = eduVideoService.list(videoQueryWrapper);

            List<VideoVO> finalVideos = new ArrayList<VideoVO>();
            for (int j = 0; j < eduVideos.size(); j++) {

                EduVideo eduVideo = eduVideos.get(j);
                //新建VideoVO
                VideoVO videoVO = new VideoVO();
                //封装对象
                BeanUtils.copyProperties(eduVideo,videoVO);
                finalVideos.add(videoVO);
            }

            chapterVO.setChildren(finalVideos);
        }
        return finalChapters;
    }

    @Override
    public boolean deleteById(String chapterId) {

        //查询当前章节下是否有内容，如果有则删除失败
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);

        //查询数据
        int count = eduVideoService.count(videoQueryWrapper);

        if (count > 0){
            throw new GuliException(20001,"该章节下有小节内容，无法删除");
        }else{
            //删除该章节
            int delete = baseMapper.deleteById(chapterId);

            return delete > 0;
        }
    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        baseMapper.delete(chapterQueryWrapper);
    }
}
