package com.baizhi.wsk.service;

import com.baizhi.wsk.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //添加
    public void insert(Chapter chapter);
    //删除
    public void delete(String id);
    //分页查询
    public Map selectPage(Integer page, Integer count, String albumId);
    //修改
    public void update(Chapter chapter);
}
