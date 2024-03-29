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
public class Counter {

  @Id
  private String id;
  private String title;
  private Integer counts;
  @JSONField(format= "YYYY-MM-dd")
  @DateTimeFormat(pattern = "YYYY-MM-dd")
  private Date createDate;
  private String user_id;
  private String course_id;



}
