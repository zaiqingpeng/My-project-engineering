package com.ruoyi.mytask.mapper;

import java.util.List;
import com.ruoyi.mytask.domain.MyExercise;

/**
 * 开始练习Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-05
 */
public interface MyExerciseMapper 
{
    /**
     * 查询开始练习
     * 
     * @param id 开始练习主键
     * @return 开始练习
     */
    public MyExercise selectMyExerciseById(Long id);

    /**
     * 查询开始练习列表
     * 
     * @param myExercise 开始练习
     * @return 开始练习集合
     */
    public List<MyExercise> selectMyExerciseList(MyExercise myExercise);

    /**
     * 新增开始练习
     * 
     * @param myExercise 开始练习
     * @return 结果
     */
    public int insertMyExercise(MyExercise myExercise);

    /**
     * 修改开始练习
     * 
     * @param myExercise 开始练习
     * @return 结果
     */
    public int updateMyExercise(MyExercise myExercise);

    /**
     * 删除开始练习
     * 
     * @param id 开始练习主键
     * @return 结果
     */
    public int deleteMyExerciseById(Long id);

    /**
     * 批量删除开始练习
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyExerciseByIds(Long[] ids);
}
