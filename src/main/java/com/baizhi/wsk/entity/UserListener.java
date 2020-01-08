package com.baizhi.wsk.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.wsk.dao.UserDao;
import com.baizhi.wsk.util.ApplicationContentUtils;

import java.util.ArrayList;

public class UserListener extends AnalysisEventListener<User> {


    ApplicationContentUtils ac= new ApplicationContentUtils();
   ArrayList<User> list= new ArrayList<User>();
    @Override
    public void invoke(User user, AnalysisContext analysisContext){
        UserDao userDao = (UserDao) ac.getBean(UserDao.class);
        userDao.insertSelective(user);
        list.add(user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        for (User user : list) {
            System.out.println(user+"-------------");
        }
    }
}
