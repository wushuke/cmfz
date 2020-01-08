package com.baizhi.wsk.controller;

import com.baizhi.wsk.dao.ArticleDao;
import com.baizhi.wsk.entity.Article;
import com.baizhi.wsk.service.ArticleService;
import com.baizhi.wsk.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleDao articleDao;

    @RequestMapping("showAllArticle")
    public Map showAllArticle(Integer page, Integer rows){
        return articleService.selectPage(page,rows);
    }

    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        // 该方法需要返回的信息 error 状态码 0 成功 1失败   成功时 url 图片路径
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        try{
            String http = HttpUtil.getHttp(imgFile, request, "/upload/articleImg/");
            hashMap.put("error",0);
            hashMap.put("url",http);
        }catch (Exception e){
            hashMap.put("error",1);
            e.printStackTrace();
        }
        return hashMap;
    }
    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }
    @RequestMapping("del")
    public void delete(Article article){
        articleService.delete(article);
    }
    @RequestMapping("insertArticle")
    public String insertArticle(Article article, MultipartFile inputfile, HttpServletRequest request){
        if (article.getId()==null||"".equals(article.getId())){
            String realPath = request.getSession().getServletContext().getRealPath("/upload/articleImg/");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            // insert
            String http = HttpUtil.getHttp(inputfile,request,"/upload/articleImg/");
            article.setId(UUID.randomUUID().toString());
            article.setImg(http);
            article.setCreateDate(new Date());
            article.setPublishDate(new Date());

            articleService.insert(article);
        }else{
            // update
            String realPath = request.getSession().getServletContext().getRealPath("/upload/articleImg/");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String http = HttpUtil.getHttp(inputfile,request,"/upload/articleImg/");
            System.out.println(http);
            article.setImg(http);

            articleService.update(article);
        }
        return "ok";
    }


    //文章接口
    @RequestMapping("article")
    public Map article(String uid,String id){
        HashMap hashMap = new HashMap();
        Article article = articleDao.selectOnee(id);
        hashMap.put("status",200);
        hashMap.put("article",article);
        return hashMap;
    }



}
