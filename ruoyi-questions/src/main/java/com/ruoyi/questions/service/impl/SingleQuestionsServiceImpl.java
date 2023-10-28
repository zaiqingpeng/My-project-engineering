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
import com.ruoyi.questions.mapper.SingleQuestionsMapper;
import com.ruoyi.questions.domain.SingleQuestions;
import com.ruoyi.questions.service.ISingleQuestionsService;

import javax.validation.Validator;

/**
 * 单选题题库Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-24
 */
@Service
public class SingleQuestionsServiceImpl implements ISingleQuestionsService
{
    @Autowired
    private SingleQuestionsMapper singleQuestionsMapper;

    /**
     * 查询单选题题库
     *
     * @param id 单选题题库主键
     * @return 单选题题库
     */
    @Override
    public SingleQuestions selectSingleQuestionsById(Integer id)
    {
        return singleQuestionsMapper.selectSingleQuestionsById(id);
    }

    /**
     * 查询单选题题库列表
     *
     * @param singleQuestions 单选题题库
     * @return 单选题题库
     */
    @Override
    public List<SingleQuestions> selectSingleQuestionsList(SingleQuestions singleQuestions)
    {
        return singleQuestionsMapper.selectSingleQuestionsList(singleQuestions);
    }

    /**
     * 新增单选题题库
     *
     * @param singleQuestions 单选题题库
     * @return 结果
     */
    @Override
    public int insertSingleQuestions(SingleQuestions singleQuestions)
    {
        return singleQuestionsMapper.insertSingleQuestions(singleQuestions);
    }

    /**
     * 修改单选题题库
     *
     * @param singleQuestions 单选题题库
     * @return 结果
     */
    @Override
    public int updateSingleQuestions(SingleQuestions singleQuestions)
    {
        return singleQuestionsMapper.updateSingleQuestions(singleQuestions);
    }

    /**
     * 批量删除单选题题库
     *
     * @param ids 需要删除的单选题题库主键
     * @return 结果
     */
    @Override
    public int deleteSingleQuestionsByIds(Integer[] ids)
    {
        return singleQuestionsMapper.deleteSingleQuestionsByIds(ids);
    }

    /**
     * 删除单选题题库信息
     *
     * @param id 单选题题库主键
     * @return 结果
     */
    @Override
    public int deleteSingleQuestionsById(Integer id)
    {
        return singleQuestionsMapper.deleteSingleQuestionsById(id);
    }

    /**
     * 导入单选题数据
     *
     */
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    protected Validator validator;

    @Override
    public String importUser(List<SingleQuestions> singleList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(singleList) || singleList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SingleQuestions single : singleList)
        {
            try
            {
                // 验证是否存在这个用户
                SingleQuestions u = singleQuestionsMapper.selectSingleQuestionsById(single.getId());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, single);
                    single.setCreateBy(operName);
                    this.insertSingleQuestions(single);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、序号 " + single.getId() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, single);
                    single.setUpdateBy(operName);
                    this.updateSingleQuestions(single);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、序号 " + single.getId() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、序号 " + single.getId() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、序号 " + single.getId() + " 导入失败：";
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
