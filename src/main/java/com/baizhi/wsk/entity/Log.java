package com.baizhi.wsk.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Log {
    @Id
    private String id;
    private String thing;
    private String name;
    @JSONField(format= "YYYY-MM-dd")
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private Date date;
    private Boolean flag;
}
