package com.github.scipioutils.core.net.http.listener;

import java.io.File;

/**
 * 文件上传时的监听器
 *
 * @author Alan Min
 * @since 1.0.0
 */
public interface FileUploadListener extends UploadListener {

    /**
     * 上传时(文件层面)
     *
     * @param totalFileCount    总文件数
     * @param uploadedFileCount 已上传的文件数
     */
    default void onFilesUploading(int totalFileCount, int uploadedFileCount) {
        System.out.println("File upload progress: " + uploadedFileCount + "/" + totalFileCount + " files");
    }

    /**
     * 上传时(单个文件的字节层面)
     *
     * @param fileNo        第几个文件(0-based)
     * @param uploadFile    上传文件
     * @param totalBytes    总字节数
     * @param uploadedBytes 已上传的字节数
     * @param progress      上传百分比(0-100)
     */
    default void onSingleUploading(int fileNo, File uploadFile, long totalBytes, long uploadedBytes, int progress) {
        System.out.println("[" + fileNo + "] File(" + uploadFile.getName() + ") uploading: " + progress + "%,  bytes: " + uploadedBytes + "/" + totalBytes);
    }

}
