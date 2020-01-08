package com.baizhi.wsk.service;

import com.baizhi.wsk.entity.Log;

import java.util.List;
import java.util.Map;

public interface LogService {
    public void addLog(Log log);

    //查所有日志
    public List<Log> queryAll();
    //分页查询
    public Map page(Integer page, Integer rows);
}
