package com.cnpc.framework.base.service;

import com.cnpc.framework.base.pojo.PageInfo;
import com.cnpc.framework.base.pojo.ScheduleJob;
import com.cnpc.framework.query.entity.Query;
import com.cnpc.framework.query.entity.QueryCondition;
import org.quartz.SchedulerException;

import java.util.List;

public interface JobService {

	/**
	 * 调度所有Cron任务
	 */
	public abstract void scheduleCronJobs() throws SchedulerException;


	/**
	 * 获取正在运行中的任务
	 */
	public abstract List<ScheduleJob> getRunningJobs() throws SchedulerException;

	/**
	 * 暂停任务
	 * 
	 * @throws SchedulerException
	 */
	public abstract void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;

	/**
	 * 恢复任务
	 */
	public abstract void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;

	/**
	 * 删除任务 删除任务后，所对应的trigger也将删除
	 */
	public abstract void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;

	/**
	 * 立即运行任务
	 */
	public abstract void triggerJob(ScheduleJob scheduleJob) throws SchedulerException;

	/**
	 * 更新时间任务表达式
	 */
	public abstract void rescheduleJob(ScheduleJob scheduleJob) throws SchedulerException;

	/**
	 * 新增Cron任务
	 */
	public abstract void scheduleCronNewJob(ScheduleJob scheduleJob) throws SchedulerException, ClassNotFoundException;
	/**
	 * 新增任务
	 */
	public abstract void scheduleSingleJob(ScheduleJob scheduleJob) throws SchedulerException, ClassNotFoundException;

	/**
	 * 新增或编辑任务Cron
	 */
	public abstract void scheduleCronSingleJob(ScheduleJob scheduleJob) throws SchedulerException, ClassNotFoundException;

	/**
	 * 获取所有计划中的任务
	 * 
	 * @param condition
	 * @param pageInfo
	 * @return
	 * @throws SchedulerException
	 */
	public List getAllJobs(QueryCondition condition, PageInfo pageInfo) throws SchedulerException;

	/**
	 * 获取任务列表
	 * 
	 * @param scheduleJob
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getScheduleJob(ScheduleJob scheduleJob) throws SchedulerException;
    
	/**
	 * 获取正在执行的任务列表
	 * @param condition
	 * @param pageInfo
	 * @return
	 * @throws SchedulerException
	 */
	public List getRunningJobs(QueryCondition condition, PageInfo pageInfo) throws SchedulerException;

}