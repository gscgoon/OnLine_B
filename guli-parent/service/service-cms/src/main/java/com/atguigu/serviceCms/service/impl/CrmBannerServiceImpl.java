package com.atguigu.serviceCms.service.impl;

import com.atguigu.serviceCms.entity.CrmBanner;
import com.atguigu.serviceCms.mapper.CrmBannerMapper;
import com.atguigu.serviceCms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author super
 * @since 2020-04-08
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 分页banner
     * @param crmBannerPage
     * @param o
     */
    @Override
    public void pageBanner(Page<CrmBanner> crmBannerPage, Object o) {
        baseMapper.selectPage(crmBannerPage,null);
    }

    /**
     * 根据id查banner
     * @param id
     * @return
     */
    @Override
    public CrmBanner getByBannerId(String id) {

        CrmBanner crmBanner = baseMapper.selectById(id);
        return crmBanner;
    }

    /**
     * 修改banner
     * @param crmBanner
     */
    @Override
    public void updateByBannerId(CrmBanner crmBanner) {
        baseMapper.updateById(crmBanner);
    }

    /**
     * 删除banner
     * @param id
     */
    @Override
    public void removeByBannerId(String id) {
        baseMapper.deleteById(id);
    }

    /**
     * 无分页banner列表
     * @param o
     * @return
     */
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> listBanner(Object o) {
        List<CrmBanner> crmBanners = baseMapper.selectList(null);
        return crmBanners;
    }
}
