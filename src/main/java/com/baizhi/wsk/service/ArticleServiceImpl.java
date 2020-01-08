package com.baizhi.wsk.service;

import com.baizhi.wsk.dao.ArticleDao;
import com.baizhi.wsk.entity.Article;
import com.baizhi.wsk.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.Map;
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Override
    public Map selectPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Example e = new Example(Chapter.class);

        int records = articleDao.selectByExample(e).size();//条件查条数
        int offset = (page - 1) * rows;
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", articleDao.selectByExampleAndRowBounds(e, new RowBounds(offset, rows)));
        return hashMap;
    }

    @Override
    //添加
    //@Log(name = "文章添加")
    public void insert(Article article) {
        articleDao.insert(article);
    }

    @Override
    //@Log(name = "文章删除")
    public void delete(Article article) {
        articleDao.deleteByPrimaryKey(article.getId());
    }

    @Override
    //@Log(name = "文章修改")
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }
}
