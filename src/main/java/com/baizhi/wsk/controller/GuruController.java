package com.baizhi.wsk.controller;

import com.baizhi.wsk.dao.GuruDao;
import com.baizhi.wsk.entity.Guru;
import com.baizhi.wsk.service.GurnService;
import com.baizhi.wsk.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruDao guruDao;
    @Autowired
    GurnService gurnService;
    @RequestMapping("allGuru")
    public List<Guru> allGuru(){
        List<Guru> gurus = gurnService.showAllGuru();
        System.out.println("上师"+gurus);
        return gurus;
    }


    //增删改
    @RequestMapping("/save")
    public Map save(Guru guru, String oper,String[] id){
        //  HashMap hashMap=null;
        if ("add".equals(oper)){

            // hashMap = new HashMap();
            String id1 = UUID.randomUUID().toString();
            guru.setId(id1);
            gurnService.insert(guru);
        }else if("edit".equals(oper)){
            gurnService.update(guru);
        }else{
            gurnService.delete(id);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("guruId",guru.getId());
        return hashMap;
    }

    //文件上传
    @RequestMapping("/upload")
    public Map uploadBanner(MultipartFile photo, String guruId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/guru/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(photo, request, "/upload/guru/");
        // 文件上传 工具类完成
        // 更新数据库信息
        Guru guru = new Guru();
        guru.setId(guruId);
        guru.setPhoto(http);
        gurnService.update(guru);
        hashMap.put("status", 200);
        return hashMap;
    }

    @RequestMapping("queryAll")
    public Map queryAll(){
        HashMap hashMap = new HashMap();
        List<Guru> list = guruDao.selectAll();
        hashMap.put("list",list);
        hashMap.put("status", 200);
        hashMap.put("message","展示所有上师");
        return hashMap;


    }




}
