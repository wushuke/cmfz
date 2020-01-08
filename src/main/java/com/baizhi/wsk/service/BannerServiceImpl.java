package com.baizhi.wsk.service;

import com.baizhi.wsk.dao.BannerDao;
import com.baizhi.wsk.entity.Banner;
import com.baizhi.wsk.entity.BannerPage;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;


    @Override
    //查所有
    public List<Banner> selectAll() {
        return bannerDao.selectAll();
    }

    @Override
    //删除
    //@Log(name = "轮播图删除")
    @Transactional(propagation = Propagation.REQUIRES_NEW) //每次开启事务
    public void delete(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    @Override
    //添加
    //@Log(name = "轮播图添加")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    //查一个
    public Banner selestOne(String id) {
        return bannerDao.selectByPrimaryKey(id);
    }

    @Override
    //修改
    //@Log(name = "轮播图修改")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    //分页查询
    @Override
    public BannerPage selectPage(Banner banner,Integer page,Integer count) {
        BannerPage bannerPage = new BannerPage();
        //存入当前页数
        bannerPage.setPage(page);
        //总条数
        int i = bannerDao.selectCount(banner);
        //计算总页数
        bannerPage.setTotal(i%count==0?i/count:i/count+1);

        Integer inx=(page-1)* count;
        bannerPage.setRows(bannerDao.selectByRowBounds(banner,new RowBounds(inx,count)));
        return bannerPage;
    }

    @Override
    public void deletes(String[] id) {
        bannerDao.deleteByIdList(Arrays.asList(id));
    }
}
