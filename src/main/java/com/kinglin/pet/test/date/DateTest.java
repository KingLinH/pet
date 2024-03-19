package com.kinglin.pet.test.date;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author huangjl
 * @description
 * @since 2023-09-05 9:22
 */
public class DateTest {

    public static void main(String[] args) {

        List<SchedulingDateDTO> totalTimeRangeList = new ArrayList<>();
        List<SchedulingDateDTO> allSplitterDtoList = new ArrayList<>();

        totalTimeRangeList.add(new SchedulingDateDTO(LocalDateTime.of(2023, 9, 3, 8, 0, 0),
                LocalDateTime.of(2023, 9, 3, 19, 0, 0)));

        totalTimeRangeList.add(new SchedulingDateDTO(LocalDateTime.of(2023, 9, 3, 20, 0, 0),
                LocalDateTime.of(2023, 9, 3, 23, 0, 0)));


        allSplitterDtoList.add(new SchedulingDateDTO(LocalDateTime.of(2023, 9, 3, 9, 0, 0),
                LocalDateTime.of(2023, 9, 3, 10, 0, 0)));
        allSplitterDtoList.add(new SchedulingDateDTO(LocalDateTime.of(2023, 9, 3, 13, 0, 0),
                LocalDateTime.of(2023, 9, 3, 14, 0, 0)));
        allSplitterDtoList.add(new SchedulingDateDTO(LocalDateTime.of(2023, 9, 3, 17, 0, 0),
                LocalDateTime.of(2023, 9, 3, 21, 0, 0)));

        //
        allSplitterDtoList.add(new SchedulingDateDTO(LocalDateTime.of(2023, 9, 3, 15, 0, 0),
                LocalDateTime.of(2023, 9, 3, 18, 0, 0)));
//        merge(allSplitterDtoList);
        mergeIntersectingTime(allSplitterDtoList);
        List<SchedulingDateDTO> siltTimeList = getSiltTimeList(totalTimeRangeList, allSplitterDtoList);
        siltTimeList.forEach(System.out::println);
    }

    private static List<SchedulingDateDTO> getSiltTimeList(List<SchedulingDateDTO> totalTimeRangeList, List<SchedulingDateDTO> allSplitterDtoList) {
        int firstCurrIndex = 0;
        int secondCurrIndex = 0;
        while (firstCurrIndex < allSplitterDtoList.size() && secondCurrIndex < totalTimeRangeList.size()) {
            // 要拆分的时间对象
            LocalDateTime firstStartTime = allSplitterDtoList.get(firstCurrIndex).getStartLocalDateTime();
            LocalDateTime firstEndTime = allSplitterDtoList.get(firstCurrIndex).getEndLocalDateTime();
            long firstStartTimeMilliSecond = getMilliSecondFromLocalDateTime(firstStartTime);
            long firstEndTimeMilliSecond = getMilliSecondFromLocalDateTime(firstEndTime);
            // 总的时间对象，需要从这些时间中进行拆分
            LocalDateTime secondStartTime = totalTimeRangeList.get(secondCurrIndex).getStartLocalDateTime();
            LocalDateTime secondEndTime = totalTimeRangeList.get(secondCurrIndex).getEndLocalDateTime();
            long secondStartTimeMilliSecond = getMilliSecondFromLocalDateTime(secondStartTime);
            long secondEndTimeMilliSecond = getMilliSecondFromLocalDateTime(secondEndTime);

            if (firstEndTimeMilliSecond <= secondStartTimeMilliSecond) {
                firstCurrIndex++;
            } else if (firstEndTimeMilliSecond < secondEndTimeMilliSecond && firstStartTimeMilliSecond <= secondStartTimeMilliSecond) {
                // 会产生一段
                totalTimeRangeList.set(secondCurrIndex, new SchedulingDateDTO(firstEndTime, secondEndTime));
                firstCurrIndex++;
            } else if (firstEndTimeMilliSecond < secondEndTimeMilliSecond && firstStartTimeMilliSecond > secondStartTimeMilliSecond) {
                // 会产生两段：此时产生的第一段是不会再被拆分的，第二段是有可能继续被拆分的。
                totalTimeRangeList.set(secondCurrIndex, new SchedulingDateDTO(firstEndTime, secondEndTime));
                totalTimeRangeList.add(secondCurrIndex, new SchedulingDateDTO(secondStartTime, firstStartTime));
                // 让p2CurrIndex来到第二段的位置
                ++firstCurrIndex;
                ++secondCurrIndex;
            } else if (firstEndTimeMilliSecond >= secondEndTimeMilliSecond && firstStartTimeMilliSecond <= secondStartTimeMilliSecond) {
                secondCurrIndex++;
            } else if (firstEndTimeMilliSecond >= secondEndTimeMilliSecond && firstStartTimeMilliSecond < secondEndTimeMilliSecond) {
                // 会产生一段
                totalTimeRangeList.set(secondCurrIndex, new SchedulingDateDTO(secondStartTime, firstStartTime));
                ++secondCurrIndex;
            } else {
                ++secondCurrIndex;
            }
        }
        return totalTimeRangeList;
    }

    /**
     * 合并数组中对象时间相交的数据
     *
     * @param splitDateList list
     * @date 2023/9/5 11:20
     */
    private static void mergeIntersectingTime(List<SchedulingDateDTO> splitDateList) {
        if (splitDateList == null || splitDateList.isEmpty()) {
            return;
        }
        splitDateList.sort(Comparator.comparing(SchedulingDateDTO::getStartLocalDateTime));
        Deque<SchedulingDateDTO> deque = new LinkedList<>();
        Iterator<SchedulingDateDTO> iterator = splitDateList.iterator();
        while (iterator.hasNext()) {
            SchedulingDateDTO dto = iterator.next();
            deque.addLast(dto);
            iterator.remove();
        }
        while (deque.size() >= 2) {
            SchedulingDateDTO first = deque.pollFirst();
            SchedulingDateDTO second = deque.pollFirst();
            long firstEndTimeMilliSecond = getMilliSecondFromLocalDateTime(first.getEndLocalDateTime());
            if (Objects.isNull(second)) {
                continue;
            }
            LocalDateTime secondStartTime = second.getStartLocalDateTime();
            LocalDateTime secondEndTime = second.getEndLocalDateTime();
            long secondStartTimeMilliSecond = getMilliSecondFromLocalDateTime(secondStartTime);
            long secondEndTimeMilliSecond = getMilliSecondFromLocalDateTime(secondEndTime);
            if (secondStartTimeMilliSecond >= firstEndTimeMilliSecond) {
                // 直接进行下一次循环。将second压入队列，first作为结果返回
                splitDateList.add(first);
                deque.addFirst(second);
            } else {
                if (secondEndTimeMilliSecond >= firstEndTimeMilliSecond) {
                    deque.addFirst(new SchedulingDateDTO(first.getStartLocalDateTime(), secondEndTime));
                } else {
                    deque.addFirst(first);
                }
            }
        }
        while (!deque.isEmpty()) {
            splitDateList.add(deque.pollFirst());
        }
    }

    /**
     * 从LocalDateTime对象中获取毫秒数
     *
     * @param time LocalDateTime 实例对象
     * @return long 毫秒数
     * @date 2023/9/5 11:20
     */
    private static long getMilliSecondFromLocalDateTime(LocalDateTime time) {
        if (ObjectUtils.isNull(time)) {
            throw new RuntimeException("The time to be converted is not allowed to be empty !");
        }
        return time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

}
