package com.baizhi.wsk.controller;

import com.baizhi.wsk.dao.CourseDao;
import com.baizhi.wsk.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/course")
@RestController
public class CourseController {

    @Autowired
    CourseDao courseDao;
    @RequestMapping("/insert")
    public Map insert(String title,String uid){
        String id = UUID.randomUUID().toString();
        courseDao.insert(id,uid,title);
        HashMap hashMap = new HashMap();
        List<Course> courses = courseDao.selectAll();
        hashMap.put("course",courses);
        hashMap.put("status",200);
        return hashMap;
    }
    //删除功课
    @RequestMapping("delete")
    public Map delete(String id,String uid){
        courseDao.delete(id,uid);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        List<Course> option = courseDao.selectAll();
        hashMap.put("option",option);
        return hashMap;
    }
}
