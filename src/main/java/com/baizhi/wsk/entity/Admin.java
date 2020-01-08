package com.baizhi.wsk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    //管理员表
    @Id
    private String id;
    private String username;
    private String password;
}
