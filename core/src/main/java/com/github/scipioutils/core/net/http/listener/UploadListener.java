package com.github.scipioutils.core.net.http.listener;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 上传监听器
 *
 * @author Alan Scipio
 * @since 1.0.1-p1
 */
public interface UploadListener {

    /**
     * 上传中
     *
     * @param totalBytes    总字节数
     * @param uploadedBytes 已上传字节数
     * @param progress      进度百分比(0-100)
     */
    default void onUploading(long totalBytes, long uploadedBytes, int progress) {
        System.out.println("Upload progress: " + progress + "%" + ", bytes: " + uploadedBytes + "/" + totalBytes);
    }

    /**
     * 上传完成时
     */
    default void onCompleted(Map<String, File> uploadFiles) {
        System.out.println("Upload completed, total upload count: " + uploadFiles.size());
    }

    /**
     * 上传出错时
     *
     * @param e IO异常对象
     */
    default void onError(IOException e) {
        e.printStackTrace();
    }

}
