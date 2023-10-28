package com.ruoyi.mytask.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mytask.mapper.MyTaskMapper;
import com.ruoyi.mytask.domain.MyTask;
import com.ruoyi.mytask.service.IMyTaskService;

/**
 * 开始考试Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-19
 */
@Service
public class MyTaskServiceImpl implements IMyTaskService 
{
    @Autowired
    private MyTaskMapper myTaskMapper;

    /**
     * 查询开始考试
     * 
     * @param questionId 开始考试主键
     * @return 开始考试
     */
    @Override
    public MyTask selectMyTaskByQuestionId(Integer questionId)
    {
        return myTaskMapper.selectMyTaskByQuestionId(questionId);
    }

    /**
     * 查询开始考试列表
     * 
     * @param myTask 开始考试
     * @return 开始考试
     */
    @Override
    public List<MyTask> selectMyTaskList(MyTask myTask)
    {
        return myTaskMapper.selectMyTaskList(myTask);
    }

    /**
     * 新增开始考试
     * 
     * @param myTask 开始考试
     * @return 结果
     */
    @Override
    public int insertMyTask(MyTask myTask)
    {
        return myTaskMapper.insertMyTask(myTask);
    }

    /**
     * 修改开始考试
     * 
     * @param myTask 开始考试
     * @return 结果
     */
    @Override
    public int updateMyTask(MyTask myTask)
    {
        return myTaskMapper.updateMyTask(myTask);
    }

    /**
     * 批量删除开始考试
     * 
     * @param questionIds 需要删除的开始考试主键
     * @return 结果
     */
    @Override
    public int deleteMyTaskByQuestionIds(Integer[] questionIds)
    {
        return myTaskMapper.deleteMyTaskByQuestionIds(questionIds);
    }

    /**
     * 删除开始考试信息
     * 
     * @param questionId 开始考试主键
     * @return 结果
     */
    @Override
    public int deleteMyTaskByQuestionId(Integer questionId)
    {
        return myTaskMapper.deleteMyTaskByQuestionId(questionId);
    }
}
