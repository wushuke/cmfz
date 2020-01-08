package com.baizhi.wsk.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Album{
//专辑表
  @Id
  private String id;
  private String title;
  private String score;
  private String author;
  private String broadcast;
  private Integer ccount;
  private String descript;
  private String status;
  @JSONField(format= "YYYY-MM-dd")
  @DateTimeFormat(pattern = "YYYY-MM-dd")
  private Date createDate;



}
