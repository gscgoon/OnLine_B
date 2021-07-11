package com.atguigu.serviceEdu.entity.chapter;

/**
 * 课程章节的小节
 */

import lombok.Data;

/**
 * @author Super
 * @date 2020-04-03 0:32
 */
@Data
public class ChapterVideo {

    /**
     * 小节的id
     */
    private String id ;

    /**
     * 小节的名称
     */
    private String title;

    /**
     * 是否免费
     */
    private Boolean isFree;

    /**
     * 云端视频资源id
     */
    private String videoSourceId;
}
