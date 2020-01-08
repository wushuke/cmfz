package com.baizhi.wsk.controller;

import com.baizhi.wsk.entity.Banner;
import com.baizhi.wsk.entity.BannerPage;
import com.baizhi.wsk.service.BannerService;
import com.baizhi.wsk.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping("/banner")  //轮播图模块
public class BannerController {
    @Autowired
    BannerService bannerService;

    // 查所有
    @RequestMapping("/selectAll")
    @ResponseBody
    public List selectAll(){
        List<Banner> list = bannerService.selectAll();
        return list;
    }
    //分页查询
    @RequestMapping("page")
    @ResponseBody
    public BannerPage page(Integer page,Integer rows){
        BannerPage bannerPage = bannerService.selectPage(new Banner(), page, rows);
       // List<Banner> rows1 = bannerPage.getRows();
        return bannerPage;
    }
    //增删改
    @RequestMapping("/save")
    public Map save(Banner banner, String oper,String [] id){
      //  HashMap hashMap=null;
        if ("add".equals(oper)){
           // hashMap = new HashMap();
            String id1 = UUID.randomUUID().toString();
            banner.setId(id1);
            bannerService.insert(banner);
        }else if("edit".equals(oper)){
            bannerService.update(banner);
        }else{
            bannerService.deletes(id);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("bannerId",banner.getId());
        return hashMap;
    }

    //文件上传
    @RequestMapping("/upload")
    public Map uploadBanner(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/upload/img/");
        // 文件上传 工具类完成
        // 更新数据库信息
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(http);
        bannerService.update(banner);
        hashMap.put("status", 200);
        return hashMap;
    }
}
