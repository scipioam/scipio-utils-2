package com.github.scipioutils.net.catcher;

import lombok.Data;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Alan Scipio
 * create date: 2022/10/24
 */
@Data
public class FileIOHandler implements IOHandler {

    private String filePath; //文件路径
    private String fileName; //文件名
    private String fileExtension = "txt"; //文件后缀，默认txt，如不需要后缀则手动set为null

    private boolean isAppend = false; //是否为追加，为false则不是追加而是覆盖

    public FileIOHandler() {
    }

    public FileIOHandler(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public FileIOHandler(String filePath, String fileName, String fileExtension) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void handle(WebInfo webInfo, Document document) {
        List<String> contentList = getFileContent(webInfo);
        File parentDir = new File(filePath);
        String finalFileExtension = (StringUtil.isBlank(fileExtension) ? "" : ("." + fileExtension));
        File file = new File(filePath + File.separatorChar + fileName + finalFileExtension);
        //创建父目录(如果不存在)
        if (!parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("parent directories created: " + parentDir.getPath());
        }
        //创建文件(如果不存在)
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("file created: " + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        //开始将内容写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, isAppend))) {
            System.out.println("start write data into file[" + file.getPath() + "], isAppend if file existed:" + isAppend);
            for (String line : contentList) {
                line += "\n";
                writer.write(line);
                writer.flush();
            }
            System.out.println("file have been written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List<String> getFileContent(WebInfo webInfo) {
        //结果集检查
        CatchResult catchResult = webInfo.getCatchResult();
        if (catchResult == null) {
            throw new IllegalArgumentException("catchResult in webInfo is null while do FileIOListener.onProcess()");
        }
        List<String> resultStrList = catchResult.getStrList();
        if (resultStrList == null || resultStrList.size() == 0) {
            throw new IllegalArgumentException("resultStrList in catchResult is null while do FileIOListener.onProcess()");
        }
        return resultStrList;
    }

}
