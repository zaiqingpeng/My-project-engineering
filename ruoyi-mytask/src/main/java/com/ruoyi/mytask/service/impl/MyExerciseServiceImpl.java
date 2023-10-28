package com.ruoyi.mytask.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mytask.mapper.MyExerciseMapper;
import com.ruoyi.mytask.domain.MyExercise;
import com.ruoyi.mytask.service.IMyExerciseService;

/**
 * 开始练习Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-05
 */
@Service
public class MyExerciseServiceImpl implements IMyExerciseService 
{
    @Autowired
    private MyExerciseMapper myExerciseMapper;

    /**
     * 查询开始练习
     * 
     * @param id 开始练习主键
     * @return 开始练习
     */
    @Override
    public MyExercise selectMyExerciseById(Long id)
    {
        return myExerciseMapper.selectMyExerciseById(id);
    }

    /**
     * 查询开始练习列表
     * 
     * @param myExercise 开始练习
     * @return 开始练习
     */
    @Override
    public List<MyExercise> selectMyExerciseList(MyExercise myExercise)
    {
        return myExerciseMapper.selectMyExerciseList(myExercise);
    }

    /**
     * 新增开始练习
     * 
     * @param myExercise 开始练习
     * @return 结果
     */
    @Override
    public int insertMyExercise(MyExercise myExercise)
    {
        return myExerciseMapper.insertMyExercise(myExercise);
    }

    /**
     * 修改开始练习
     * 
     * @param myExercise 开始练习
     * @return 结果
     */
    @Override
    public int updateMyExercise(MyExercise myExercise)
    {
        return myExerciseMapper.updateMyExercise(myExercise);
    }

    /**
     * 批量删除开始练习
     * 
     * @param ids 需要删除的开始练习主键
     * @return 结果
     */
    @Override
    public int deleteMyExerciseByIds(Long[] ids)
    {
        return myExerciseMapper.deleteMyExerciseByIds(ids);
    }

    /**
     * 删除开始练习信息
     * 
     * @param id 开始练习主键
     * @return 结果
     */
    @Override
    public int deleteMyExerciseById(Long id)
    {
        return myExerciseMapper.deleteMyExerciseById(id);
    }
}
