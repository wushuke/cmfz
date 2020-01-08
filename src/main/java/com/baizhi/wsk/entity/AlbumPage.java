package com.baizhi.wsk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumPage {
    //专辑分页
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Album> rows;
}
