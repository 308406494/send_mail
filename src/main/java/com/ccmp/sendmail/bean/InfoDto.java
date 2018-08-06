package com.ccmp.sendmail.bean;

import com.ccmp.sendmail.annotation.Title;
import lombok.Data;

@Data
public class InfoDto {
    @Title("省份")
    private String province;
    @Title("集团名称")
    private String custName;
    @Title("集团编码")
    private String custCode;
    @Title("可测试")
    private Integer testNum;
    @Title("已激活")
    private Integer activeNum;
    @Title("其它")
    private Integer otherNum;
    @Title("当日新增")
    private Integer todayNew;
    @Title("当日已销户")
    private Integer todayDis;
    @Title("合计用户数")
    private Integer count;
}
