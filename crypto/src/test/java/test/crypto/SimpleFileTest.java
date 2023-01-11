package test.crypto;

import com.github.scipioutils.crypto.SimpleFileCrypto;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * {@link SimpleFileCrypto}的相关测试
 *
 * @author Alan Scipio
 * created on 2023/1/11
 */
public class SimpleFileTest {

    @Test
    public void test0() throws Exception {
        //源文件
        File srcFile = new File("");
        //加密文件
        File encFile = new File("");
        //解密文件
        File decFile = new File("");

        SimpleFileCrypto crypto = new SimpleFileCrypto()
                .setKey(460) //指定密钥
                .setProcessingListener((read, total) ->
                    System.out.println("Processing:  [" + read + "/" + total + "]  bytes")
                );

        System.out.println("开始加密文件：  " + srcFile.getPath());
        crypto.encrypt(srcFile, encFile);
        System.out.println("加密完成：  " + encFile.getPath());
        System.out.println("-----------------------------------------------------------");
        System.out.println("开始解密文件：  " + encFile.getPath());
        crypto.decrypt(encFile, decFile);
        System.out.println("解密完成：  " + decFile.getPath());
    }

}
