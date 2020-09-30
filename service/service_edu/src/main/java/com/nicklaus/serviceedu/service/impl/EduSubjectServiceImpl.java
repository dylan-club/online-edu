package com.nicklaus.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nicklaus.serviceedu.entity.EduSubject;
import com.nicklaus.serviceedu.entity.excel.SubjectClassification;
import com.nicklaus.serviceedu.entity.vo.FirstSubject;
import com.nicklaus.serviceedu.entity.vo.SecondSubject;
import com.nicklaus.serviceedu.listener.SubjectExcelListener;
import com.nicklaus.serviceedu.mapper.EduSubjectMapper;
import com.nicklaus.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author nicklaus
 * @since 2020-09-11
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) {
        //添加课程分类
        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();
            //调用方法读取文件
            EasyExcel.read(inputStream, SubjectClassification.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FirstSubject> findAllSubject() {

        //查询一级分类
        QueryWrapper<EduSubject> firstWrapper = new QueryWrapper<EduSubject>();
        //parent_id = 0
        firstWrapper.eq("parent_id","0");
        List<EduSubject> firstEduSubjects = baseMapper.selectList(firstWrapper);

        //查询二级分类
        QueryWrapper<EduSubject> secondWrapper = new QueryWrapper<EduSubject>();
        //parent_id != 0
        firstWrapper.ne("parent_id","0");
        List<EduSubject> secondEduSubjects = baseMapper.selectList(secondWrapper);

        //添加一级分类
        List<FirstSubject> finalFirstSubjects = new ArrayList<FirstSubject>();
        for (int i = 0; i < firstEduSubjects.size(); i++) {
            FirstSubject firstSubject = new FirstSubject();
            EduSubject firstEduSubject = firstEduSubjects.get(i);
            //将数据封装到一级分类中去
            BeanUtils.copyProperties(firstEduSubject,firstSubject);

            //将一级分类添加到列表中
            finalFirstSubjects.add(firstSubject);

            //添加二级分类
            List<SecondSubject> finalSecondSubjects = new ArrayList<SecondSubject>();
            for (int j = 0; j < secondEduSubjects.size(); j++) {
                EduSubject secondEduSubject = secondEduSubjects.get(j);

                //判断当前一级分类下是否有二级分类
                if (secondEduSubject.getParentId().equals(firstEduSubject.getId())){
                    //将数据封装到二级分类中去
                    SecondSubject secondSubject = new SecondSubject();
                    BeanUtils.copyProperties(secondEduSubject,secondSubject);

                    //将二级分类添加到列表中
                    finalSecondSubjects.add(secondSubject);
                }
            }

            //将二级分类列表添加到一级分类列表中
            firstSubject.setChildren(finalSecondSubjects);
        }

        return finalFirstSubjects;
    }
}
