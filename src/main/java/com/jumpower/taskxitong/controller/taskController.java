package com.jumpower.taskxitong.controller;

import com.jumpower.taskxitong.entity.SysTask;
import com.jumpower.taskxitong.service.SysTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        //List<WmsUser> wmsUsers = wmsUserService.queryWmsUserMapperList(wmsUser);
        return sysTasks;
    }

}
