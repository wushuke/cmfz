package com.baizhi.wsk.controller;

import com.baizhi.wsk.dao.UserDao;
import com.baizhi.wsk.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/userGuru")
public class UserGuruController {

    @Autowired
    UserDao userDao;


    @RequestMapping("insertGuru")
    public Map insertGuru(String uid,String id){
        String uuid = UUID.randomUUID().toString();
        userDao.userGuru(uuid,uid,id);
        List<Guru> list = userDao.selectGuru(uid, id);
        HashMap hashMap = new HashMap();
        hashMap.put("list",list);
        hashMap.put("200","status");
        hashMap.put("上师","message");
        return hashMap;
    }
}
