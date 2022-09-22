package com.github.scipioutils.core.data.structure;

import com.github.scipioutils.core.AssertUtils;

import java.util.*;

/**
 * 树型结构工具类
 * 输入输出的TreeNode实现类，必须完全一致，否则IDE不会报错但是实际运行报错
 *
 * @author Alan Scipio
 * @since 2021/4/23
 */
public class TreeStructure {

    /**
     * 【根节点方式】创建树型结构
     *
     * @param root     根节点
     * @param nodeList 原始数据
     * @return 带有完整树型结构的根节点
     */
    public static <T extends TreeNode<T>> T buildTree(T root, Collection<T> nodeList) {
        AssertUtils.notNull(root, "root object can not be null");
        AssertUtils.collectionNotEmpty(nodeList, "nodeList can not be null");
        //创建缓存
        Map<Object, T> cacheMap = new HashMap<>();
        cacheMap.put(root.getId(), root);
        //遍历原始数据list，构建树型结构
        for (T currentNode : nodeList) {
            T parentNode = cacheMap.get(currentNode.getParentId());
            //击中缓存
            if (parentNode != null) {
                parentNode.addChildNode(currentNode);//添加子节点
            }
            cacheMap.put(currentNode.getId(), currentNode);
        }
        cacheMap.clear();//清除缓存
        return root;
    }

    /**
     * 【根节点方式】创建树型结构
     *
     * @param nodeList 原始数据，默认第1哥为根节点
     * @return 带有完整树型结构的根节点
     */
    public static <T extends TreeNode<T>> T buildTree(List<T> nodeList) {
        return buildTree(nodeList.get(0), nodeList);
    }

    /**
     * 【森林方式】创建树型结构
     *
     * @param rootNodeId 根节点的id
     * @param nodeList   原始数据
     * @return 森林（没有根节点）
     */
    public static <T extends TreeNode<T>> List<T> buildForest(Object rootNodeId, Collection<T> nodeList) {
        AssertUtils.collectionNotEmpty(nodeList, "nodeList can not be null");
        //创建结果集
        List<T> resultList = new ArrayList<>();
        //创建缓存
        Map<Object, T> cacheMap = new HashMap<>();
        //遍历原始数据list，构建树型结构
        for (T currentNode : nodeList) {
            if (currentNode.getParentId().equals(rootNodeId)) {
                resultList.add(currentNode);
            }
            T parentNode = cacheMap.get(currentNode.getParentId());
            //击中缓存
            if (parentNode != null) {
                parentNode.addChildNode(currentNode);//添加子节点
            }
            cacheMap.put(currentNode.getId(), currentNode);
        }
        cacheMap.clear();//清除缓存
        return resultList;
    }

    public static <T extends TreeNode<T>> List<T> buildForest(List<T> nodeList) {
        return buildForest(nodeList.get(0).getId(), nodeList);
    }

    /**
     * 递归遍历树型结构，转为扁平结构的list
     *
     * @param root     根节点
     * @param listener 读取每一个节点时的监听回调
     * @return 扁平的所有节点list <strong>（深度优先）</strong>
     */
    public static <T extends TreeNode<T>> Collection<T> extractTree(TreeNode<T> root, TreeReadListener listener) {
        return readTreeByDeep(root.getChildren(), new ArrayList<>(), listener);
    }

    /**
     * 递归遍历树型结构
     *
     * @param forest   森林
     * @param listener 读取每一个节点时的监听器
     * @return 扁平的所有节点list <strong>（深度优先）</strong>
     */
    public static <T extends TreeNode<T>> Collection<T> extractForest(Collection<T> forest, TreeReadListener listener) {
        return readTreeByDeep(forest, new ArrayList<>(), listener);
    }

    /**
     * 递归遍历树型结构<strong>（深度优先）</strong>
     *
     * @param forest     森林
     * @param resultList 结果集，不能为null
     * @return 结果集
     */
    private static <T extends TreeNode<T>> Collection<T> readTreeByDeep(Collection<T> forest, Collection<T> resultList, TreeReadListener listener) {
        if (forest != null) {
            for (T node : forest) {
                resultList.add(node);
                //遍历读取时的回调
                if (listener != null) {
                    listener.onNode(node);
                }
                readTreeByDeep(node.getChildren(), resultList, listener);
            }
        }
        return resultList;
    }

}
