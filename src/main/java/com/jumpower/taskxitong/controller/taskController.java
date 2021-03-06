package com.jumpower.taskxitong.controller;

import com.jumpower.taskxitong.common.YYBlogResult;
import com.jumpower.taskxitong.entity.SysTask;
import com.jumpower.taskxitong.enums.JobStatusEnum;
import com.jumpower.taskxitong.service.SysTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class taskController {

    @Resource
    private SysTaskService taskService;

    /**
     *  系统默认路径
     * @return
     */
    @RequestMapping("/")
    public String openTask(){
        return "/task";
    }

    /**
     * 打开任务查询页面
     * @return
     */
    @RequestMapping("/task")
    public String taskOpen(){
        return "/task";
    }

    /**
     *  任务表信息列表信息
     * @param task
     * @return
     */
    @PostMapping("/taskListJson")
    @ResponseBody
    public Object taskListJson(SysTask task){
        List<SysTask> sysTasks = taskService.list(task);
        return sysTasks;
    }

    /**
     * 修改任务状态
     * @param id
     * @param jobStatus
     * @return
     */
    @PostMapping("/changeStatus")
    @ResponseBody
    public String changeStatus(@RequestParam(value = "taskId",required = false) Long id,
                                     @RequestParam(value = "state",required = false) Boolean jobStatus) {
        String status = jobStatus == true ? JobStatusEnum.RUNNING.getCode() : JobStatusEnum.STOP.getCode();
        try {
            taskService.changeStatus(id, status);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("错误信息为:" + e.getMessage());
        }
      return "操作失败";
    }

    /**
     * 根据ID删除任务
     * @param id
     * @return
     */
    @PostMapping("/removeByUniqueId")
    @ResponseBody
    public Object removeByUniqueId(Long id){
        int remove = taskService.remove(id);
        return remove;
    }

    @PostMapping("/addJob")
    @ResponseBody
    public Object addJob(SysTask sysTask){
        sysTask.setJobStatus("0");
        int save = taskService.save(sysTask);
        return save;
    }

    @PostMapping("/updateJob")
    @ResponseBody
    public Object updateJob(SysTask sysTask){
        int update = taskService.update(sysTask);
        return update;
    }
}
