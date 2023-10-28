package com.ruoyi.questions.service;

import java.util.List;
import com.ruoyi.questions.domain.MultiplyQuestions;

/**
 * 多选题题库Service接口
 *
 * @author ruoyi
 * @date 2023-03-19
 */
public interface IMultiplyQuestionsService
{
    /**
     * 查询多选题题库
     *
     * @param id 多选题题库主键
     * @return 多选题题库
     */
    public MultiplyQuestions selectMultiplyQuestionsById(Integer id);

    /**
     * 查询多选题题库列表
     *
     * @param multiplyQuestions 多选题题库
     * @return 多选题题库集合
     */
    public List<MultiplyQuestions> selectMultiplyQuestionsList(MultiplyQuestions multiplyQuestions);

    /**
     * 新增多选题题库
     *
     * @param multiplyQuestions 多选题题库
     * @return 结果
     */
    public int insertMultiplyQuestions(MultiplyQuestions multiplyQuestions);

    /**
     * 修改多选题题库
     *
     * @param multiplyQuestions 多选题题库
     * @return 结果
     */
    public int updateMultiplyQuestions(MultiplyQuestions multiplyQuestions);

    /**
     * 批量删除多选题题库
     *
     * @param ids 需要删除的多选题题库主键集合
     * @return 结果
     */
    public int deleteMultiplyQuestionsByIds(Integer[] ids);

    /**
     * 删除多选题题库信息
     *
     * @param id 多选题题库主键
     * @return 结果
     */
    public int deleteMultiplyQuestionsById(Integer id);

    /**
     * 导入用户数据
     *
     * @param stuList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<MultiplyQuestions> stuList, Boolean isUpdateSupport, String operName);

}
