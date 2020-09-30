package com.nicklaus.serviceedu.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

public class PageUtils<T> {

    public Map<String, Object> getPageMap(Page<T> page){
        Map<String, Object> map = new HashMap<>();
        map.put("items", page.getRecords());      //当前页的数据对象
        map.put("current", page.getCurrent());    //当前页码
        map.put("pages", page.getPages());        //总页数
        map.put("size", page.getSize());          //当前条目数
        map.put("total", page.getTotal());        //总记录数
        map.put("hasNext", page.hasNext());       //是否有下一页
        map.put("hasPrevious", page.hasPrevious());//是否有上一页
        return map;
    }
}
