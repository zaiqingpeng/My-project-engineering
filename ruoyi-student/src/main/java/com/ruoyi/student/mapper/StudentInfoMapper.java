package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.StudentInfo;

/**
 * 学生基本信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-18
 */
public interface StudentInfoMapper
{

    /**
     * 查询学生基本信息
     *
     * @param id 学生基本信息主键
     * @return 学生基本信息
     */
    public StudentInfo selectStudentInfoById(Integer id);

    /**
     * 查询学生基本信息列表
     *
     * @param studentInfo 学生基本信息
     * @return 学生基本信息集合
     */
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo);

    /**
     * 新增学生基本信息
     *
     * @param studentInfo 学生基本信息
     * @return 结果
     */
    public int insertStudentInfo(StudentInfo studentInfo);

    /**
     * 修改学生基本信息
     *
     * @param studentInfo 学生基本信息
     * @return 结果
     */
    public int updateStudentInfo(StudentInfo studentInfo);

    /**
     * 删除学生基本信息
     *
     * @param id 学生基本信息主键
     * @return 结果
     */
    public int deleteStudentInfoById(Integer id);

    /**
     * 批量删除学生基本信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentInfoByIds(Integer[] ids);
}
