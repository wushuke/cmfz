package com.baizhi.wsk.dao;

import com.baizhi.wsk.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface BannerDao extends Mapper<Banner> , DeleteByIdListMapper<Banner,String> {
}
