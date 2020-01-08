import com.baizhi.wsk.dao.BannerDao;
import com.baizhi.wsk.dao.UserDao;
import com.baizhi.wsk.entity.Banner;
import com.baizhi.wsk.entity.UserMapDTO;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest

@RunWith(SpringRunner.class)
public class TestPoi {
    @Autowired
    UserDao userDao;
    @Autowired
    BannerDao bannerDao;
    @Test
    public void test1(){
        List<Banner> banners = bannerDao.selectAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        String[] str = {"ID","标题","图片","超链接","创建时间","描述","状态"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }
        // 通过workbook对象获取样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 通过workbook对象获取数据格式化处理对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        // 指定格式化样式 如 yyyy-MM-dd
        short format = dataFormat.getFormat("yyyy-MM-dd");
        // 为样式对象 设置格式化处理
        cellStyle.setDataFormat(format);
        for (int i = 0; i < banners.size(); i++) {
            Banner banner = banners.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getUrl());
            row1.createCell(3).setCellValue(banner.getHref());
            HSSFCell cell = row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(banner.getCreate_date());
            row1.createCell(5).setCellValue(banner.getDescript());
            row1.createCell(6).setCellValue(banner.getStatus());
        }
        try {
            workbook.write(new File("E:\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*//创建excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作簿
        HSSFSheet sheet = workbook.createSheet();
        //创建行
        HSSFRow row = sheet.createRow(0);
        //创建列
        Cell cell = row.createCell(0);
        String []str={"id","标题","图片路径","超链接","日期","描述","状态"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }
        //通过workboot对象，获取样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //通过workboot对象，获取数据格式化对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        //指定格式化样式
        short format = dataFormat.getFormat("yyyy-MM-dd");
        //为样式对象设置格式化处理
        cellStyle.setDataFormat(format);
        List<Banner> banners = bannerDao.selectAll();
        for (int i = 0; i < banners.size(); i++) {
            Banner banner = banners.get(i);
            HSSFRow row1 = sheet.getRow(i + 1);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getUrl());
            row1.createCell(3).setCellValue(banner.getHref());

            HSSFCell cell1 = row1.createCell(4);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(banner.getCreate_date());
            row1.createCell(5).setCellValue(banner.getDescript());
            row1.createCell(6).setCellValue(banner.getStatus());
        }
        try {
            workbook.write(new File("E:\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Test
    public void name() {
        List<UserMapDTO> map = userDao.map("1");
        for (UserMapDTO userMapDTO : map) {
            System.out.println(userMapDTO);
        }
    }
}
