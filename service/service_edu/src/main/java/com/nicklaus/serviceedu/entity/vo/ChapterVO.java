package com.nicklaus.serviceedu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVO {

    private String id;
    private String title;
    private List<VideoVO> children;
}
