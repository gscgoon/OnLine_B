package com.atguigu.serviceCms.controller;


import com.atguigu.commonUtils.R;
import com.atguigu.serviceCms.entity.CrmBanner;
import com.atguigu.serviceCms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author super
 * @since 2020-04-08
 */
@Api(description = "banner管理")
@RestController
@RequestMapping("/admin/serviceCms/crmBanner")
//@CrossOrigin 因为使用了spring cloud 的gateway网关配置，所以不需要再次配置跨域注解
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("banner列表")
    @GetMapping("/getAllBanner/{page}/{limit}")
    public R getAllBanner(@ApiParam(name = "page",value = "当前页",required = true)
                          @PathVariable Long page,
                          @ApiParam(name = "limit",value = "每页记录数",required = true)
                          @PathVariable Long limit
                          ){
        Page<CrmBanner> crmBannerPage = new Page<>(page,limit);
        crmBannerService.pageBanner(crmBannerPage,null);
        long total = crmBannerPage.getTotal();
        List<CrmBanner> records = crmBannerPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    @ApiOperation("根据id查banner")
    @GetMapping("/getBannerById/{id}")
    public R getBannerById(@ApiParam(name = "id",value = "banner id",required = true)
                           @PathVariable String id
                           ){
        CrmBanner crmBanner = crmBannerService.getByBannerId(id);
        return R.ok().data("crmBanner",crmBanner);
    }

    @ApiOperation("修改banner")
    @PostMapping("/updateBannerById")
    public R updateBannerById(@ApiParam(name = "crmBanner",value = "banner对象",required = true)
                              @RequestBody CrmBanner crmBanner
                              ){
        crmBannerService.updateByBannerId(crmBanner);
        return R.ok().message("banner修改成功！");
    }

    @ApiOperation("删除banner")
    @DeleteMapping("/deleteBanner/{id}")
    public R deleteBanner(@ApiParam(name = "id",value = "banner id",required = true)
                          @PathVariable String id
                          ){
        crmBannerService.removeByBannerId(id);
        return R.ok();
    }



    @ApiOperation("无分页banner列表")
    @GetMapping("/getAllBanner")
    public R index() {
        List<CrmBanner> list = crmBannerService.listBanner(null);
        return R.ok().data("bannerList", list);
    }

}

