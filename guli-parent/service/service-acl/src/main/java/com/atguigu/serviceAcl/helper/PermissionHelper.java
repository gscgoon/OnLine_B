package com.atguigu.serviceAcl.helper;

import com.atguigu.serviceAcl.entity.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建菜单数据
 * </p>
 *
 * @author qy
 * @since 2019-11-11
 */
public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return trees
     */
    public static List<Permission> build(List<Permission> treeNodes) {
        //用来存放构建好的集合
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            //当前对象是否有父节点，如果有设置等级为1，父节点等级为0
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                //其他对象是否存在属于该节点的子节点，递归循环添加字节点
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNode 当前节点
     * @param treeNodes 所有查到的节点的集合
     * @return treeNode
     */
    public static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        //设置该节点的所有子节点，初始化
        treeNode.setChildren(new ArrayList<>());
        //循环遍历所有节点的集合
        for (Permission it : treeNodes) {
            //是否存在其他节点的父id是当前节点的id
            if(treeNode.getId().equals(it.getPid())) {
                //设置其他节点的等级为当前节点的下一级
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                //继续递归构建节点树
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}
