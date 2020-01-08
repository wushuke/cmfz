package com.baizhi.wsk.service;


import com.baizhi.wsk.entity.Banner;
import com.baizhi.wsk.entity.BannerPage;

import java.util.List;

public interface BannerService {
    //查所有
    public List<Banner> selectAll();
    //分页查询
    public BannerPage selectPage(Banner banner,Integer page,Integer count);
    //删除
    public void delete(String id);
    //批量删除
    public void deletes(String[]id);
    //添加
    public void insert(Banner banner);
    //查一个
    public Banner selestOne(String id);
    //修改
    public void update(Banner banner);
}
