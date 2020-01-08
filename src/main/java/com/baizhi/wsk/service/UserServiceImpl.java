package com.baizhi.wsk.service;

import com.baizhi.wsk.dao.UserDao;
import com.baizhi.wsk.entity.Chapter;
import com.baizhi.wsk.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public Map selectPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Example e = new Example(Chapter.class);

        int records = userDao.selectByExample(e).size();//条件查条数
        int offset = (page - 1) * rows;
        hashMap.put("page", page);
        hashMap.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        hashMap.put("records", records);
        hashMap.put("rows", userDao.selectByExampleAndRowBounds(e, new RowBounds(offset, rows)));
        return hashMap;
    }

    @Override
    //添加
    //@Log(name = "用户添加")
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    //@Log(name = "用户删除")
    public void delete(String[] id) {
        userDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    //@Log(name = "用户修改")
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public Map showUserTime() {

        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("0",1));
        manList.add(userDao.queryUserByTime("0",7));
        manList.add(userDao.queryUserByTime("0",30));
        manList.add(userDao.queryUserByTime("0",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("1",1));
        womenList.add(userDao.queryUserByTime("1",7));
        womenList.add(userDao.queryUserByTime("1",30));
        womenList.add(userDao.queryUserByTime("1",365));
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;

    }

    @Override
    public List map(String sex) {


        return userDao.map(sex);

    }
}
