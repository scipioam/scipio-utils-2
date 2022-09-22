package com.github.scipioutils.core.io.stream;

import com.github.scipioutils.core.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.*;

/**
 * 流解析工具类
 *
 * @author Alan Scipio
 * create date 2018/4/16
 */
@Data
@Accessors(chain = true)
public class StreamParser {

    /**
     * 默认缓冲区大小
     */
    public static final int DEFAULT_BUFFER_SIZE = 1024;

    private int bufferSize = DEFAULT_BUFFER_SIZE;

    private String charsetName = "UTF-8";

    public static StreamParser build() {
        return new StreamParser();
    }

    /**
     * 从流中读取数据为字符串
     *
     * @param in 数据来源
     * @return 字符串结果
     */
    public String readAsString(InputStream in, InputStreamWrapper wrapper) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("inputStream can not be null");
        }
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("buffSize can not less or equal then 0");
        }
        if (StringUtils.isBlank(charsetName)) {
            charsetName = "UTF-8";
        }
        try (InputStream finalIn = (wrapper == null ? in : wrapper.wrap(in)); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            //开始读取
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = finalIn.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toString(charsetName);
        }
    }

    public String readAsString(InputStream in) throws IOException {
        return readAsString(in, null);
    }

    /**
     * 从流中读取字节数据
     */
    public byte[] read(InputStream in, InputStreamWrapper wrapper) throws IOException {
        try (InputStream finalIn = (wrapper == null ? in : wrapper.wrap(in)); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            //开始读取
            int count;
            byte[] buf = new byte[bufferSize];
            while ((count = finalIn.read(buf)) != -1) {
                if (count > 0) {
                    out.write(buf, 0, count);
                }
            }
            return out.toByteArray();
        }
    }

    public byte[] read(InputStream in) throws IOException {
        return read(in, null);
    }


    /**
     * 从gzip流中读取字节数据
     *
     * @param in 数据来源
     * @return 字符串结果
     */
    public String readGZIPStream(InputStream in) throws IOException {
        return readAsString(in, InputStreamWrapper.GZIP);
    }

}
