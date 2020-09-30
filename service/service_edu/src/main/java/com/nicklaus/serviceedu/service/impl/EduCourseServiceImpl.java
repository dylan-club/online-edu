package com.nicklaus.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.commonutils.UtilConstants;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceedu.entity.EduCourse;
import com.nicklaus.serviceedu.entity.EduCourseDescription;
import com.nicklaus.serviceedu.entity.vo.CourseInfo;
import com.nicklaus.serviceedu.entity.vo.CourseQuery;
import com.nicklaus.serviceedu.entity.vo.CourseWebVo;
import com.nicklaus.serviceedu.entity.vo.PublishCourseVO;
import com.nicklaus.serviceedu.mapper.EduCourseMapper;
import com.nicklaus.serviceedu.service.EduChapterService;
import com.nicklaus.serviceedu.service.EduCourseDescriptionService;
import com.nicklaus.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nicklaus.serviceedu.service.EduVideoService;
import com.nicklaus.serviceedu.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-12
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public String addCourseInfo(CourseInfo courseInfo) {
        //添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        //判断是否添加成功
        if (insert <= 0){
            throw new GuliException(20001,"课程添加失败！");
        }

        //添加课程描述
        EduCourseDescription description = new EduCourseDescription();
        description.setId(eduCourse.getId());
        description.setDescription(courseInfo.getDescription());
        descriptionService.save(description);

        return eduCourse.getId();
    }

    @Override
    public CourseInfo findCoursesInfoByCourseId(String courseId) {
        //创建课程信息对象
        CourseInfo courseInfo = new CourseInfo();
        //查询课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);

        if (eduCourse == null){
            throw new GuliException(20001,"查询课程信息失败");
        }

        //封装对象
        BeanUtils.copyProperties(eduCourse,courseInfo);

        //查询课程描述信息
        EduCourseDescription description = descriptionService.getById(courseId);

        //封装数据
        courseInfo.setDescription(description.getDescription());
        return courseInfo;
    }

    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {
        //更新课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int update = baseMapper.updateById(eduCourse);

        if (update <= 0){
            //更新课程信息失败
            throw new GuliException(20001,"更新课程信息失败");
        }

        //更新课程简介
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo,description);
        descriptionService.updateById(description);
    }

    @Override
    public PublishCourseVO getPublishCourseInfoById(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public void publishCourseById(String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus(UtilConstants.Course.COURSE_PUBLISHED);
        //修改课程状态
        baseMapper.updateById(course);
    }

    @Override
    public void pageQuery(Page<EduCourse> eduCoursePage, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();

        //排序
        courseQueryWrapper.orderByDesc("gmt_create");

        //查询条件为空
        if (courseQuery == null){
            baseMapper.selectPage(eduCoursePage,courseQueryWrapper);
            return;
        }

        //获取查询字段
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)){
            courseQueryWrapper.like("title", title); //课程名称模糊查询
        }

        if (!StringUtils.isEmpty(teacherId)){
            courseQueryWrapper.eq("teacher_id", teacherId); //根据讲师查询
        }

        if (!StringUtils.isEmpty(subjectParentId)){
            courseQueryWrapper.like("subject_parent_id", subjectParentId); //根据一级分类查询
        }

        if (!StringUtils.isEmpty(title)){
            courseQueryWrapper.like("subject_id", subjectId); //根据二级分类查询
        }

        baseMapper.selectPage(eduCoursePage,courseQueryWrapper);
    }

    @Override
    public boolean removeCourseById(String courseId) {

        //删除小节
        eduVideoService.removeByCourseId(courseId);
        //删除章节
        eduChapterService.removeByCourseId(courseId);
        //删除课程描述
        descriptionService.removeById(courseId);
        //删除课程
        int result = baseMapper.deleteById(courseId);

        if (result == 0){
            return false;
        }else{
            return true;
        }
    }

    @Cacheable(value = "course",key = "'selectTopEightCourses'")
    @Override
    public List<EduCourse> findTopEightCourses() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id").last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(courseQueryWrapper);
        return eduCourses;
    }

    @Override
    public Map<String, Object> getPageQueryCourseList(Page<EduCourse> coursePage, CourseQuery courseQuery) {
        //创建wrapper
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //组合多条件
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())){
            //一级分类
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())){
            //二级分类
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())){
            //购买数量排序
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())){
            //时间排序
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQuery.getPriceSort())){
            //价格排序
            queryWrapper.orderByDesc("price");
        }

        //组合查询
        baseMapper.selectPage(coursePage, queryWrapper);

        return new PageUtils<EduCourse>().getPageMap(coursePage);
    }

    @Override
    public CourseWebVo getCourseInfoByCourseId(String courseId) {
        return baseMapper.getCourseInfoByCourseId(courseId);
    }
}
