package com.atguigu.serviceAcl.controller;

import com.atguigu.commonUtils.R;
import com.atguigu.serviceAcl.entity.Permission;
import com.atguigu.serviceAcl.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 * @author testjava
 * @since 2020-01-12
 */
@Api(description = "菜单管理")
@RestController
@RequestMapping("/admin/Acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/getAllMenu")
    public R getAllMenu() {
        List<Permission> children =  permissionService.queryAllMenu();
        return R.ok().data("children",children);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("/removeMenu/{id}")
    public R removeMenu(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return R.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.doAssign(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

