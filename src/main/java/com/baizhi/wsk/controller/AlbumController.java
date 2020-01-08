package com.baizhi.wsk.controller;

import com.baizhi.wsk.dao.AlbumDao;
import com.baizhi.wsk.dao.ArticleDao;
import com.baizhi.wsk.entity.Album;
import com.baizhi.wsk.entity.AlbumPage;
import com.baizhi.wsk.entity.Article;
import com.baizhi.wsk.service.AlbumService;
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
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleDao articleDao;

    //分页查询
    @RequestMapping("page")
    @ResponseBody
    public AlbumPage selectPage(Integer page,Integer rows){
        AlbumPage albumPage = albumService.selectPage(new Album(), page, rows);
        return albumPage;
    }

    //增删改
    @RequestMapping("/save")
    public Map save(Album album, String oper){
         HashMap hashMap=new HashMap();
        if ("add".equals(oper)){
            // hashMap = new HashMap();
            String id1 = UUID.randomUUID().toString();
            album.setId(id1);
            albumService.insert(album);
        }else if("edit".equals(oper)){
            albumService.update(album);
        }else{
            albumService.delete(album);
        }
        hashMap.put("bannerId",album.getId());
        return hashMap;
    }

    //文件上传
    @RequestMapping("/upload")
    public Map uploadBanner(MultipartFile status, String bannerId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/album/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String http = HttpUtil.getHttp(status, request, "/upload/album/");
        // 文件上传 工具类完成
        // 更新数据库信息
        Album album = new Album();
        album.setId(bannerId);
        album.setStatus(http);
        albumService.update(album);
        hashMap.put("status", 200);
        return hashMap;
    }

    //专辑详情接口
    @RequestMapping("/zj")
    public Map zj(String uid,String id){
        HashMap hashMap = new HashMap();
        Album album = albumDao.selectOnes(id);
        hashMap.put("status",200);
        hashMap.put("album",album);
        List<Article> list = articleDao.selects(id);
        hashMap.put("list",list);
        return hashMap;
    }
}
