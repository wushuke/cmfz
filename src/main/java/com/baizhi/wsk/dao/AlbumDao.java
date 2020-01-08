package com.baizhi.wsk.dao;

import com.baizhi.wsk.entity.Album;
import tk.mybatis.mapper.common.Mapper;

public interface AlbumDao extends Mapper<Album> {
    public Album selectOnes(String id);
}
