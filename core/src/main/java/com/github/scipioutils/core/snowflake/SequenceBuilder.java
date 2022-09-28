package com.github.scipioutils.core.snowflake;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Alan Scipio
 * create date: 2022/9/27
 */
@Setter
@Accessors(chain = true)
public class SequenceBuilder {

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private long twepoch = 1664268238500L;

    /**
     * 时间戳的bit位数，标准为41位
     */
    private long timestampBits = 41L;

    /**
     * 机房id的bit位数，标准为5位
     */
    private long datacenterIdBits = 5L;

    /**
     * 机器id的bit位数，标准为5位
     */
    private long workerIdBits = 5L;

    /**
     * 序列号的bit位数，即每毫秒内产生的id数，标准为2的12次方个
     */
    private long sequenceBits = 12L;

    /**
     * 所属机房id
     */
    private Long datacenterId;

    /**
     * 所属机器id
     */
    private Long workerId;

    public static SequenceBuilder builder() {
        return new SequenceBuilder();
    }

    public Sequence build() {
        Sequence sequence;
        if (datacenterId != null && workerId != null) {
            sequence = new Sequence(workerId, datacenterId);
        } else {
            sequence = new Sequence();
        }
        sequence.init(twepoch, timestampBits, datacenterIdBits, workerIdBits, sequenceBits);
        return sequence;
    }

}
