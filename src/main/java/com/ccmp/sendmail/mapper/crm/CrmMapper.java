package com.ccmp.sendmail.mapper.crm;

import com.ccmp.sendmail.bean.InfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrmMapper {
    List<InfoDto> getInfoReport();
}
