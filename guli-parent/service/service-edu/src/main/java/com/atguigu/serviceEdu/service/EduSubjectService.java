package com.atguigu.serviceEdu.service;

import com.atguigu.serviceEdu.entity.EduSubject;
import com.atguigu.serviceEdu.entity.sort.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 课程分类excel导入
     * @param file
     * @param eduSubjectService
     * @return
     */
    void analysisExcel(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 课程分类列表
     * @return
     */
    List<OneSubject> getAllSubject();
}
