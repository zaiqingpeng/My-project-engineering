package com.ruoyi.student.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.StudentInfoMapper;
import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.service.IStudentInfoService;

import javax.validation.Validator;


/**
 * 学生基本信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-18
 */
@Service
public class StudentInfoServiceImpl implements IStudentInfoService
{
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    /**
     * 查询学生基本信息
     *
     * @param id 学生基本信息主键
     * @return 学生基本信息
     */
    @Override
    public StudentInfo selectStudentInfoById(Integer id)
    {
        return studentInfoMapper.selectStudentInfoById(id);
    }

    /**
     * 查询学生基本信息列表
     *
     * @param studentInfo 学生基本信息
     * @return 学生基本信息
     */
    @Override
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo)
    {
        return studentInfoMapper.selectStudentInfoList(studentInfo);
    }

    /**
     * 新增学生基本信息
     *
     * @param studentInfo 学生基本信息
     * @return 结果
     */
    @Override
    public int insertStudentInfo(StudentInfo studentInfo)
    {
        return studentInfoMapper.insertStudentInfo(studentInfo);
    }

    /**
     * 修改学生基本信息
     *
     * @param studentInfo 学生基本信息
     * @return 结果
     */
    @Override
    public int updateStudentInfo(StudentInfo studentInfo)
    {
        return studentInfoMapper.updateStudentInfo(studentInfo);
    }

    /**
     * 批量删除学生基本信息
     *
     * @param ids 需要删除的学生基本信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentInfoByIds(Integer[] ids)
    {
        return studentInfoMapper.deleteStudentInfoByIds(ids);
    }

    /**
     * 删除学生基本信息信息
     *
     * @param id 学生基本信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentInfoById(Integer id)
    {
        return studentInfoMapper.deleteStudentInfoById(id);
    }


    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    protected Validator validator;

    @Override
    public String importUser(List<StudentInfo> stuList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(stuList) || stuList.size() == 0)
        {
            throw new ServiceException("导入学生基本信息数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (StudentInfo stu : stuList)
        {
            try
            {
                // 验证是否存在这个用户
                StudentInfo u = studentInfoMapper.selectStudentInfoById(stu.getId());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException((javax.validation.Validator) validator, stu);
                    stu.setCreateBy(operName);
                    this.insertStudentInfo(stu);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、学号 " + stu.getStudentId() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, stu);
                    stu.setUpdateBy(operName);
                    this.updateStudentInfo(stu);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、学号 " + stu.getStudentId() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、学号 " + stu.getStudentId() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、学号 " + stu.getStudentId() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
