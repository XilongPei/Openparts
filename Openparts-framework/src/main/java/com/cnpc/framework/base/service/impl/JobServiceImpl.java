package com.cnpc.framework.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cnpc.framework.base.pojo.PageInfo;
import com.cnpc.framework.base.pojo.QuartzJobFactory;
import com.cnpc.framework.base.pojo.ScheduleJob;
import com.cnpc.framework.base.service.JobService;
import com.cnpc.framework.query.entity.Query;
import com.cnpc.framework.query.entity.QueryCondition;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.cnpc.framework.utils.StrUtil;


@Service("jobService")
public class JobServiceImpl implements JobService {
    @Autowired(required = false)
    private SchedulerFactoryBean schedulerFactoryBean;

    /*
     * 批量调度所有任务
     */
    //TODO   scheduleCronJobs
    public void scheduleCronJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<ScheduleJob> jobList = getAllJobs(null, null);
        for (ScheduleJob job : jobList) {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            // 获取trigger,即在spring配置文件中定义的bean id="myTrigger"
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在，创建一个
            // if (null == trigger) {
            if (!scheduler.checkExists(triggerKey)) {
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(),
                        job.getJobGroup()).build();
                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                // 按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // trigger已经存在，那么更细相应的定时设置
                // 表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }

        }
    }

    /**
     * 调度或更新单个任务
     */
    public void scheduleCronSingleJob(ScheduleJob job) throws SchedulerException, ClassNotFoundException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //List<ScheduleJob> jobList = getAllJobs();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        // 获取trigger,即在spring配置文件中定义的
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClass()))
                    .withIdentity(job.getJobName(), job.getJobGroup()).withDescription(job.getDesc()).build();
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            // 按新的cronExpression表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder)
                    .withDescription(job.getDesc()).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // trigger已经存在，那么更细相应的定时设置
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger
                    .getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .withDescription(
                            job.getDesc().substring(
                                    job.getDesc().indexOf("trigger:") > -1 ? job.getDesc().indexOf("trigger:") + 8 : 0))
                    .build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 调度单个任务 SimpleTrigger 和 CronTrigger
     */
    public void scheduleSingleJob(ScheduleJob job) throws SchedulerException, ClassNotFoundException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        // List<ScheduleJob> jobList = getAllJobs();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroup());
        // 获取trigger
        Trigger trigger = scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        // if (null == trigger) {
        if (!scheduler.checkExists(triggerKey)) {
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClass()))
                    .withIdentity(job.getJobName(), job.getJobGroup()).withDescription(job.getDesc()).build();
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
            trigger = setTriggerParam(job, triggerKey, triggerBuilder, trigger);
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // trigger已经存在，更新相应的定时设置
            TriggerBuilder triggerBuilder = trigger.getTriggerBuilder();
            trigger = setTriggerParam(job, triggerKey, triggerBuilder, trigger);
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }

    }

    /**
     * 设置trigger
     *
     * @param job
     * @param triggerKey
     * @param trigger
     * @param triggerBuilder
     */
    public Trigger setTriggerParam(ScheduleJob job, TriggerKey triggerKey, TriggerBuilder triggerBuilder,
                                   Trigger trigger) {
        if (job.getTriggerType().equals("CronTrigger")) {
            CronScheduleBuilder cronBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            // 按新的cronExpression表达式构建一个新的trigger
            if (job.getStartTime() != null)
                triggerBuilder.startAt(job.getStartTime());
            if (job.getEndTime() != null)
                triggerBuilder.endAt(job.getEndTime());
            //int index=job.getDesc().indexOf(";repeatType:")>-1?job.getDesc().indexOf(";repeatType:"):job.getDesc().length();
            trigger = triggerBuilder.withIdentity(triggerKey).withSchedule(cronBuilder).withDescription(
                    job.getDesc()).build();
        } else if (job.getTriggerType().equals("SimpleTrigger")) {
            SimpleScheduleBuilder simpleBuilder = SimpleScheduleBuilder.simpleSchedule();
            if (job.getStartTime() != null)
                triggerBuilder.startAt(job.getStartTime());
            if (job.getEndTime() != null)
                triggerBuilder.endAt(job.getEndTime());
            if (StrUtil.isNotBlank(job.getRepeatType())) {
                if (job.getRepeatType().equals("repeatForever")) {
                    simpleBuilder=simpleBuilderForever(simpleBuilder,job);

                }
                else if (job.getRepeatType().equals("repeatHourlyForever")) {
                    if (job.getRepeatInterval() != null)
                        simpleBuilder = simpleBuilder.repeatHourlyForever(job.getRepeatInterval());
                    else
                        simpleBuilder = simpleBuilder.repeatHourlyForever();
                } else if (job.getRepeatType().equals("repeatMinutelyForever")) {
                    if (job.getRepeatInterval() != null)
                        simpleBuilder = simpleBuilder.repeatMinutelyForever(job.getRepeatInterval());
                    else
                        simpleBuilder = simpleBuilder.repeatMinutelyForever();
                } else if (job.getRepeatType().equals("repeatSecondlyForever")) {
                    if (job.getRepeatInterval() != null)
                        simpleBuilder = simpleBuilder.repeatSecondlyForever(job.getRepeatInterval());
                    else
                        simpleBuilder = simpleBuilder.repeatSecondlyForever();
                } else if (job.getRepeatType().equals("repeatHourlyForTotalCount")) {
                    if (job.getRepeatCount() != null && job.getRepeatInterval() != null)
                        simpleBuilder = simpleBuilder.repeatHourlyForTotalCount(job.getRepeatCount(), job
                                .getRepeatInterval());
                    else if (job.getRepeatCount() != null)
                        simpleBuilder = simpleBuilder.repeatHourlyForTotalCount(job.getRepeatCount());
                } else if (job.getRepeatType().equals("repeatMinutelyForTotalCount")) {
                    if (job.getRepeatCount() != null && job.getRepeatInterval() != null)
                        simpleBuilder = simpleBuilder.repeatMinutelyForTotalCount(job.getRepeatCount(), job
                                .getRepeatInterval());
                    else if (job.getRepeatCount() != null)
                        simpleBuilder = simpleBuilder.repeatMinutelyForTotalCount(job.getRepeatCount());
                } else if (job.getRepeatType().equals("repeatSecondlyForTotalCount")) {
                    if (job.getRepeatCount() != null && job.getRepeatInterval() != null)
                        simpleBuilder = simpleBuilder.repeatSecondlyForTotalCount(job.getRepeatCount(), job
                                .getRepeatInterval());
                    else if (job.getRepeatCount() != null)
                        simpleBuilder = simpleBuilder.repeatSecondlyForTotalCount(job.getRepeatCount());
                }
            }
            if(StrUtil.isEmpty(job.getDesc()))
                job.setDesc("");
            int index=job.getDesc().indexOf(";repeatType:")>-1?job.getDesc().indexOf(";repeatType:"):job.getDesc().length();
            trigger = triggerBuilder.withIdentity(triggerKey).withSchedule(simpleBuilder).withDescription(
                    job.getDesc().substring(0,index) + ";repeatType:" + job.getRepeatType()).build();
            /*trigger = triggerBuilder.withIdentity(triggerKey).withSchedule(simpleBuilder).withDescription(
                    job.getDesc()).build();*/
        }
        return trigger;
    }

    /**
     * 建立simpleTrigger repeatForever
     * @param simpleBuilder
     * @param job
     * @return
     */
    public SimpleScheduleBuilder simpleBuilderForever(SimpleScheduleBuilder simpleBuilder,ScheduleJob job){
        if(job.getRepeatInterval()!=null){
            if(job.getRepeatUnit().equals("hour")){
                return simpleBuilder.withIntervalInHours(job.getRepeatInterval());
            }else if(job.getRepeatUnit().equals("minute")){
                return simpleBuilder.withIntervalInMinutes(job.getRepeatInterval());
            }else{
                return simpleBuilder.withIntervalInSeconds(job.getRepeatInterval());
            }
        }else{
            return simpleBuilder.repeatForever();
        }
    }
    /**
     * 获取所有计划中的任务
     */
    public List<ScheduleJob> getAllJobs(QueryCondition condition, PageInfo pageInfo) throws SchedulerException {
        String jobId;
        if(condition!=null);
             jobId = condition.getConditionMap().get("jobId").toString();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setJobId(jobKey.getGroup() + "_" + jobKey.getName());
                if (!StrUtil.isEmpty(jobId)&&job.getJobId().indexOf(jobId) == -1) {
                    continue;
                }
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                /*job.setDesc("job:" + scheduler.getJobDetail(jobKey).getDescription() + ";trigger:"
                        + trigger.getDescription());*/
                job.setDesc(trigger.getDescription());
                job.setJobClass(scheduler.getJobDetail(jobKey).getJobClass().getName());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    job = getCronJob(job, trigger);
                } else if (trigger instanceof SimpleTrigger) {
                    SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
                    job.setTriggerType("SimpleTrigger");
                    job.setStartTime(simpleTrigger.getStartTime());
                    job.setEndTime(simpleTrigger.getEndTime());
                    String repeatType = null;
                    if (simpleTrigger.getDescription() != null && simpleTrigger.getDescription().indexOf("repeatType:") > -1) {
                        repeatType = simpleTrigger.getDescription().substring(
                                simpleTrigger.getDescription().indexOf("repeatType:") + 11);
                    }
                    job.setRepeatType(repeatType);
                    job.setRepeatCount(simpleTrigger.getRepeatCount());
                    job.setRepeatInterval((int) simpleTrigger.getRepeatInterval());
                    if (repeatType != null && repeatType.indexOf("Secondly") > -1)
                        job.setRepeatIntervalStr(((int) simpleTrigger.getRepeatInterval() / 1000) + "秒");
                    else if (repeatType != null && repeatType.indexOf("Minutely") > -1)
                        job.setRepeatIntervalStr((int) simpleTrigger.getRepeatInterval() / 60000 + "分钟");
                    else if (repeatType != null && repeatType.indexOf("Hourly") > -1)
                        job.setRepeatIntervalStr((int) simpleTrigger.getRepeatInterval() / 3600000 + "小时");
                    job.setTriggerName(simpleTrigger.getKey().getName());
                    job.setTriggerGroup(simpleTrigger.getKey().getGroup());
                    int index=job.getDesc().indexOf(";repeatType:")>-1?job.getDesc().indexOf(";repeatType:"):job.getDesc().length();
                    job.setDesc(job.getDesc().substring(0,index));
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 获取单个任务
     */
    public List<ScheduleJob> getScheduleJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = new JobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
        for (Trigger trigger : triggers) {
            ScheduleJob job = new ScheduleJob();
            job.setJobId(jobKey.getGroup() + "_" + jobKey.getName());
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
           /* job.setDesc("job:" + scheduler.getJobDetail(jobKey).getDescription() + " trigger:"
                    + trigger.getDescription());*/
            job.setDesc(trigger.getDescription());
            job.setJobClass(scheduler.getJobDetail(jobKey).getJobClass().getName());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                job = getCronJob(job, trigger);
            } else if (trigger instanceof SimpleTrigger) {
                SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
                job.setTriggerType("SimpleTrigger");
                job.setStartTime(simpleTrigger.getStartTime());
                job.setEndTime(simpleTrigger.getEndTime());
                String repeatType = null;
                if (simpleTrigger.getDescription().indexOf("repeatType:") > -1) {
                    repeatType = simpleTrigger.getDescription().substring(
                            simpleTrigger.getDescription().indexOf("repeatType:") + 11);
                }
                job.setRepeatType(repeatType);
                job.setRepeatCount(simpleTrigger.getRepeatCount());
                if (repeatType != null && repeatType.indexOf("Secondly") > -1)
                    job.setRepeatInterval((int) simpleTrigger.getRepeatInterval() / 1000);
                else if (repeatType != null && repeatType.indexOf("Minutely") > -1)
                    job.setRepeatInterval((int) simpleTrigger.getRepeatInterval() / 60000);
                else if (repeatType != null && repeatType.indexOf("Hourly") > -1)
                    job.setRepeatInterval((int) simpleTrigger.getRepeatInterval() / 3600000);
                job.setTriggerName(simpleTrigger.getKey().getName());
                job.setTriggerGroup(simpleTrigger.getKey().getGroup());
                int index=job.getDesc().indexOf(";repeatType:")>-1?job.getDesc().indexOf(";repeatType:"):job.getDesc().length();
                job.setDesc(job.getDesc().substring(0,index));
            }
            jobList.add(job);
        }
        return jobList;
    }

    public ScheduleJob getCronJob(ScheduleJob job, Trigger trigger) {
        CronTrigger cronTrigger = (CronTrigger) trigger;
        String cronExpression = cronTrigger.getCronExpression();
        job.setCronExpression(cronExpression);
        job.setTriggerType("CronTrigger");
        job.setStartTime(cronTrigger.getStartTime());
        job.setEndTime(cronTrigger.getEndTime());
        job.setTriggerName(cronTrigger.getKey().getName());
        job.setTriggerGroup(cronTrigger.getKey().getGroup());
        return job;
    }

    /**
     * 获取运行中的任务
     */
    public List<ScheduleJob> getRunningJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobId(jobKey.getGroup() + "_" + jobKey.getName());
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDesc(trigger.getDescription());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停
     */
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复运行
     */
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除
     */
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 运行一次
     */
    public void triggerJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新时间表达式
     */
    public void rescheduleJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        // 获取trigger
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        // 按照新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 新增单个任务 Cron
     */
    public void scheduleCronNewJob(ScheduleJob scheduleJob) throws SchedulerException, ClassNotFoundException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        // 获取trigger,即在spring配置文件中定义的bean id="myTrigger"
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(scheduleJob.getJobClass()))
                .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).withDescription(
                        scheduleJob.getDesc()).build();
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        // 按新的cronExpression表达式构建一个新的trigger
        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }


    /**
     * 获取所有正在执行的任务（页面展示）
     */
    public List getRunningJobs(QueryCondition condition, PageInfo pageInfo) throws SchedulerException {
        return this.getRunningJobs();
    }

}
