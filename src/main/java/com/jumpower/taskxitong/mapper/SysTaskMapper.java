package com.jumpower.taskxitong.mapper;



import com.jumpower.taskxitong.entity.SysTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysTaskMapper {

    SysTask get(Long id);

    List<SysTask> list();

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

    List<SysTask> listTaskVoByDesc(@Param("desc") String desc);
}
