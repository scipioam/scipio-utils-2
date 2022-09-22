package com.github.scipioutils.core.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * 文件操作工具
 *
 * @author Alan Min
 * @since 1.0.0
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileUtils {

    /**
     * 默认缓冲区大小
     */
    public static final int DEFAULT_BUFFER_SIZE = 1024;

    private FileUtils() {
    }

    /**
     * 拷贝文件
     *
     * @param sourceFile: 源文件
     * @param targetFile: 目标文件，不存在就创建
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        if (!sourceFile.exists()) {
            throw new FileNotFoundException("source file is not found: " + sourceFile.getPath());
        }
        checkOrCreate(targetFile);
        try (FileInputStream fis = new FileInputStream(sourceFile); FileOutputStream fos = new FileOutputStream(targetFile)) {
            FileChannel in = fis.getChannel();//得到对应的文件通道
            FileChannel out = fos.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入
        }
    }

    /**
     * 拷贝文件
     *
     * @return 目标文件
     */
    public static File copyFile(String sourcePath, String targetPath) throws IOException {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);
        copyFile(sourceFile, targetFile);
        return targetFile;
    }

    /**
     * 删除指定文件夹及其所有子文件夹
     *
     * @param dir     要删除的根目录
     * @param keepDir 是否保留所有文件夹。true：保留
     */
    public static void deleteFiles(File dir, boolean keepDir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children == null) {
                return;
            }
            //递归删除子目录下的所有文件
            for (String child : children) {
                deleteFiles(new File(dir, child), keepDir);
            }
        }
        // 目录此时为空，可以删除
        if (!keepDir) {
            dir.delete();
        }
    }

    public static void deleteFiles(File dir) {
        deleteFiles(dir, false);
    }

    /**
     * 检查文件夹下是否有文件
     *
     * @param root       开始的根路径
     * @param includeDir 统计是否包括文件夹，true代表是
     * @return 子文件个数
     */
    public static int countFiles(File root, boolean includeDir) throws FileNotFoundException {
        FileCounter fileCounter = new FileCounter();
        return fileCounter.count(root, includeDir, null);
    }

    /**
     * 检查文件夹下是否有文件（不包括文件夹）
     *
     * @param root 开始的根路径
     * @return 子文件个数
     */
    public static int countFiles(File root) throws FileNotFoundException {
        FileCounter fileCounter = new FileCounter();
        return fileCounter.count(root);
    }


    /**
     * 将字节数据写入一个文件
     *
     * @param file 要写入的文件对象
     * @param data 要写入的数据
     * @throws IOException 写入失败
     */
    public static void writeBytes(File file, byte[] data) throws IOException {
        try (OutputStream fos = new FileOutputStream(file); OutputStream os = new BufferedOutputStream(fos)) {
            os.write(data);
        }
    }

    /**
     * 将字符串内容写入一份文件里
     *
     * @param file    要写入的文件
     * @param content 写入内容
     * @param append  追加模式。false则为覆盖模式
     * @throws IOException 创建文件或目录失败，写入失败
     */
    public static void writeString(File file, String content, boolean append) throws IOException {
        checkOrCreate(file);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            writer.write(content);
            writer.flush();
        }
    }

    /**
     * 从文件读取字符串
     *
     * @param file       要读取的文件
     * @param bufferSize 一次读取的缓冲池大小（单位是一个字符）
     * @param charset    字符集
     */
    public static String readString(File file, int bufferSize, Charset charset) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("file can not be found! path: " + file.getPath());
        } else if (file.isDirectory()) {
            throw new IllegalArgumentException("file path can not be a directory path!");
        }

        StringBuilder sb = new StringBuilder();
        try (FileInputStream is = new FileInputStream(file); InputStreamReader reader = new InputStreamReader(is, charset)) {
            int count;
            char[] buffer = new char[bufferSize];//每次读取的缓冲区，其大小决定了读取速度
            while ((count = reader.read(buffer)) != -1) {  //读取
                sb.append(new String(buffer, 0, count));
            }
        }
        return sb.toString();
    }

    /**
     * 从文件读取字符串 - 默认缓冲池大小
     */
    public static String readString(File file, String charset) throws IOException {
        return readString(file, DEFAULT_BUFFER_SIZE, Charset.forName(charset));
    }

    /**
     * 从文件读取字符串 - 默认字符集，默认缓冲池大小
     */
    public static String readString(File file) throws IOException {
        return readString(file, DEFAULT_BUFFER_SIZE, Charset.defaultCharset());
    }

    /**
     * 检查给定的文件对象是否为一个文件路径，如果是但不存在的话就创建
     *
     * @param file 给定的文件全路径
     * @throws IllegalArgumentException 给定的文件对象为文件夹时，抛出此异常
     * @throws IOException              创建失败
     */
    public static void checkOrCreate(File file) throws IllegalArgumentException, IOException {
        //检查这个文件是否存在
        if (!file.exists()) {
            if (file.isDirectory()) {
                throw new IllegalArgumentException("local file path can not be a directory path!");
            }
            try {
                //确保父目录存在
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                //开始创建
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("create file failed! path is: " + file.getPath());
            }
        }
    }

    /**
     * 往文件里写数据（数据来源于流）
     *
     * @param file 目标文件
     * @param is   数据来源
     * @throws IOException 读取或写入失败，或关闭输出流失败
     */
    public static void writeStream(File file, InputStream is, int bufferSize) throws IOException {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("buffer size must greater then 0");
        }
        if (is == null) {
            throw new IllegalArgumentException("InputStream is null");
        }
        //检查文件
        checkOrCreate(file);
        //开始输出
        try (FileOutputStream os = new FileOutputStream(file)) {
            int count;
            byte[] buffer = new byte[bufferSize];//每次读取的缓冲区，其大小决定了读取速度
            while ((count = is.read(buffer)) != -1) {  //读取
                os.write(buffer, 0, count);//把读出的每一块，向目标输出
                os.flush();
            }
        } finally {
            is.close();
        }
    }

    /**
     * 往文件里写数据（数据来源于流） (默认缓冲区大小)
     */
    public static void writeStream(File file, InputStream is) throws IOException {
        writeStream(file, is, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 往文件里写字节数据
     *
     * @param filePath 本地文件地址
     * @param data     要输出的字节数据
     * @throws IOException 读取或写入失败，或关闭输出流失败
     */
    public static void writeBytes(String filePath, byte[] data, int bufferSize) throws IOException {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("buffer size must greater then 0");
        }
        //检查文件
        File localFile = new File(filePath);//获取本地文件对象
        checkOrCreate(localFile);
        //开始输出
        try (ByteArrayInputStream is = new ByteArrayInputStream(data); FileOutputStream os = new FileOutputStream(localFile)) {
            int count;
            byte[] buffer = new byte[bufferSize];//每次读取的缓冲区，其大小决定了读取速度
            while ((count = is.read(buffer)) != -1) {  //读取
                os.write(buffer, 0, count);//把读出的每一块，向客户端输出
                os.flush();
            }
        }
    }

    /**
     * 往文件里写字节数据(默认缓冲区大小)
     */
    public static void writeBytes(String filePath, byte[] data) throws IOException {
        writeBytes(filePath, data, DEFAULT_BUFFER_SIZE);
    }


}
