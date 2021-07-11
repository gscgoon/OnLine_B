package com.atguigu.serviceEdu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程的章节
 */

/**
 * @author Super
 * @date 2020-04-03 0:30
 */
@Data
public class Chapter {

    /**
     * 章节id
     */
    private String id;

    /**
     * 章节名称
     */
    private  String title;

    /**
     * 章节中有多个小节，将小节集合作为章节的属性封装到章节中
     */
    private List<ChapterVideo> children = new ArrayList<>();
}
