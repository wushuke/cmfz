package com.baizhi.wsk.service;

import com.baizhi.wsk.dao.GuruDao;
import com.baizhi.wsk.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("gurnService")
public class GurnServiceImpl implements GurnService {
    @Autowired
    GuruDao guruDao;
    @Override
    public List<Guru> showAllGuru() {
        return guruDao.selectAll();
    }

    @Override
    public void insert(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    public void delete(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public void update(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }
}
