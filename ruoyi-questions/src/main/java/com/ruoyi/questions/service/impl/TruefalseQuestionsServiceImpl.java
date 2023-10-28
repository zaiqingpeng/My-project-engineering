package com.ruoyi.questions.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.questions.mapper.TruefalseQuestionsMapper;
import com.ruoyi.questions.domain.TruefalseQuestions;
import com.ruoyi.questions.service.ITruefalseQuestionsService;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import javax.validation.Validator;

/**
 * 判断题题库Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-19
 */
@Service
public class TruefalseQuestionsServiceImpl implements ITruefalseQuestionsService
{
    @Autowired
    private TruefalseQuestionsMapper truefalseQuestionsMapper;

    /**
     * 查询判断题题库
     *
     * @param id 判断题题库主键
     * @return 判断题题库
     */
    @Override
    public TruefalseQuestions selectTruefalseQuestionsById(Long id)
    {
        return truefalseQuestionsMapper.selectTruefalseQuestionsById(id);
    }

    /**
     * 查询判断题题库列表
     *
     * @param truefalseQuestions 判断题题库
     * @return 判断题题库
     */
    @Override
    public List<TruefalseQuestions> selectTruefalseQuestionsList(TruefalseQuestions truefalseQuestions)
    {
        return truefalseQuestionsMapper.selectTruefalseQuestionsList(truefalseQuestions);
    }

    /**
     * 新增判断题题库
     *
     * @param truefalseQuestions 判断题题库
     * @return 结果
     */
    @Override
    public int insertTruefalseQuestions(TruefalseQuestions truefalseQuestions)
    {
        return truefalseQuestionsMapper.insertTruefalseQuestions(truefalseQuestions);
    }

    /**
     * 修改判断题题库
     *
     * @param truefalseQuestions 判断题题库
     * @return 结果
     */
    @Override
    public int updateTruefalseQuestions(TruefalseQuestions truefalseQuestions)
    {
        return truefalseQuestionsMapper.updateTruefalseQuestions(truefalseQuestions);
    }

    /**
     * 批量删除判断题题库
     *
     * @param ids 需要删除的判断题题库主键
     * @return 结果
     */
    @Override
    public int deleteTruefalseQuestionsByIds(Long[] ids)
    {
        return truefalseQuestionsMapper.deleteTruefalseQuestionsByIds(ids);
    }

    /**
     * 删除判断题题库信息
     *
     * @param id 判断题题库主键
     * @return 结果
     */
    @Override
    public int deleteTruefalseQuestionsById(Long id)
    {
        return truefalseQuestionsMapper.deleteTruefalseQuestionsById(id);
    }

    //////////判断题导入方法//////////
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    protected Validator validator;

    @Override
    public String importUser(List<TruefalseQuestions> tfList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(tfList) || tfList.size() == 0)
        {
            throw new ServiceException("导入判断题数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TruefalseQuestions tf : tfList)
        {
            try
            {
                // 验证是否存在这个用户
                TruefalseQuestions u = truefalseQuestionsMapper.selectTruefalseQuestionsById(tf.getId());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, tf);
                    tf.setCreateBy(operName);
                    this.insertTruefalseQuestions(tf);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、序号 " + tf.getId() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, tf);
                    tf.setUpdateBy(operName);
                    this.updateTruefalseQuestions(tf);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、序号 " + tf.getId() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、序号 " + tf.getId() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、序号 " + tf.getId() + " 导入失败：";
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
