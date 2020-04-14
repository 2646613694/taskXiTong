package com.jumpower.taskxitong.quartz.task.account;

import com.ktamr.common.utils.DateUtils;
import com.ktamr.common.utils.PropertiesUtil;
import com.ktamr.common.utils.sql.SqlCondition;
import com.ktamr.domain.HaArea;
import com.ktamr.domain.HaBillconfig;
import com.ktamr.domain.HaFreeze;
import com.ktamr.quartz.task.BaseJob;
import com.ktamr.service.HaAreaService;
import com.ktamr.service.HaBillconfigService;
import com.ktamr.service.HaFreezeService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@DisallowConcurrentExecution //作业不并发
@Component
public class accountJob extends BaseJob implements Job  {

    @Resource
    private HaFreezeService haFreezeService;
    @Resource
    private HaAreaService haAreaService;
    @Resource
    private HaBillconfigService haBillconfigService;


    /**
     *
     * @param jobExecutionContext 调度上下文的各种信息
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String code = PropertiesUtil.readProperty("operatorCode");
        String taskId = jobExecutionContext.getJobDetail().getJobDataMap().get("xka").toString();
        HaBillconfig haBillconfig=new HaBillconfig();
        haBillconfig.setTaskId(Long.valueOf(taskId));
        HaBillconfig billconfig = haBillconfigService.queryHaBillconfigByTaskId(haBillconfig);
        HaFreeze haFreeze=new HaFreeze();
        haFreeze.setNodeType("area");
        haFreeze.setNodeIds(String.valueOf(billconfig.getAreaId()));
        String IDStr = DateUtils.parseDateToStr("yyyyMMddHHmmss", DateUtils.getNowDate())+"-";
        String nowStr = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", DateUtils.getNowDate());
        haFreeze.setIDStr(IDStr);
        haFreeze.setNowStr(nowStr);
        haFreeze.getParams().put("getRgnCondition", SqlCondition.getRgnCondition(haFreeze.getNodeType(),haFreeze.getNodeIds()));
        haFreezeService.insertHaFreeze11(haFreeze);
        haFreezeService.insertHaFreeze12(haFreeze);
        haFreezeService.BinsertHaFreeze2(haFreeze);
        haFreezeService.BinsertHaFreeze3(haFreeze);
        haFreezeService.BinsertHaFreeze41(haFreeze);
        haFreezeService.BinsertHaFreeze42(haFreeze);
        haFreezeService.BinsertHaFreeze43(haFreeze);
        haFreezeService.BinsertHaFreeze51(haFreeze);
        haFreezeService.BinsertHaFreeze52(haFreeze);
        HaArea haArea=new HaArea();
        String timeStr = DateUtils.parseDateToStr("yyyy/MM/dd HH:mm:ss", DateUtils.getNowDate());
        haArea.setTimeStr(timeStr);
        haArea.getParams().put("getRgnCondition",SqlCondition.getRgnCondition(haFreeze.getNodeType(),haFreeze.getNodeIds()));
        //Object attribute =session.getAttribute("operatorCode");
        haAreaService.updateWanChengJieSuanOne(haArea);
        haArea.setOpertorCode(String.valueOf(code));
        haAreaService.updateWanChenJieSuanTwo(haArea);
        haAreaService.updateWanChenJieSuanThree(haArea);
        haAreaService.updateWanChenJieSuanFour(haArea);
        haAreaService.updateWanChenJieSuanFive(haArea);
        haAreaService.updateWanChenJieSuanSix(haArea);
        haAreaService.updateWanChenJieSuanSeven(haArea);

    }
}
