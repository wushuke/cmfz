package com.baizhi.wsk.controller;

import com.baizhi.wsk.dao.CounterDao;
import com.baizhi.wsk.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/counter")
public class CounterController {
    @Autowired
    CounterDao counterDao;
    @RequestMapping("selects")
    public Map selects (String uid,String id){
        List<Counter> counters = counterDao.selects(uid, id);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        hashMap.put("counters",counters);
        return hashMap;
    }

    @RequestMapping("insert")
    public Map insert(String uid,String title){
        String id = UUID.randomUUID().toString();
        counterDao.insert(uid,id,title);
        List<Counter> counters = counterDao.selectAll();
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        hashMap.put("counters",counters);
        return hashMap;
    }

    @RequestMapping("delete")
    public Map delete(String id,String uid){
        counterDao.delete(uid,id);
        List<Counter> counters = counterDao.selectAll();
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        hashMap.put("counters",counters);
        return hashMap;
    }

    @RequestMapping("update")
    public Map update(String id,String uid,Integer counts){
        counterDao.update(uid,id,counts);
        List<Counter> counters = counterDao.selectAll();
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        hashMap.put("counters",counters);
        return hashMap;
    }
}
