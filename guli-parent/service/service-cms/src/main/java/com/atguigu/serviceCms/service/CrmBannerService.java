package com.atguigu.serviceCms.service;

import com.atguigu.serviceCms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author super
 * @since 2020-04-08
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 分页banner
     * @param crmBannerPage
     * @param o
     */
    void pageBanner(Page<CrmBanner> crmBannerPage, Object o);

    /**
     * 根据id查banner
     * @param id
     * @return
     */
    CrmBanner getByBannerId(String id);

    /**
     * 修改banner
     * @param crmBanner
     */
    void updateByBannerId(CrmBanner crmBanner);

    /**
     * 删除banner
     * @param id
     */
    void removeByBannerId(String id);

    /**
     * 无分页banner列表
     * @param o
     * @return
     */
    List<CrmBanner> listBanner(Object o);
}
