package com.baizhi.wsk.service;

import com.baizhi.wsk.dao.ChapterDao;
import com.baizhi.wsk.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.Map;

@Service("chapterService")
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    //添加
    //@Log(name = "章节添加")
    @Transactional(propagation = Propagation.REQUIRES_NEW) //每次开启事务
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    //删除
   // @Log(name = "章节删除")
    @Transactional(propagation = Propagation.REQUIRES_NEW) //每次开启事务
    public void delete(String id) {
        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapterDao.delete(chapter);
    }

    @Override
    //分页查询
    public Map selectPage(Integer page, Integer rows, String albumId) {
        HashMap hashMap = new HashMap();
        Example e = new Example(Chapter.class);
        e.createCriteria().andEqualTo("albumId", albumId);
        int records = chapterDao.selectByExample(e).size();//条件查条数
        int offset = (page - 1) * rows;
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", chapterDao.selectByExampleAndRowBounds(e, new RowBounds(offset, rows)));
        return hashMap;

    }

    @Override
    //修改
    //@Log(name = "章节修改")
    @Transactional(propagation = Propagation.REQUIRES_NEW) //每次开启事务
    public void update(Chapter chapter) {
        System.out.println(chapter);
        chapterDao.updateByPrimaryKeySelective(chapter);

    }
}
