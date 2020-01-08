package com.baizhi.wsk.dao;

import com.baizhi.wsk.entity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article> {
    public Article selectOnee(String id);

    public List<Article> selects(String id);
}
