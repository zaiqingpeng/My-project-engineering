package com.ruoyi.classes.service;

import java.util.List;
import com.ruoyi.classes.domain.ClassesInfo;

/**
 * 班级信息Service接口
 * 
 * @author ruoyi
 * @date 2023-03-18
 */
public interface IClassesInfoService 
{
    /**
     * 查询班级信息
     * 
     * @param id 班级信息主键
     * @return 班级信息
     */
    public ClassesInfo selectClassesInfoById(Long id);

    /**
     * 查询班级信息列表
     * 
     * @param classesInfo 班级信息
     * @return 班级信息集合
     */
    public List<ClassesInfo> selectClassesInfoList(ClassesInfo classesInfo);

    /**
     * 新增班级信息
     * 
     * @param classesInfo 班级信息
     * @return 结果
     */
    public int insertClassesInfo(ClassesInfo classesInfo);

    /**
     * 修改班级信息
     * 
     * @param classesInfo 班级信息
     * @return 结果
     */
    public int updateClassesInfo(ClassesInfo classesInfo);

    /**
     * 批量删除班级信息
     * 
     * @param ids 需要删除的班级信息主键集合
     * @return 结果
     */
    public int deleteClassesInfoByIds(Long[] ids);

    /**
     * 删除班级信息信息
     * 
     * @param id 班级信息主键
     * @return 结果
     */
    public int deleteClassesInfoById(Long id);
}
