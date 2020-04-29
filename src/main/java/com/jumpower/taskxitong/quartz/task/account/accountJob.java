package com.jumpower.taskxitong.quartz.task.account;

import com.jumpower.taskxitong.quartz.task.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@DisallowConcurrentExecution //作业不并发
@Component
public class accountJob extends BaseJob implements Job  {




    /**
     *
     * @param jobExecutionContext 调度上下文的各种信息
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("你好犀利哥，我进入你的定时器任务仔了");

    }
}
