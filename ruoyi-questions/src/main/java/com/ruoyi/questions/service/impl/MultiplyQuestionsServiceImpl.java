package com.ruoyi.questions.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.questions.mapper.MultiplyQuestionsMapper;
import com.ruoyi.questions.domain.MultiplyQuestions;
import com.ruoyi.questions.service.IMultiplyQuestionsService;

import javax.validation.Validator;

/**
 * 多选题题库Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-19
 */
@Service
public class MultiplyQuestionsServiceImpl implements IMultiplyQuestionsService
{
    @Autowired
    private MultiplyQuestionsMapper multiplyQuestionsMapper;

    /**
     * 查询多选题题库
     *
     * @param id 多选题题库主键
     * @return 多选题题库
     */
    @Override
    public MultiplyQuestions selectMultiplyQuestionsById(Integer id)
    {
        return multiplyQuestionsMapper.selectMultiplyQuestionsById(id);
    }

    /**
     * 查询多选题题库列表
     *
     * @param multiplyQuestions 多选题题库
     * @return 多选题题库
     */
    @Override
    public List<MultiplyQuestions> selectMultiplyQuestionsList(MultiplyQuestions multiplyQuestions)
    {
        return multiplyQuestionsMapper.selectMultiplyQuestionsList(multiplyQuestions);
    }

    /**
     * 新增多选题题库
     *
     * @param multiplyQuestions 多选题题库
     * @return 结果
     */
    @Override
    public int insertMultiplyQuestions(MultiplyQuestions multiplyQuestions)
    {
        return multiplyQuestionsMapper.insertMultiplyQuestions(multiplyQuestions);
    }

    /**
     * 修改多选题题库
     *
     * @param multiplyQuestions 多选题题库
     * @return 结果
     */
    @Override
    public int updateMultiplyQuestions(MultiplyQuestions multiplyQuestions)
    {
        return multiplyQuestionsMapper.updateMultiplyQuestions(multiplyQuestions);
    }

    /**
     * 批量删除多选题题库
     *
     * @param ids 需要删除的多选题题库主键
     * @return 结果
     */
    @Override
    public int deleteMultiplyQuestionsByIds(Integer[] ids)
    {
        return multiplyQuestionsMapper.deleteMultiplyQuestionsByIds(ids);
    }

    /**
     * 删除多选题题库信息
     *
     * @param id 多选题题库主键
     * @return 结果
     */
    @Override
    public int deleteMultiplyQuestionsById(Integer id)
    {
        return multiplyQuestionsMapper.deleteMultiplyQuestionsById(id);
    }


    /**
     * 导入多选题题库信息
     * @param
     * @return 结果
     */
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    protected Validator validator;

    @Override
    public String importUser(List<MultiplyQuestions> stuList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(stuList) || stuList.size() == 0)
        {
            throw new ServiceException("导入多选题数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (MultiplyQuestions stu : stuList)
        {
            try
            {
                // 验证是否存在这个用户
                MultiplyQuestions u = multiplyQuestionsMapper.selectMultiplyQuestionsById(stu.getId());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, stu);
                    stu.setCreateBy(operName);
                    this.insertMultiplyQuestions(stu);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、序号" + stu.getId() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, stu);
                    stu.setUpdateBy(operName);
                    this.updateMultiplyQuestions(stu);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、序号 " + stu.getId() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、序号 " + stu.getId() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、序号" + stu.getId() + " 导入失败：";
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
