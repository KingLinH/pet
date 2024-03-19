package com.kinglin.pet.util;

import com.kinglin.pet.exception.PetException;

/**
 * @author huangjl
 * @description 雪花算法
 * @since 2024-01-08 11:10
 */
public class SnowFlakeUtil {

    // 起始时间戳 2024-01-01 00:00:00
    private final static long START_TIME = 1704038400L;

    // 每部分的位数
    private final static long SEQUENCE_BIT = 12; // 12位序列号
    private final static long MACHINE_BIT = 5; // 5位序机器id
    private final static long DATACENTER_BIT = 5; // 5位机房id

    // 每部分最大值
    private final static long MAX_SEQUENCE_NUM = ~(-1L << SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);

    // 每部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    // 机房id
    private final long datacenterId;
    // 机器id
    private final long machineId;
    // 序列号
    private long sequence = 0L;
    // 上次的时间戳
    private long lastTimestamp = -1L;

    public SnowFlakeUtil(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new PetException("datacenterId can not be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new PetException("machineId can not be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public synchronized long getNextId() {
        long currentTimeStamp = getNewTimeStamp();
        if (currentTimeStamp < lastTimestamp) {
            throw new PetException("Clock moved backwards, Refusing to generate id");
        }
        if (currentTimeStamp == lastTimestamp) {
            // 若在相同毫秒内 序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE_NUM;
            // 同一毫秒的序列号已达到最大值
            if (sequence == 0L) {
                currentTimeStamp = getNextMill();
            }
        } else {
            // 若不做同一毫秒内，则序列号为0
            sequence = 0;
        }
        lastTimestamp = currentTimeStamp;
        return (currentTimeStamp - START_TIME) << TIMESTAMP_LEFT // 时间戳部分
                | datacenterId << DATACENTER_LEFT // 机房id部分
                | machineId << MACHINE_LEFT // 机器id部分
                | sequence; // 序列号部分
    }

    private long getNextMill() {
        long mill = getNewTimeStamp();
        while (mill <= lastTimestamp) {
            mill = getNewTimeStamp();
        }
        return mill;
    }

    private long getNewTimeStamp() {
        return System.currentTimeMillis();
    }
}
