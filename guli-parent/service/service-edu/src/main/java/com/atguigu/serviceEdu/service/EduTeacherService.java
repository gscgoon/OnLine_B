package com.atguigu.serviceEdu.service;

import com.atguigu.serviceEdu.entity.EduTeacher;
import com.atguigu.serviceEdu.entity.vo.QueryTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author super
 * @since 2020-03-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 查讲师
     * @param pageT
     * @param queryTeacher
     */
    void getPage(Page<EduTeacher> pageT, QueryTeacher queryTeacher);

    /**
     * 查讲师，并将讲师添加到redis中
     * @return
     */
    List<EduTeacher> listTeacher();

    /**
     * web讲师列表
     * @param pageWebTeacher
     * @return
     */
    Map<String,Object> getWebTeacherList(Page<EduTeacher> pageWebTeacher);
}
