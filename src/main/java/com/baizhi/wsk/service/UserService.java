package com.baizhi.wsk.service;

import com.baizhi.wsk.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页查询
    public Map selectPage(Integer page, Integer rows);
    //添加
    public void insert(User user);
    //删除
    public void delete(String[] id);
    //修改
    public void update(User user);
    public Map showUserTime();
    public List map(String sex);
}
