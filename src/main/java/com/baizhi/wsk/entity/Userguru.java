package com.baizhi.wsk.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userguru {

  @Id
  private String id;
  private String guruId;
  private String userId;

}
