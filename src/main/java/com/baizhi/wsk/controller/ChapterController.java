package com.baizhi.wsk.controller;

import com.baizhi.wsk.dao.AlbumDao;
import com.baizhi.wsk.entity.Album;
import com.baizhi.wsk.entity.Chapter;
import com.baizhi.wsk.service.ChapterService;
import com.baizhi.wsk.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    AlbumDao albumDao;

    @Autowired
    ChapterService chapterService;

    //分页查询
    @ResponseBody
    @RequestMapping("/page")
    public Map page(Integer page, Integer rows,String albumId){
         return  chapterService.selectPage(page, rows,albumId);
    }
    //增删改
    @RequestMapping("/save")
    public Map save(Chapter chapter, String oper,String id,String albumId){
        Album album = new Album();
        HashMap hashMap = new HashMap();
        if (oper.equals("del")) {
            chapterService.delete(id);
            //更改章节数量
            album.setId(chapter.getAlbumId());

            Album album1 = albumDao.selectOne(album);
            album1.setCcount(album1.getCcount()-1);
            albumDao.updateByPrimaryKeySelective(album1);
        } else if (oper.equals("add")) {
            //更改章节数量
            album.setId(chapter.getAlbumId());
            Album album1 = albumDao.selectOne(album);
            System.out.println("++===========之前============="+album1);
            System.out.println(album1.getCcount());
            album1.setCcount(album1.getCcount()+1);
            albumDao.updateByPrimaryKeySelective(album1);
            System.out.println("+++++++++之后++++++++++++"+album1);
            System.out.println(album1.getCcount());

            String chapterId = UUID.randomUUID().toString().replace("-", "");
            chapter.setId(chapterId);
            chapter.setAlbumId(albumId);
            chapter.setCreateTime(new Date());
            chapterService.insert(chapter);
            hashMap.put("chapterId", chapterId);
        } else if (oper.equals("edit")) {
            chapter.setCreateTime(new Date());
            chapterService.update(chapter);
            hashMap.put("chapterId", chapter.getId());
        }
            return hashMap;
    }

    //文件上传
    @RequestMapping("/upload")
    public Map uploadBanner(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/chapter/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String http = HttpUtil.getHttp(url, request, "/upload/chapter/");
        // 文件上传 工具类完成
        // 更新数据库信息
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        // 计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);

        chapter.setSize(size);

        // 计算音频时长
        // 使用三方计算音频时间工具类 得出音频时长
        String[] split = http.split("/");
        // 获取文件名
        String name = split[split.length-1];
        chapter.setTitle(name);
        // 通过文件获取AudioFile对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);

        chapterService.update(chapter);
        hashMap.put("status", 200);
        return hashMap;
    }
    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        String name = split[split.length-1];
        File file = new File(realPath, name);
        // 调用该方法时必须使用 location.href 不能使用ajax ajax不支持下载
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename="+name);

        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
        // FileUtils.copyFile("服务器文件",outputStream)
        //FileUtils.copyFile();
    }


}
