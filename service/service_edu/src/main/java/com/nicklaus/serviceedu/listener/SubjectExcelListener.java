package com.nicklaus.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nicklaus.servicebase.handler.exception.GuliException;
import com.nicklaus.serviceedu.entity.EduSubject;
import com.nicklaus.serviceedu.entity.excel.SubjectClassification;
import com.nicklaus.serviceedu.service.EduSubjectService;

public class SubjectExcelListener extends AnalysisEventListener<SubjectClassification> {

    private EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {
    }

    @Override
    public void invoke(SubjectClassification subjectClassification, AnalysisContext analysisContext) {
        //读取excel中的内容
        //excel中没有数据
        if (subjectClassification == null){
            throw new GuliException(20001,"文件中无数据，请重新上传");
        }

        //添加一级分类
        EduSubject existOneSubject = this.existOneSubject(subjectClassification.getOneSubjectName(), subjectService);

        if (existOneSubject == null){
            //如果一级分类不存在
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectClassification.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        //获取一级分类的pid
        String pid = existOneSubject.getId();

        //添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(subjectClassification.getTwoSubjectName(), pid, subjectService);
        if (existTwoSubject == null){
            //如果二级分类不存在
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectClassification.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(String name, EduSubjectService service){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        wrapper.eq("title",name)
                .eq("parent_id","0");

        EduSubject subject = service.getOne(wrapper);

        return subject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(String name, String pid, EduSubjectService service){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name)
                .eq("parent_id",pid);

        //查询数据
        EduSubject subject = service.getOne(wrapper);

        return subject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
