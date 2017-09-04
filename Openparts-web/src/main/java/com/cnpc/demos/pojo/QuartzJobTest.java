package com.cnpc.demos.pojo;

import com.cnpc.framework.utils.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJobTest implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(DateUtil.getCurrDateTimeStr()+":----hello world----");
		//ScheduleJob scheduleJob=(ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
		//System.out.println("任务名称=["+scheduleJob.getJobName()+"]");
		
	}
	

}