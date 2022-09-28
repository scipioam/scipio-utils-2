package test.core;

import com.github.scipioutils.core.snowflake.SnowFlakeIdGenerator;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Alan Scipio
 * create date: 2022/9/27
 */
public class SnowflakeIdTest {

    @Test
    public void timeCustomTest() {
        String id = SnowFlakeIdGenerator.nextIdStr();
        System.out.println("id     [" + id + "]");
        System.out.println("length [" + id.length() + "]");

        System.out.println("-------------------------------------------------------\n");

        long startTime = 1664268238500L;
        LocalDateTime startTimeObj = Instant.ofEpochMilli(startTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        long interval = System.currentTimeMillis() - startTime;
        long timeBits = 40L;
        long timeMask = ~(-1L << timeBits);
        System.out.println("起始时间戳:                 " + startTime);
        System.out.println("起始时间戳解读为:            " + startTimeObj);
        System.out.println("时间差(当前时间戳-起始时间戳): " + interval);
        System.out.println("雪花算法时间戳长度是:         " + timeBits);
        System.out.println("雪花算法时间戳掩码是:         " + timeMask);
        System.out.println("当前时间戳与掩码进行与运算:    " + (interval & timeMask));
        System.out.println("当前时间戳与掩码进行与运算:    " + (interval & timeMask));

        long yearLength = (1L << timeBits) / (1000L * 60 * 60 * 24 * 365);
        LocalDateTime endTimeObj = startTimeObj.plusYears(yearLength);
        System.out.println("此时间长度支持最大年限:       " + yearLength + "年");
        System.out.println("具体的结束时间为:            " + endTimeObj);

        System.out.println(System.currentTimeMillis());
    }

}
