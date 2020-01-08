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
public class Course {
//功课表
  @Id
  private String id;
  private String title;
  private String user_id;
  private String type;
  @JSONField(format= "YYYY-MM-dd")
  @DateTimeFormat(pattern = "YYYY-MM-dd")
  private Date createDate;



}
