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
import com.ruoyi.questions.domain.SingleQuestions;
import com.ruoyi.questions.service.ISingleQuestionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 单选题题库Controller
 *
 * @author ruoyi
 * @date 2023-03-24
 */
@RestController
@RequestMapping("/questions/single_questions")
public class SingleQuestionsController extends BaseController
{
    @Autowired
    private ISingleQuestionsService singleQuestionsService;

    /**
     * 查询单选题题库列表
     */
    @PreAuthorize("@ss.hasPermi('questions:single_questions:list')")
    @GetMapping("/list")
    public TableDataInfo list(SingleQuestions singleQuestions)
    {
//        startPage();
        List<SingleQuestions> list = singleQuestionsService.selectSingleQuestionsList(singleQuestions);
        return getDataTable(list);
    }

    /**
     * 导出单选题题库列表
     */
    @PreAuthorize("@ss.hasPermi('questions:single_questions:export')")
    @Log(title = "单选题题库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SingleQuestions singleQuestions)
    {
        List<SingleQuestions> list = singleQuestionsService.selectSingleQuestionsList(singleQuestions);
        ExcelUtil<SingleQuestions> util = new ExcelUtil<SingleQuestions>(SingleQuestions.class);
        util.exportExcel(response, list, "单选题题库数据");
    }

    /**
     * 获取单选题题库详细信息
     */
    @PreAuthorize("@ss.hasPermi('questions:single_questions:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(singleQuestionsService.selectSingleQuestionsById(id));
    }

    /**
     * 新增单选题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:single_questions:add')")
    @Log(title = "单选题题库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SingleQuestions singleQuestions)
    {
        return toAjax(singleQuestionsService.insertSingleQuestions(singleQuestions));
    }

    /**
     * 修改单选题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:single_questions:edit')")
    @Log(title = "单选题题库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SingleQuestions singleQuestions)
    {
        return toAjax(singleQuestionsService.updateSingleQuestions(singleQuestions));
    }

    /**
     * 删除单选题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:single_questions:remove')")
    @Log(title = "单选题题库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(singleQuestionsService.deleteSingleQuestionsByIds(ids));
    }


    /**
     * 导入excel中的数据
     */
    @Log(title = "单选题数据", businessType = BusinessType.IMPORT) // todo
    @PreAuthorize("@ss.hasPermi('questions:single_questions:import')") // todo
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SingleQuestions> util = new ExcelUtil<>(SingleQuestions.class); // todo
        List<SingleQuestions> stuList = util.importExcel(file.getInputStream()); // todo
        String operName = getUsername();
        String message = singleQuestionsService.importUser(stuList, updateSupport, operName); // todo
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SingleQuestions> util = new ExcelUtil<>(SingleQuestions.class); // todo
        util.importTemplateExcel(response, "单选题数据");
    }

}
