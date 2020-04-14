package com.jumpower.taskxitong.service;



import com.jumpower.taskxitong.common.DataGridResult;
import com.jumpower.taskxitong.entity.SysTask;
import com.jumpower.taskxitong.query.TaskQuery;
import org.quartz.SchedulerException;

import java.util.List;


public interface SysTaskService {

    SysTask get(Long id);

    List<SysTask> list(SysTask task);

    void initSchedule() throws SchedulerException;

    int save(SysTask task);

    /**
     * 添加任务并返回主键ID
     * @param task
     * @return
     */
    Long saveReturnId(SysTask task);

    int update(SysTask task);

    int remove(Long id);

    int removeBatch(Long[] ids);

    void changeStatus(Long jobId, String jobStatus) throws SchedulerException;

    void run(SysTask scheduleJob) throws SchedulerException;

}
