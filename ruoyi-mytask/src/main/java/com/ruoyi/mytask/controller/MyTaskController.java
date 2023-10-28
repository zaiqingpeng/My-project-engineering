package com.ruoyi.mytask.controller;

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
import com.ruoyi.mytask.domain.MyTask;
import com.ruoyi.mytask.service.IMyTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 开始考试Controller
 *
 * @author ruoyi
 * @date 2023-04-19
 */
@RestController
@RequestMapping("/mytask/examing")
public class MyTaskController extends BaseController
{
    @Autowired
    private IMyTaskService myTaskService;

    /**
     * 查询开始考试列表
     */
    @PreAuthorize("@ss.hasPermi('mytask:examing:list')")
    @GetMapping("/list")
    public TableDataInfo list(MyTask myTask)
    {
//        startPage();
        List<MyTask> list = myTaskService.selectMyTaskList(myTask);
        return getDataTable(list);
    }

    /**
     * 导出开始考试列表
     */
    @PreAuthorize("@ss.hasPermi('mytask:examing:export')")
    @Log(title = "开始考试", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MyTask myTask)
    {
        List<MyTask> list = myTaskService.selectMyTaskList(myTask);
        ExcelUtil<MyTask> util = new ExcelUtil<MyTask>(MyTask.class);
        util.exportExcel(response, list, "开始考试数据");
    }

    /**
     * 获取开始考试详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytask:examing:query')")
    @GetMapping(value = "/{questionId}")
    public AjaxResult getInfo(@PathVariable("questionId") Integer questionId)
    {
        return success(myTaskService.selectMyTaskByQuestionId(questionId));
    }

    /**
     * 新增开始考试
     */
    @PreAuthorize("@ss.hasPermi('mytask:examing:add')")
    @Log(title = "开始考试", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyTask myTask)
    {
        return toAjax(myTaskService.insertMyTask(myTask));
    }

    /**
     * 修改开始考试
     */
    @PreAuthorize("@ss.hasPermi('mytask:examing:edit')")
    @Log(title = "开始考试", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyTask myTask)
    {
        return toAjax(myTaskService.updateMyTask(myTask));
    }

    /**
     * 删除开始考试
     */
    @PreAuthorize("@ss.hasPermi('mytask:examing:remove')")
    @Log(title = "开始考试", businessType = BusinessType.DELETE)
	@DeleteMapping("/{questionIds}")
    public AjaxResult remove(@PathVariable Integer[] questionIds)
    {
        return toAjax(myTaskService.deleteMyTaskByQuestionIds(questionIds));
    }
}
