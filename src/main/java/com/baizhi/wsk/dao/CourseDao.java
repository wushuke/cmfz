package com.baizhi.wsk.dao;

import com.baizhi.wsk.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseDao {

    //添加功课
    public void insert(@Param("id")String id,@Param("uid")String uid,@Param("title")String title);
    //删除功课
    public void delete(@Param("id")String id,@Param("uid")String uid);
    //查所有功课
    public List<Course> selectAll();

}
