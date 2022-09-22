package com.github.scipioutils.core.data.structure;

import java.util.Collection;
import java.util.List;

/**
 * 树型结构的节点定义
 *
 * @author Alan Scipio
 * @since 2021/4/23
 */
public interface TreeNode<T extends TreeNode<T>> {

    /**
     * 节点id
     */
    Object getId();

    /**
     * 父节点id
     */
    Object getParentId();

    /**
     * 子节点集合
     */
    Collection<T> getChildren();

    /**
     * 添加子节点
     *
     * @param childNode 子节点
     */
    default void addChildNode(T childNode) {
        getChildren().add(childNode);
    }

}
