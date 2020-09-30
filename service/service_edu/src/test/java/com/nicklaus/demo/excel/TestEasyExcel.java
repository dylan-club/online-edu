package com.nicklaus.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestEasyExcel {

    public static void main(String[] args) {

        String filename = "E:\\test\\write.xlsx";

//        //文件路径名称，实体类的class对象
//        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getList());

        //excel读取操作
        EasyExcel.read(filename, DemoData.class,new ExcelListener()).sheet("学生列表").doRead();
    }


    private static List<DemoData> getList(){
        List<DemoData> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname(UUID.randomUUID().toString());
            list.add(demoData);
        }

        return list;
    }

}
