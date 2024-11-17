package cn.wbnull.hellobill.common.util;

/**
 * 雪花算法工具类
 *
 * @author dukunbiao(null)  2024-11-17
 */
public class SnowflakeUtils {

    // 起始时间戳（毫秒）
    private final long twepoch = 1288834974657L;
    // 工作机器ID所占的位数
    private final long workerIdBits = 5L;
    // 数据中心ID所占的位数
    private final long datacenterIdBits = 5L;
    // 最大工作机器ID
    private final long maxWorkerId = ~(-1L << workerIdBits);
    // 最大数据中心ID
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);
    // 序列号所占的位数
    private final long sequenceBits = 12L;
    // 工作机器ID左移位数
    private final long workerIdShift = sequenceBits;
    // 数据中心ID左移位数
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间戳左移位数
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    // 序列号掩码
    private final long sequenceMask = ~(-1L << sequenceBits);

    // 工作机器ID
    private long workerId;
    // 数据中心ID
    private long dataCenterId;
    // 序列号
    private long sequence = 0L;
    // 上一次时间戳
    private long lastTimestamp = -1L;

    private static final class SnowflakeUtilsHolder {
        static final SnowflakeUtils snowflakeUtils = new SnowflakeUtils();
    }

    public static synchronized SnowflakeUtils getInstance() {
        return SnowflakeUtilsHolder.snowflakeUtils;
    }

    public SnowflakeUtils() {
        this.workerId = 1L;
        this.dataCenterId = 1L;
    }

    public SnowflakeUtils(long workerId, long dataCenterId) {
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() {
        long timestamp = genTime();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1L) & 4095L;
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) |
                (dataCenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    public synchronized long nextId(long timestamp) {
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1L) & 4095L;
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) |
                (dataCenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    public String nextIdStr() {
        return Long.toString(nextId());
    }

    public String nextIdStr(long timestamp) {
        return Long.toString(nextId(timestamp));
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = genTime();
        if (timestamp < lastTimestamp) {
            timestamp = genTime();
        }

        return timestamp;
    }

    private long genTime() {
        return System.currentTimeMillis();
    }
}
