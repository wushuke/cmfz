package com.baizhi.wsk.service;

import com.baizhi.wsk.entity.Album;
import com.baizhi.wsk.entity.AlbumPage;

public interface AlbumService {
    //添加
    public void insert(Album album);
    //删除
    public void delete(Album album);
    //分页查询
    public AlbumPage selectPage(Album album, Integer page, Integer count);
    //修改
    public void update(Album album);
}
