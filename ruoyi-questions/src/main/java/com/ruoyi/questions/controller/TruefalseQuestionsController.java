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
import com.ruoyi.questions.domain.TruefalseQuestions;
import com.ruoyi.questions.service.ITruefalseQuestionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 判断题题库Controller
 *
 * @author ruoyi
 * @date 2023-03-19
 */
@RestController
@RequestMapping("/questions/tf_questions")
public class TruefalseQuestionsController extends BaseController
{
    @Autowired
    private ITruefalseQuestionsService truefalseQuestionsService;

    /**
     * 查询判断题题库列表
     */
    @PreAuthorize("@ss.hasPermi('questions:tf_questions:list')")
    @GetMapping("/list")
    public TableDataInfo list(TruefalseQuestions truefalseQuestions)
    {
//        startPage();
        List<TruefalseQuestions> list = truefalseQuestionsService.selectTruefalseQuestionsList(truefalseQuestions);
        return getDataTable(list);
    }

    /**
     * 导出判断题题库列表
     */
    @PreAuthorize("@ss.hasPermi('questions:tf_questions:export')")
    @Log(title = "判断题题库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TruefalseQuestions truefalseQuestions)
    {
        List<TruefalseQuestions> list = truefalseQuestionsService.selectTruefalseQuestionsList(truefalseQuestions);
        ExcelUtil<TruefalseQuestions> util = new ExcelUtil<TruefalseQuestions>(TruefalseQuestions.class);
        util.exportExcel(response, list, "判断题题库数据");
    }

    /**
     * 获取判断题题库详细信息
     */
    @PreAuthorize("@ss.hasPermi('questions:tf_questions:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(truefalseQuestionsService.selectTruefalseQuestionsById(id));
    }

    /**
     * 新增判断题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:tf_questions:add')")
    @Log(title = "判断题题库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TruefalseQuestions truefalseQuestions)
    {
        return toAjax(truefalseQuestionsService.insertTruefalseQuestions(truefalseQuestions));
    }

    /**
     * 修改判断题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:tf_questions:edit')")
    @Log(title = "判断题题库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TruefalseQuestions truefalseQuestions)
    {
        return toAjax(truefalseQuestionsService.updateTruefalseQuestions(truefalseQuestions));
    }

    /**
     * 删除判断题题库
     */
    @PreAuthorize("@ss.hasPermi('questions:tf_questions:remove')")
    @Log(title = "判断题题库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(truefalseQuestionsService.deleteTruefalseQuestionsByIds(ids));
    }


    /////判断题导入接口/////
    @Log(title = "判断题信息", businessType = BusinessType.IMPORT) // todo
    @PreAuthorize("@ss.hasPermi('questions:tf_questions:import')") // todo/
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<TruefalseQuestions> util = new ExcelUtil<>(TruefalseQuestions.class); // todo
        List<TruefalseQuestions> stuList = util.importExcel(file.getInputStream()); // todo
        String operName = getUsername();
        String message = truefalseQuestionsService.importUser(stuList, updateSupport, operName); // todo
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<TruefalseQuestions> util = new ExcelUtil<>(TruefalseQuestions.class); // todo
        util.importTemplateExcel(response, "判断题信息");
    }

}
