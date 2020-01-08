package com.baizhi.wsk.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chapter {
//章节表
  @Id
  private String id;
  private String title;
  private String url;
  private Double size;
  private String time;
  @JSONField(format= "YYYY-MM-dd")
  @DateTimeFormat(pattern = "YYYY-MM-dd")
  private Date createTime;
  private String albumId;



}
