package com.cnpc.framework.base.pojo;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cnpc.framework.utils.DateUtil;

public class QuartzJobFactory implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(DateUtil.getCurrDateTimeStr()+"  任务成功运行");
		JobDataMap dataMap=context.getMergedJobDataMap();
		//ScheduleJob scheduleJob=(ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
		//System.out.println("任务名称=["+scheduleJob.getJobName()+"]");
		
	}
	

}
