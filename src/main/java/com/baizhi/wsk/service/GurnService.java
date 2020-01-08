package com.baizhi.wsk.service;

import com.baizhi.wsk.entity.Guru;

import java.util.List;

public interface GurnService {
    public List<Guru> showAllGuru();
    public void insert(Guru guru);
    //删除
    public void delete(String[] id);
    //修改
    public void update(Guru guru);
}
