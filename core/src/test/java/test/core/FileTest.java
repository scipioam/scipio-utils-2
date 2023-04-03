package test.core;

import com.github.scipioutils.core.io.FileUtils;
import org.junit.jupiter.api.Test;

/**
 * @author Alan Scipio
 * created on 2023/4/3
 */
public class FileTest {

    @Test
    public void encodingConvert() throws Exception {
        String srcDir = "E:\\Projects\\BBC_FP11\\temp\\RP040";
        String destDir = "E:\\Projects\\BBC_FP11\\temp\\RP040\\result";
        String srcEncoding = "Shift-JIS";
        String destEncoding = "UTF-8";

        System.out.println("源目录：" + srcDir);
        System.out.println("目标目录：" + destDir);
        System.out.println("源编码：" + srcEncoding);
        System.out.println("目标编码：" + destEncoding);
        System.out.println("开始转码...");
        FileUtils.encodingConvert(srcDir, destDir, srcEncoding, destEncoding, (dir, fileName) -> fileName.endsWith(".vb"));
        System.out.println("转换完毕");
    }

}
