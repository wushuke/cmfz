package com.baizhi.wsk.dao;

import com.baizhi.wsk.entity.Counter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterDao {
    // 展示计数器
    public List<Counter> selects(@Param("uid") String uid,@Param("id") String id);
    //展示所有计数器
    public List<Counter> selectAll();
    //添加计数器
    public void insert(@Param("uid") String uid,@Param("id") String id,@Param("title")String title);
    //删除计数器
    public void delete(@Param("uid") String uid,@Param("id") String id);
    //修改计数器
    public  void update(@Param("uid") String uid,@Param("id") String id,@Param("counts")Integer counts);
}
