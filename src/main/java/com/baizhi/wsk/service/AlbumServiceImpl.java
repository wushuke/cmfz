package com.baizhi.wsk.service;

import com.baizhi.wsk.dao.AlbumDao;
import com.baizhi.wsk.entity.Album;
import com.baizhi.wsk.entity.AlbumPage;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("albumService")
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;
    @Override
    //添加
    //@Log(name = "专辑添加")
    public void insert(Album album) {
        System.out.println(album);
        albumDao.insert(album);
    }

    @Override
    //删除
    //@Log(name = "专辑删除")
    public void delete(Album album) {
        albumDao.deleteByPrimaryKey(album.getId());
    }



    @Override
    //修改
    //@Log(name = "专辑修改")
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    //分页查询
    public AlbumPage selectPage(Album album, Integer page, Integer count) {
        AlbumPage albumPage = new AlbumPage();
        //存入当前页数
        albumPage.setPage(page);
        //总条数
        int i = albumDao.selectCount(album);
        //计算总页数
        albumPage.setTotal(i%count==0?i/count:i/count+1);
        Integer inx=(page-1)* count;
        albumPage.setRows(albumDao.selectByRowBounds(album,new RowBounds(inx,count)));
        return albumPage;
    }
}
