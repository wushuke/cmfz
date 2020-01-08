package com.baizhi.wsk.service;

import com.baizhi.wsk.dao.LogDao;
import com.baizhi.wsk.entity.Chapter;
import com.baizhi.wsk.entity.Log;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceImpl implements LogService {

     @Autowired
    LogDao logDao;

    //每次开启事务
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void addLog(Log log) {

     logDao.addLog(log);
    }

    @Override
    public List<Log> queryAll() {
        return logDao.queryAll();
    }

    @Override
    public Map page(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Example e = new Example(Chapter.class);

        int records = logDao.selectByExample(e).size();//条件查条数
        int offset = (page - 1) * rows;
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", logDao.selectByExampleAndRowBounds(e, new RowBounds(offset, rows)));
        return hashMap;
    }
}
