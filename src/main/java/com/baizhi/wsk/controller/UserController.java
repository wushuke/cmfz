package com.baizhi.wsk.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.wsk.dao.UserDao;
import com.baizhi.wsk.entity.Course;
import com.baizhi.wsk.entity.User;
import com.baizhi.wsk.entity.UserListener;
import com.baizhi.wsk.service.UserService;
import com.baizhi.wsk.util.HttpUtil;
import com.baizhi.wsk.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    //导出
    @RequestMapping("imageUpload")
    public void imageUpload(){
        List<User> users = userDao.selectAll();
        String url="E:\\后期项目\\day6-富文本编辑器\\"+new Date().getTime()+".xls";
        EasyExcel.write(url, User.class)
                .sheet("用户")
                .doWrite(users);
    }
    //导入

    @RequestMapping("leadExcel")
    public void leadExcel(){
        String url = "E:\\后期项目\\day6-富文本编辑器\\1578189850425.xls";
        EasyExcel.read(url,User.class,new UserListener()).sheet().doRead();
    }




    //分页查询
    @RequestMapping("userPage")
    public Map userPage(Integer page, Integer rows){
        return userService.selectPage(page,rows);
    }

    @RequestMapping("showUserTime")
    public Map showUserTime(){
        return userService.showUserTime();
    }

    @RequestMapping("showUserMap")
    public Map showUserMap(){
        HashMap hashMap = new HashMap();
        List man = userService.map("1");
        List women = userService.map("0");
        hashMap.put("man",man);
        hashMap.put("women",women);
        return hashMap;
    }
    //增删改
    @RequestMapping("save")
    public Map save(User user, String oper, String[] id){
        //  HashMap hashMap=null;
        if ("add".equals(oper)){

            // hashMap = new HashMap();
            String id1 = UUID.randomUUID().toString();
            user.setId(id1);
            user.setRigestDate(new Date());
            userService.insert(user);

        }else if("edit".equals(oper)){
            userService.update(user);
        }else{
            userService.delete(id);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("userId",user.getId());
        return hashMap;
    }

    //文件上传
    @RequestMapping("/upload")
    public Map uploadBanner(MultipartFile photo, String userId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/user/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(photo, request, "/upload/user/");
        // 文件上传 工具类完成
        // 更新数据库信息
        User user = new User();
        user.setId(userId);
        user.setPhoto(http);
        userService.update(user);
        hashMap.put("status", 200);
        return hashMap;
    }

    @RequestMapping("login")
    //登陆接口
    public Map login(String phone,String password){
        HashMap hashMap = new HashMap();
        User user = userDao.login(phone, password);
        hashMap.put("status","200");
        hashMap.put("user",user);
        return hashMap;
    }
    @RequestMapping("/gk")
    public Map gk(String id){
        HashMap hashMap = new HashMap();
        List<Course> option = userDao.oneSelect(id);
        hashMap.put("status","200");
        hashMap.put("option",option);
        return hashMap;
    }
    @RequestMapping("update")
    public Map update(String uid,String nick_name,String password){
        userDao.update(uid,nick_name,password);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("nick_name",nick_name);
        hashMap.put("password",password);
        return hashMap;
    }

    @RequestMapping("redSelect")
    public Map redSelect(){
        List<User> list = userDao.redSelect();
        HashMap hashMap = new HashMap();
        hashMap.put("list",list);
        return hashMap;

    }
    // 发送验证码
    @RequestMapping("userCode")
    public Map userCode(String phone){
        String id = UUID.randomUUID().toString();
        String code = id.substring(0,3);
        SmsUtil.send(phone,code);
        HashMap hashMap = new HashMap();
        Jedis jedis = new Jedis("192.168.120.129");
        jedis.setex(phone,300,code);
        if (phone==""){
            hashMap.put("-200","struts");
            hashMap.put("message","验证码发送失败");
            return hashMap;
        }
        hashMap.put("200","struts");
        hashMap.put("message","验证码发送成功");
        return hashMap;
    }
    //注册接口
    @RequestMapping("register")
    public Map register(String code, HttpServletRequest request,String phone){
        String id = UUID.randomUUID().toString();

        Jedis jedis = new Jedis("192.168.120.129");
        String code1 = jedis.get(phone);
        HashMap hashMap = new HashMap();
        if(code.equals(code1)){
            userDao.register(id);
            hashMap.put("200","struts");
            hashMap.put("message","注册成功");
        }else {
            hashMap.put("-200","struts");
            hashMap.put("message","注册失败");
        }
        return hashMap;
    }
    // 补充个人信息接口
    @RequestMapping("replenish")
    public Map replenish(String id,String password,String photo,String name,String nick_name,String sex,String sign,String location){
        userDao.replenish(id,password,photo,name,nick_name,sex,sign,location);
        HashMap hashMap = new HashMap();
       try {
           hashMap.put("200", "struts");
           User user = userDao.selectOnePerson(id);
           hashMap.put("user", user);
           return hashMap;
       }catch (Exception e){
           hashMap.put("-200", "struts");
           hashMap.put("补充失败", "message");
           return hashMap;
       }
    }
}
