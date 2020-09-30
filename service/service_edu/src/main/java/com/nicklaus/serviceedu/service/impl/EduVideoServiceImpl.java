package com.nicklaus.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nicklaus.serviceedu.client.VodClient;
import com.nicklaus.serviceedu.entity.EduVideo;
import com.nicklaus.serviceedu.mapper.EduVideoMapper;
import com.nicklaus.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);

        List<String> videoIds = new ArrayList<>();

        for (EduVideo eduVideo : eduVideos) {
            //视频id存在
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId()) && !"".equals(eduVideo.getVideoSourceId())){
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }

        if (videoIds.size() > 0){
            //批量删除视频
            vodClient.deleteVideoList(videoIds);
        }

        //删除小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        baseMapper.delete(videoQueryWrapper);
    }
}
