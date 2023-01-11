package com.github.scipioutils.crypto.listener;

/**
 * 流加密或解密进行时的监听器
 *
 * @author Alan Scipio
 * created on 2023/1/11
 */
public interface OnProcessingListener {

    /**
     * 处理进行时
     *
     * @param readBytesCount  读取并处理的字节总数
     * @param totalBytesCount 总共的字节总数
     */
    void onProcess(long readBytesCount, long totalBytesCount);

}
