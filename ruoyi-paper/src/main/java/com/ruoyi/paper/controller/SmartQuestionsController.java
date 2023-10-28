package com.ruoyi.paper.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.paper.domain.SmartQuestions;
import com.ruoyi.paper.service.ISmartQuestionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 智能出卷Controller
 * 
 * @author ruoyi
 * @date 2023-04-03
 */
@RestController
@RequestMapping("/paper/smart_info")
public class SmartQuestionsController extends BaseController
{
    @Autowired
    private ISmartQuestionsService smartQuestionsService;

    /**
     * 查询智能出卷列表
     */
    @PreAuthorize("@ss.hasPermi('paper:smart_info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SmartQuestions smartQuestions)
    {
//        startPage();
        List<SmartQuestions> list = smartQuestionsService.selectSmartQuestionsList(smartQuestions);
        return getDataTable(list);
    }

    /**
     * 导出智能出卷列表
     */
    @PreAuthorize("@ss.hasPermi('paper:smart_info:export')")
    @Log(title = "智能出卷", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SmartQuestions smartQuestions)
    {
        List<SmartQuestions> list = smartQuestionsService.selectSmartQuestionsList(smartQuestions);
        ExcelUtil<SmartQuestions> util = new ExcelUtil<SmartQuestions>(SmartQuestions.class);
        util.exportExcel(response, list, "智能出卷数据");
    }

    /**
     * 获取智能出卷详细信息
     */
    @PreAuthorize("@ss.hasPermi('paper:smart_info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(smartQuestionsService.selectSmartQuestionsById(id));
    }

    /**
     * 新增智能出卷
     */
    @PreAuthorize("@ss.hasPermi('paper:smart_info:add')")
    @Log(title = "智能出卷", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SmartQuestions smartQuestions)
    {
        return toAjax(smartQuestionsService.insertSmartQuestions(smartQuestions));
    }

    /**
     * 修改智能出卷
     */
    @PreAuthorize("@ss.hasPermi('paper:smart_info:edit')")
    @Log(title = "智能出卷", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SmartQuestions smartQuestions)
    {
        return toAjax(smartQuestionsService.updateSmartQuestions(smartQuestions));
    }

    /**
     * 删除智能出卷
     */
    @PreAuthorize("@ss.hasPermi('paper:smart_info:remove')")
    @Log(title = "智能出卷", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(smartQuestionsService.deleteSmartQuestionsByIds(ids));
    }
}
