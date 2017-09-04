package com.cnpc.framework.base.pojo;

import java.util.Date;

/**
 * 计划任务信息
 * 
 * @author jrn Date 15-4-23 Time 上午10:36
 * 
 */
public class ScheduleJob {

	/** 任务id */
	private String jobId;

	/** 任务名称 */
	private String jobName;

	/** 任务分组 */
	private String jobGroup;
	
	/** trigger名称 */
	private String triggerName;

	/** trigger分组 */
	private String triggerGroup;

	/** 任务状态 0禁用 1启用 2删除 */
	private String jobStatus;

	/** 任务运行时间表达式 
	 * CronTrigger 
	 */
	private String cronExpression;

	/** 任务描述 */
	private String desc;
	
	/**	反射方式注入class */
	private String jobClass;
	
	/**
	 * trigger类型 CronTrigger SimpleTrigger
	 */
	private String triggerType;
	/**
	 * 开始时间  SimpleTrigger/CronTrigger
	 */
	private Date startTime;
	/**
	 * 结束时间  SimpleTrigger/CronTrigger
	 */
	private Date endTime;
	/**
	 * 重复次数 SimpleTrigger
	 */
	private Integer repeatCount;
	/**
	 * 重复间隔  SimpleTrigger
	 */
	private Integer repeatInterval;
	/**
	 * 重复间隔 SimpleTrigger 含单位
	 */
	private String repeatIntervalStr;
	/**
	 * 重复间隔单位
	 */
	private String repeatUnit; //hour minute second
	/**
	 * 重复类型
	 */
	private String repeatType;

	public String getRepeatUnit() {
		return repeatUnit;
	}

	public void setRepeatUnit(String repeatUnit) {
		this.repeatUnit = repeatUnit;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(Integer repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public String getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}

	public String getRepeatIntervalStr() {
		return repeatIntervalStr;
	}

	public void setRepeatIntervalStr(String repeatIntervalStr) {
		this.repeatIntervalStr = repeatIntervalStr;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

}