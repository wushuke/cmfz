package com.baizhi.wsk.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner implements Serializable {
    //轮播图
    @Id
    private String id;
    private String title;
    private String url;
    private String href;
    @JSONField(format= "YYYY-MM-dd")
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private Date create_date;
    private String descript;
    private String status;
}
