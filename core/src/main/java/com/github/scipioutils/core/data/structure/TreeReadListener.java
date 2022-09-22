package com.github.scipioutils.core.data.structure;

/**
 * 树型结构读取监听器
 *
 * @author Alan Scipio
 * @since 2021/4/23
 */
public interface TreeReadListener {

    /**
     * 读取每一个节点时的监听回调
     *
     * @param currentNode 当前读取的节点
     */
    void onNode(TreeNode<?> currentNode);

}
