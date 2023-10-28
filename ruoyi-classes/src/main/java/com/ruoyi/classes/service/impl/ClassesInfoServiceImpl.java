package com.ruoyi.classes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.classes.mapper.ClassesInfoMapper;
import com.ruoyi.classes.domain.ClassesInfo;
import com.ruoyi.classes.service.IClassesInfoService;

/**
 * 班级信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-18
 */
@Service
public class ClassesInfoServiceImpl implements IClassesInfoService 
{
    @Autowired
    private ClassesInfoMapper classesInfoMapper;

    /**
     * 查询班级信息
     * 
     * @param id 班级信息主键
     * @return 班级信息
     */
    @Override
    public ClassesInfo selectClassesInfoById(Long id)
    {
        return classesInfoMapper.selectClassesInfoById(id);
    }

    /**
     * 查询班级信息列表
     * 
     * @param classesInfo 班级信息
     * @return 班级信息
     */
    @Override
    public List<ClassesInfo> selectClassesInfoList(ClassesInfo classesInfo)
    {
        return classesInfoMapper.selectClassesInfoList(classesInfo);
    }

    /**
     * 新增班级信息
     * 
     * @param classesInfo 班级信息
     * @return 结果
     */
    @Override
    public int insertClassesInfo(ClassesInfo classesInfo)
    {
        return classesInfoMapper.insertClassesInfo(classesInfo);
    }

    /**
     * 修改班级信息
     * 
     * @param classesInfo 班级信息
     * @return 结果
     */
    @Override
    public int updateClassesInfo(ClassesInfo classesInfo)
    {
        return classesInfoMapper.updateClassesInfo(classesInfo);
    }

    /**
     * 批量删除班级信息
     * 
     * @param ids 需要删除的班级信息主键
     * @return 结果
     */
    @Override
    public int deleteClassesInfoByIds(Long[] ids)
    {
        return classesInfoMapper.deleteClassesInfoByIds(ids);
    }

    /**
     * 删除班级信息信息
     * 
     * @param id 班级信息主键
     * @return 结果
     */
    @Override
    public int deleteClassesInfoById(Long id)
    {
        return classesInfoMapper.deleteClassesInfoById(id);
    }
}
