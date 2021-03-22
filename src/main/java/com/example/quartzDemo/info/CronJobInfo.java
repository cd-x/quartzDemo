package com.example.quartzDemo.info;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CronJobInfo {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;

    private String cronExpression;
    private String jobName;

}
