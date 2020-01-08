package com.baizhi.wsk.dao;

import com.baizhi.wsk.entity.Course;
import com.baizhi.wsk.entity.Guru;
import com.baizhi.wsk.entity.User;
import com.baizhi.wsk.entity.UserMapDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    public Integer queryUserByTime(@Param("sex") String sex,@Param("day") Integer day);
    public List<UserMapDTO> map(@Param("sex") String sex);

    public User login(@Param("phone") String phone,@Param("password") String password);

    public List<Course> oneSelect(String id);

    public void update(@Param("uid") String uid,@Param("nick_name") String nick_name,@Param("password") String password);

    public List<User> redSelect();

    public List<Guru> selectGuru(@Param("uid") String uid);

    //注册接口
    public void register(@Param("id") String id);

    public void replenish(@Param("id")String id, @Param("password")String password, @Param("photo")String photo,
                          @Param("name")String name, @Param("nick_name")String nick_name, @Param("sex")String sex,
                          @Param("sign")String sign, @Param("location")String location);
    //查一个
    public User selectOnePerson(@Param("id")String id);

    //关注上师接口
    public  void userGuru(@Param("uuid")String uuid,@Param("uid")String uid,@Param("id")String id);
    //查询用户所关注的上师
    public List<Guru> selectGuru(@Param("uid")String uid,@Param("id")String id);
}
