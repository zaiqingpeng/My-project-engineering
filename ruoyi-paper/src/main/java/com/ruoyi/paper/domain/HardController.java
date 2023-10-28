package com.ruoyi.paper.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出题难度控制对象 hard_controller
 *
 * @author ruoyi
 * @date 2023-04-04
 */
public class HardController extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 难度水平*/
    @Excel(name = "难度水平")
    private String hardName;
    /** 单选题数 */
    @Excel(name = "单选题数")
    private String singleCount;

    /** 多选题数 */
    @Excel(name = "多选题数")
    private String multiplyCount;

    /** 判断题数 */
    @Excel(name = "判断题数")
    private String tfCount;

    /** 单简单 */
    @Excel(name = "单简单")
    private String sSimple;

    /** 单中等 */
    @Excel(name = "单中等")
    private String sAverage;

    /** 单较难 */
    @Excel(name = "单较难")
    private String sHarder;

    /** 多简单 */
    @Excel(name = "多简单")
    private String  mSimple;

    /** 多中等 */
    @Excel(name = "多中等")
    private String mAverage;

    /** 多较难 */
    @Excel(name = "多较难")
    private String mHarder;

    /** 判简单 */
    @Excel(name = "判简单")
    private String tSimple;

    /** 判中等 */
    @Excel(name = "判中等")
    private String tAverage;

    /** 判较难 */
    @Excel(name = "判较难")
    private String tHarder;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setsingleCount(String singleCount)
    {
        this.singleCount = singleCount;
    }

    public String gethardName()
    {
        return hardName;
    }

    public void sethardName(String hardName)
    {
        this.hardName = hardName;
    }
    public String getsingleCount()
    {
        return singleCount;
    }
    public void setMultiplyCount(String multiplyCount)
    {
        this.multiplyCount = multiplyCount;
    }

    public String getMultiplyCount()
    {
        return multiplyCount;
    }
    public void setTfCount(String tfCount)
    {
        this.tfCount = tfCount;
    }

    public String getTfCount()
    {
        return tfCount;
    }
    public void setsSimple(String sSimple)
    {
        this.sSimple = sSimple;
    }

    public String getsSimple()
    {
        return sSimple;
    }
    public void setsAverage(String sAverage)
    {
        this.sAverage = sAverage;
    }

    public String getsAverage()
    {
        return sAverage;
    }
    public void setsHarder(String sHarder)
    {
        this.sHarder = sHarder;
    }

    public String getsHarder()
    {
        return sHarder;
    }
    public void setmSimple(String  mSimple)
    {
        this.mSimple =  mSimple;
    }

    public String getmSimple()
    {
        return mSimple;
    }
    public void setmAverage(String mAverage)
    {
        this.mAverage = mAverage;
    }

    public String getmAverage()
    {
        return mAverage;
    }
    public void setmHarder(String mHarder)
    {
        this.mHarder = mHarder;
    }

    public String getmHarder()
    {
        return mHarder;
    }
    public void settSimple(String tSimple)
    {
        this.tSimple = tSimple;
    }

    public String gettSimple()
    {
        return tSimple;
    }
    public void settAverage(String tAverage)
    {
        this.tAverage = tAverage;
    }

    public String gettAverage()
    {
        return tAverage;
    }
    public void settHarder(String tHarder)
    {
        this.tHarder = tHarder;
    }

    public String gettHarder()
    {
        return tHarder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("singleCount", getsingleCount())
            .append("multiplyCount", getMultiplyCount())
            .append("tfCount", getTfCount())
            .append("sSimple", getsSimple())
            .append("sAverage", getsAverage())
            .append("sHarder", getsHarder())
            .append("mSimple", getmSimple())
            .append("mAverage", getmAverage())
            .append("mHarder", getmHarder())
            .append("tSimple", gettSimple())
            .append("tAverage", gettAverage())
            .append("tHarder", gettHarder())
            .toString();
    }
}
