package com.cnpc.framework.base.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.base.pojo.Result;
import com.cnpc.framework.base.pojo.ScheduleJob;
import com.cnpc.framework.base.service.JobService;
import com.cnpc.framework.utils.DateUtil;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by billJiang on 2017/2/25.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * 基于quartz的任务管理器
 */
@Controller
@RequestMapping(value = "/job")
public class JobController {

    private static Logger logger= LoggerFactory.getLogger(JobController.class);
    @Resource
    public JobService jobService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(String id, HttpServletRequest request){
        request.setAttribute("selectId",id);
        return "tool/job/job_list";
    }

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(String jobName,String jobGroup,Model model){
        model.addAttribute("jobName", jobName);
        model.addAttribute("jobGroup", jobGroup);
        return "tool/job/job_edit";
    }

    @RequestMapping("/pause")
    @ResponseBody
    public Result pause(ScheduleJob scheduleJob) throws SchedulerException{
        jobService.pauseJob(scheduleJob);
        return new Result();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(ScheduleJob scheduleJob) throws SchedulerException{
        jobService.deleteJob(scheduleJob);
        return new Result();
    }

    @RequestMapping("/resume")
    @ResponseBody
    public Result resume(ScheduleJob scheduleJob) throws SchedulerException{
        jobService.resumeJob(scheduleJob);
        return new Result();
    }

    @RequestMapping("/trigger")
    @ResponseBody
    public Result trigger(ScheduleJob scheduleJob) throws SchedulerException{
        jobService.triggerJob(scheduleJob);
        return new Result();
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result save(String job) throws SchedulerException,ClassNotFoundException{
        ScheduleJob scheduleJob= JSON.parseObject(job, ScheduleJob.class);
        System.out.println(DateUtil.format(scheduleJob.getStartTime(),DateUtil.formatStr_yyyyMMddHHmmss));
        jobService.scheduleSingleJob(scheduleJob);
        return new Result();
    }

    @RequestMapping("/getJob")
    @ResponseBody
    public ScheduleJob getJob(ScheduleJob scheduleJob) throws SchedulerException{
        List<ScheduleJob> jobList=jobService.getScheduleJob(scheduleJob);
        System.out.println(scheduleJob.getJobName()+"_"+scheduleJob.getJobGroup()+"  size:"+jobList.size());
        return jobList.get(0);
    }
    @RequestMapping("/getRunningJoblist")
    @ResponseBody
    public List<ScheduleJob> getRunningJoblist() throws SchedulerException {
        return jobService.getRunningJobs();
    }

    /**
     * 批量执行Cron任务
     */
    @RequestMapping("/scheduleJobs")
    public void scheduleJobs() throws SchedulerException {
        jobService.scheduleCronJobs();

    }

    @RequestMapping(value="/checkJobClass",method = RequestMethod.POST)
    @ResponseBody
    public Map checkJobClass(String jobClass){
        Map map=new HashMap<>();
        try {
            Class<?> objClass = Class.forName(jobClass);
            if(objClass!=null)
            map.put("valid", true);
            return map;
        } catch (Exception ex) {
            logger.error(ex.getMessage().toString());
            map.put("valid", false);
            return map;
        }
    }
}
