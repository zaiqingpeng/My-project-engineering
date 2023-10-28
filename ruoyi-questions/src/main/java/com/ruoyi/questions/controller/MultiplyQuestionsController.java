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
import com.ruoyi.questions.domain.MultiplyQuestions;
import com.ruoyi.questions.service.IMultiplyQuestionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 多选题题库Controller
 *
 * @author ruoyi
 * @date 2023-03-19
 */
@RestController
@RequestMapping("/questions/multiply_questions")
public class MultiplyQuestionsController extends BaseController
{
    @Autowired
    private IMultiplyQuestionsService multiplyQuestionsService;

    /**
     * 查询多选题题库列表
     */
    @PreAuthorize("@ss.hasPermi('questions:multiply_questions:list')")
    @GetMapping("/list")
    public TableDataInfo list(MultiplyQuestions multiplyQuestions)
    {
//        startPage();
        List<MultiplyQuestions> list = multiplyQuestionsService.selectMultiplyQuestionsList(multiplyQuestions);
        return getDataTable(list);
    }

    /**
     * 导出多选题题库列表
     */
    @PreAuthorize("@ss.hasPermi('questions:multiply_questions:export')")
    @Log(title = "多选题题库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MultiplyQuestions multiplyQuestions)
    {
        List<MultiplyQuestions> list = multiplyQuestionsService.selectMultiplyQuestionsList(multiplyQuestions);
        ExcelUtil<MultiplyQuestions> util = new ExcelUtil<MultiplyQuestions>(MultiplyQuestions.class);
        util.exportExcel(response, list, "多选题题库数据");
    }

    /**
     * 获取多选题题库详细信息
     */
    @PreAuthorize("@ss.hasPermi('questions:multiply_questions:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(multiplyQuestionsService.selectMultiplyQuestionsById(id));
    }

    /**
     * 新增多选题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:multiply_questions:add')")
    @Log(title = "多选题题库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MultiplyQuestions multiplyQuestions)
    {
        return toAjax(multiplyQuestionsService.insertMultiplyQuestions(multiplyQuestions));
    }

    /**
     * 修改多选题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:multiply_questions:edit')")
    @Log(title = "多选题题库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MultiplyQuestions multiplyQuestions)
    {
        return toAjax(multiplyQuestionsService.updateMultiplyQuestions(multiplyQuestions));
    }

    /**
     * 删除多选题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:multiply_questions:remove')")
    @Log(title = "多选题题库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(multiplyQuestionsService.deleteMultiplyQuestionsByIds(ids));
    }

     //导入数据方法介绍
    @Log(title = "多选题数据", businessType = BusinessType.IMPORT) // todo
    @PreAuthorize("@ss.hasPermi('questions:multiply_questions:import')") // todo
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<MultiplyQuestions> util = new ExcelUtil<>(MultiplyQuestions.class); // todo
        List<MultiplyQuestions> stuList = util.importExcel(file.getInputStream()); // todo
        String operName = getUsername();
        String message = multiplyQuestionsService.importUser(stuList, updateSupport, operName); // todo
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<MultiplyQuestions> util = new ExcelUtil<>(MultiplyQuestions.class); // todo
        util.importTemplateExcel(response, "多选题数据");
    }

}
