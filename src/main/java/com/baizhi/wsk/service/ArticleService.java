package com.baizhi.wsk.service;

import com.baizhi.wsk.entity.Article;

import java.util.Map;

public interface ArticleService {
    //分页查询
    public Map selectPage(Integer page, Integer rows);
    //添加
    public void insert(Article article);
    //删除
    public void delete(Article article);
    //修改
    public void update(Article article);
}
