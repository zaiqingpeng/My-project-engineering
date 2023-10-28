package com.ruoyi.mytask.mapper;

import java.util.List;
import com.ruoyi.mytask.domain.MyTask;

/**
 * 开始考试Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-19
 */
public interface MyTaskMapper 
{
    /**
     * 查询开始考试
     * 
     * @param questionId 开始考试主键
     * @return 开始考试
     */
    public MyTask selectMyTaskByQuestionId(Integer questionId);

    /**
     * 查询开始考试列表
     * 
     * @param myTask 开始考试
     * @return 开始考试集合
     */
    public List<MyTask> selectMyTaskList(MyTask myTask);

    /**
     * 新增开始考试
     * 
     * @param myTask 开始考试
     * @return 结果
     */
    public int insertMyTask(MyTask myTask);

    /**
     * 修改开始考试
     * 
     * @param myTask 开始考试
     * @return 结果
     */
    public int updateMyTask(MyTask myTask);

    /**
     * 删除开始考试
     * 
     * @param questionId 开始考试主键
     * @return 结果
     */
    public int deleteMyTaskByQuestionId(Integer questionId);

    /**
     * 批量删除开始考试
     * 
     * @param questionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyTaskByQuestionIds(Integer[] questionIds);
}
