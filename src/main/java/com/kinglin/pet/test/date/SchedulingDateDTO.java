package com.kinglin.pet.test.date;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SchedulingDateDTO {
    /**
     * 起始时间
     */
    private LocalDateTime startLocalDateTime;
    /**
     * 结束时间
     */
    private LocalDateTime endLocalDateTime;
}
