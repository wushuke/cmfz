package com.baizhi.wsk.dao;

import com.baizhi.wsk.entity.Guru;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface GuruDao extends Mapper<Guru>, DeleteByIdListMapper<Guru,String> {
}
