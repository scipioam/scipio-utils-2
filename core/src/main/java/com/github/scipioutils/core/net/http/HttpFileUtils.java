package com.github.scipioutils.core.net.http;

import com.github.scipioutils.core.StringUtils;
import com.github.scipioutils.core.net.http.listener.DownloadListener;
import com.github.scipioutils.core.net.http.listener.FileUploadListener;
import com.github.scipioutils.core.net.http.listener.UploadListener;
import com.github.scipioutils.core.net.mime.MimeType;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * @author Alan Scipio
 * create date: 2022/9/22
 */
public class HttpFileUtils {

    public static final String BOUNDARY = "-----PART-BOUNDARY-----";

    //传输文件要用到
    public static final String NEWLINE = "\r\n";

    //传输文件要用到
    public static final String TOW_HYPHENS = "--";

    public static HttpFileUtils build() {
        return new HttpFileUtils();
    }

    //========================================== ↓↓↓↓↓↓ 下载 ↓↓↓↓↓↓ ==========================================

    /**
     * 下载文件
     *
     * @param outputFilePath     要输出的文件（全路径）
     * @param in                 连接里响应的输入流，文件数据在此流中
     * @param contentLength      响应的文件字节大小
     * @param bufferSize         下载缓冲池大小
     * @param downloadAutoSuffix 是否自动生成文件后缀（根据contentType进行截取），true代表是
     * @param contentType        用于自动拼接文件后缀
     * @param downloadListener   下载监听器
     * @return 下载下来的文件
     */
    public File downloadFile(String outputFilePath,
                             InputStream in,
                             long contentLength,
                             int bufferSize,
                             boolean downloadAutoSuffix,
                             String contentType,
                             DownloadListener downloadListener) {
        if (contentLength == 0L) {
            throw new IllegalStateException("Response content-Length is 0 while download");
        }

        String finalFilePath = outputFilePath;
        //自动根据contentType生成后缀
        if (downloadAutoSuffix && StringUtils.isNotNull(contentType)) {
            String extension = MimeType.getFileExtensionByContentType(contentType);
//            System.out.println("Auto match file extension by Content-Type: [" + extension + "]");
            finalFilePath = outputFilePath + extension;
        }
        File outputFile = new File(finalFilePath);

        Exception ee;
        boolean isSuccess = false;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            bis = new BufferedInputStream(in);
            fos = new FileOutputStream(outputFile);

            int length;//每次读取的字节数
            long readLength = 0L;//总共已读取的字节数
            byte[] buffer = new byte[bufferSize];
            while ((length = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
                //下载中的回调
                if (downloadListener != null) {
                    //计算下载进度
                    readLength += length;
                    int progress = (int) (100L * readLength / contentLength);
                    downloadListener.onDownloading(contentLength, readLength, progress);
                }
            }
            isSuccess = true;
            if (downloadListener != null) {
                downloadListener.onSuccess(outputFile);
            }
        } catch (Exception e) {
            ee = e;
            if (downloadListener != null) {
                downloadListener.onError(outputFile, ee);
            }
        } finally {
            try {
                if (bis != null) bis.close();
                if (fos != null) fos.close();
                if (!isSuccess) { //不成功就删除空的输出文件
                    if (outputFile.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        outputFile.delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //下载结束时的回调
        if (downloadListener != null) {
            downloadListener.onFinished(isSuccess, outputFile);
        }
        return outputFile;
    }

    //========================================== ↓↓↓↓↓↓ 上传 ↓↓↓↓↓↓ ==========================================

    /**
     * 执行文件等数据的输出操作（上传）
     *
     * @param conn         HTTP连接对象
     * @param outputParams 要输出的字符串参数（可选）
     * @param outputFiles  要输出的文件对象（单个File对象或key-File的HashMap对象）
     */
    public void uploadFiles(HttpURLConnection conn,
                            Map<String, String> outputParams,
                            Map<String, File> outputFiles,
                            String charset,
                            int bufferSize,
                            UploadListener uploadListener)
            throws IOException {
        // 往服务器端写内容 也就是发起http请求需要带的参数
        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            // 请求参数部分
            writeParams(outputParams, dos, charset);
            // 请求上传文件部分
            writeFiles(outputFiles, dos, bufferSize, uploadListener);
            // 请求结束标志
            String endTarget = TOW_HYPHENS + BOUNDARY + TOW_HYPHENS + NEWLINE;
            dos.writeBytes(endTarget);
            dos.flush();
        }
    }

    /**
     * 传输文件专用 - 对字符串参数进行编码处理并输出数据流中
     *
     * @param outputParams 要传输的参数
     * @param dos          数据输出流
     */
    private void writeParams(Map<String, String> outputParams, DataOutputStream dos, String charset)
            throws IOException {
        if (outputParams == null || outputParams.size() == 0) {
            System.out.println("output string params (Map<String, String>) is null");//test
            return;
        }

        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> entry : outputParams.entrySet()) {
            //每段开头
            params.append(TOW_HYPHENS).append(BOUNDARY).append(NEWLINE);
            //参数头
            params.append("Content-Length: ").append(entry.getValue().length())
                    .append(NEWLINE)
                    .append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"")
                    .append(NEWLINE)
                    .append("Content-Type: text/plain; charset=").append(charset)
                    // 参数头设置完以后需要两个换行，然后才是参数内容
                    .append(NEWLINE)
                    .append(NEWLINE)
                    .append(entry.getValue())
                    .append(NEWLINE);
        }
        dos.writeBytes(params.toString());
        dos.flush();
    }

    /**
     * 传输文件专用 - 将文件输出到数据流中
     *
     * @param uploadFiles 要上传的文件
     * @param dos         数据输出流
     */
    private void writeFiles(Map<String, File> uploadFiles,
                            DataOutputStream dos,
                            int bufferSize,
                            UploadListener uploadListener) throws IOException {
        if (uploadFiles == null) {
            return;
        }
        try {
            //检查要上传的文件
            for (Map.Entry<String, File> entry : uploadFiles.entrySet()) {
                if (!entry.getValue().exists() || entry.getValue().isDirectory()) {
                    throw new IllegalArgumentException("file not exists or is a directory which key is [" + entry.getKey() + "]");
                }
            }

            FileUploadListener fileUploadListener = null;
            long totalBytes = 0L;
            long readBytes = 0L;
            //统计总字节数，供普通上传监听器用
            if (uploadListener != null) {
                for (Map.Entry<String, File> entry : uploadFiles.entrySet()) {
                    totalBytes += entry.getValue().length();
                }
                //转为FileUploadListener
                if (uploadListener instanceof FileUploadListener) {
                    fileUploadListener = (FileUploadListener) uploadListener;
                }
            }

            int loopCount = 1;
            for (Map.Entry<String, File> entry : uploadFiles.entrySet()) {
                //单个文件的输出（上传）
                long singleReadBytes = writeSingleFile(loopCount, dos, entry.getKey(), entry.getValue(), fileUploadListener, uploadListener, bufferSize, totalBytes, readBytes);
                if (uploadListener != null) {
                    readBytes += singleReadBytes;
                    if (fileUploadListener != null) {
                        fileUploadListener.onFilesUploading(uploadFiles.size(), loopCount);
                    }
                }
                loopCount++;
            }//end of for
            //上传完成的回调
            if (uploadListener != null) {
                uploadListener.onCompleted(uploadFiles);
            }
        } catch (IOException e) {
            if (uploadListener != null) {
                uploadListener.onError(e);
            }
            throw e;
        }
    }//end of writeFiles()

    /**
     * 对单个文件的输出操作
     *
     * @param fileNo 循环的第几个文件
     * @param dos    数据输出流
     * @param key    元信息里的name的值
     * @param file   要输出的文件
     */
    private long writeSingleFile(int fileNo,
                                 DataOutputStream dos,
                                 String key,
                                 File file,
                                 FileUploadListener fileUploadListener,
                                 UploadListener uploadListener,
                                 int bufferSize,
                                 long totalBytes,
                                 long readBytes)
            throws IOException {
        String headParams = TOW_HYPHENS + BOUNDARY + NEWLINE +
                "Accept-Encoding: gzip" +
                NEWLINE +
                "Content-Length:" + file.length() +
                NEWLINE +
                "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + file.getName() + "\"" +
                NEWLINE +
                "Content-Type:" + getFileContentType(file) +
                NEWLINE +
                "content-transfer-encoding: binary" +
                NEWLINE +
                NEWLINE;// 参数头设置完以后需要两个换行，然后才是参数内容
        dos.writeBytes(headParams);

        try (FileInputStream fis = new FileInputStream(file)) {
            int len;
            long readLength = 0L;//已经读取的长度
            long fileLength = file.length();//文件总长度
            //确定上传文件缓冲区的大小
            byte[] buffer = new byte[bufferSize];
            //开始上传
            while ((len = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, len);
                if (uploadListener != null) {
                    readLength += len;
                    int totalProgress = (int) ((readBytes / totalBytes) * 100);
                    if (fileUploadListener != null) { //针对单个上传文件
                        int fileProgress = (int) (100L * readLength / fileLength);
                        fileUploadListener.onSingleUploading(fileNo, file, fileLength, readLength, fileProgress);
                    }
                    //总进度的回调
                    uploadListener.onUploading(totalBytes, (readBytes + readLength), totalProgress);
                }
            }
            dos.writeBytes(NEWLINE);
            dos.flush();
            return readLength;
        }
    }

    /**
     * 根据文件后缀名获取对应的contentType
     * @return 获取失败则默认为application/octet-stream
     */
    public static String getFileContentType(File file) {
        String fileContentType = "application/octet-stream";
        try {
            String[] arr = file.getName().split("\\.");
            String extension = arr[arr.length - 1];
           return MimeType.getContentTypeByFileExtension(extension);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContentType;
    }

}
