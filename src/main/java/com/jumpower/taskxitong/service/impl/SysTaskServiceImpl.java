package com.jumpower.taskxitong.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumpower.taskxitong.common.DataGridResult;
import com.jumpower.taskxitong.entity.SysTask;
import com.jumpower.taskxitong.enums.JobStatusEnum;
import com.jumpower.taskxitong.mapper.SysTaskMapper;
import com.jumpower.taskxitong.quartz.utils.QuartzManager;
import com.jumpower.taskxitong.query.TaskQuery;
import com.jumpower.taskxitong.service.SysTaskService;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysTaskServiceImpl implements SysTaskService {

    @Resource
    private SysTaskMapper taskMapper;
    @Resource
    private QuartzManager quartzManager;


    @Override
    public SysTask get(Long id) {
        return taskMapper.get(id);
    }

    @Override
    public List<SysTask> list(SysTask task) {
      return taskMapper.list();
    }

    @Override
    public int update(SysTask task) {
        return   taskMapper.update(task);
    }

    @Override
    public int remove(Long id) {
        try {
            SysTask task = get(id);
            quartzManager.deleteJob(task);
            return taskMapper.remove(id);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int removeBatch(Long[] ids) {
        for (Long id : ids) {
            try {
                SysTask task = get(id);
                quartzManager.deleteJob(task);
            } catch (SchedulerException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return taskMapper.removeBatch(ids);
    }

    @Override
    public void initSchedule() throws SchedulerException {
        // 这里获取任务信息数据
        List<SysTask> jobList = taskMapper.list();
        for (SysTask task : jobList) {
            if (JobStatusEnum.RUNNING.getCode().equals(task.getJobStatus())) {
                quartzManager.addJob(task);
            }
        }
    }

    @Override
    public int save(SysTask task) {
        task.setJobStatus(JobStatusEnum.STOP.getCode());
        task.setCreateUser("管理员");
        task.setCreateTime(new Date());
        task.setUpdateUser("管理员");
        task.setUpdateTime(new Date());
        return taskMapper.save(task);
    }

    /**
     * 添加任务并返回主键ID
     * @param task
     * @return
     */
    @Override
    public Long saveReturnId(SysTask task) {
        task.setJobStatus(JobStatusEnum.STOP.getCode());
        task.setCreateUser("管理员");
        task.setCreateTime(new Date());
        task.setUpdateUser("管理员");
        task.setUpdateTime(new Date());
        return taskMapper.saveReturnId(task);
    }

    @Override
    public void changeStatus(Long jobId, String jobStatus) throws SchedulerException {
        SysTask task = get(jobId);
        if (task == null) {
            return;
        }
        if (JobStatusEnum.STOP.getCode().equals(jobStatus)) {
            quartzManager.deleteJob(task);
            task.setJobStatus(JobStatusEnum.STOP.getCode());
        } else {
            task.setJobStatus(JobStatusEnum.RUNNING.getCode());
            quartzManager.addJob(task);
        }
        update(task);
    }

    @Override
    public void run(SysTask task) throws SchedulerException {
        quartzManager.runJobNow(task);
    }
}
