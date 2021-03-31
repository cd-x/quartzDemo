package com.example.quartzDemo.info;

import java.util.Date;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimerInfo {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date endDate;
	
	private int repeatInterval;
	private boolean runForever;
	private int totalFireCount;
	
	private String jobName;
}
