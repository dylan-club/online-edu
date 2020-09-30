package com.nicklaus.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nicklaus.serviceedu.entity.EduTeacher;
import com.nicklaus.serviceedu.mapper.EduTeacherMapper;
import com.nicklaus.serviceedu.entity.vo.TeacherQuery;
import com.nicklaus.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nicklaus.serviceedu.utils.PageUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-07
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> teacherPage, TeacherQuery teacherQuery) {

        //设置wrapper
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //查询字段不存在
        if (teacherQuery == null){
            baseMapper.selectPage(teacherPage,wrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);    //对姓名进行模糊查询
        }

        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);    //根据讲师职称进行查询
        }

        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);    //查询开始时间点
        }

        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);    //查询结束时间点
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        baseMapper.selectPage(teacherPage, wrapper);
    }

    @Cacheable(value = "teacher",key = "'selectTopFourTeachers'")
    @Override
    public List<EduTeacher> findTopFourTeachers() {
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id").last("limit 4");
        List<EduTeacher> eduTeachers = baseMapper.selectList(teacherQueryWrapper);

        return eduTeachers;
    }

    @Override
    public Map<String, Object> findTeacherFrontList(Page<EduTeacher> teacherPage) {
        baseMapper.selectPage(teacherPage, new QueryWrapper<EduTeacher>().orderByDesc("id"));
        return new PageUtils<EduTeacher>().getPageMap(teacherPage);
    }
}
