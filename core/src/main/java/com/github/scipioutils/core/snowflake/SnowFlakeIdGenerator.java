package com.github.scipioutils.core.snowflake;

import java.util.HashMap;
import java.util.Map;

/**
 * 雪花算法下的ID生成器
 *
 * @author Alan Scipio
 * @since 2022/7/15
 */
public class SnowFlakeIdGenerator {

    //默认id生成器（不指定机器id和数据中心id）
    private static final String DEFAULT_SEQ = "defaultSeq";
    private static final Map<String, Sequence> SEQUENCE_MAP = new HashMap<>();

    private SnowFlakeIdGenerator() {
    }

    /**
     * 雪花算法下，生成ID
     */
    public static long nextId() {
        Sequence sequence = SEQUENCE_MAP.computeIfAbsent(DEFAULT_SEQ, key -> SequenceBuilder.builder().build());
        return sequence.nextId();
    }

    /**
     * 雪花算法下，生成ID
     *
     * @param workerId     机器ID
     * @param datacenterId 数据中心ID
     */
    public static long nextId(long workerId, long datacenterId) {
        Sequence sequence = SEQUENCE_MAP.computeIfAbsent(workerId + "-" + datacenterId, key -> SequenceBuilder.builder()
                .setWorkerId(workerId)
                .setDatacenterId(datacenterId)
                .build());
        return sequence.nextId();
    }

    public static String nextIdStr() {
        return nextId() + "";
    }

    public static String nextIdStr(long workerId, long datacenterId) {
        return nextId(workerId, datacenterId) + "";
    }

}
