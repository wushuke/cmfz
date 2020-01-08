package com.baizhi.wsk.service;

import com.baizhi.wsk.dao.AdminDao;
import com.baizhi.wsk.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    public Admin login(Admin admin) {
       return adminDao.selectOne(admin);
    }
}
