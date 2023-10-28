package com.ruoyi.questions.controller;

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
import com.ruoyi.questions.domain.FreeQuestions;
import com.ruoyi.questions.service.IFreeQuestionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 简答题题库Controller
 * 
 * @author ruoyi
 * @date 2023-05-15
 */
@RestController
@RequestMapping("/questions/free_questions")
public class FreeQuestionsController extends BaseController
{
    @Autowired
    private IFreeQuestionsService freeQuestionsService;

    /**
     * 查询简答题题库列表
     */
    @PreAuthorize("@ss.hasPermi('questions:free_questions:list')")
    @GetMapping("/list")
    public TableDataInfo list(FreeQuestions freeQuestions)
    {
//        startPage();
        List<FreeQuestions> list = freeQuestionsService.selectFreeQuestionsList(freeQuestions);
        return getDataTable(list);
    }

    /**
     * 导出简答题题库列表
     */
    @PreAuthorize("@ss.hasPermi('questions:free_questions:export')")
    @Log(title = "简答题题库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FreeQuestions freeQuestions)
    {
        List<FreeQuestions> list = freeQuestionsService.selectFreeQuestionsList(freeQuestions);
        ExcelUtil<FreeQuestions> util = new ExcelUtil<FreeQuestions>(FreeQuestions.class);
        util.exportExcel(response, list, "简答题题库数据");
    }

    /**
     * 获取简答题题库详细信息
     */
    @PreAuthorize("@ss.hasPermi('questions:free_questions:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(freeQuestionsService.selectFreeQuestionsById(id));
    }

    /**
     * 新增简答题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:free_questions:add')")
    @Log(title = "简答题题库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FreeQuestions freeQuestions)
    {
        return toAjax(freeQuestionsService.insertFreeQuestions(freeQuestions));
    }

    /**
     * 修改简答题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:free_questions:edit')")
    @Log(title = "简答题题库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FreeQuestions freeQuestions)
    {
        return toAjax(freeQuestionsService.updateFreeQuestions(freeQuestions));
    }

    /**
     * 删除简答题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:free_questions:remove')")
    @Log(title = "简答题题库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(freeQuestionsService.deleteFreeQuestionsByIds(ids));
    }
}
