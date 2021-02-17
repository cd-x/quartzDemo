package com.example.quartzDemo.info;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TimerInfo {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date endDate;
	
	private long repeatIntervalMs;
	private boolean runForever;
	private int totalFireCount;
	
	private String jobName;
	
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getRepeatIntervalMs() {
		return repeatIntervalMs;
	}
	public void setRepeatIntervalMs(long repeatIntervalMs) {
		this.repeatIntervalMs = repeatIntervalMs;
	}
	public boolean isRunForever() {
		return runForever;
	}
	public void setRunForever(boolean runForever) {
		this.runForever = runForever;
	}
	public int getTotalFireCount() {
		return totalFireCount;
	}
	public void setTotalFireCount(int totalFireCount) {
		this.totalFireCount = totalFireCount;
	}
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Override
	public String toString() {
		return "TimerInfo [startDate=" + startDate + ", endDate=" + endDate + ", repeatIntervalMs=" + repeatIntervalMs
				+ ", runForever=" + runForever + ", totalFireCount=" + totalFireCount + ", jobName=" + jobName +"]";
	}
}
