package com.jdyx.app.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Page implements Serializable {

    private int pageNow = 0; // 当前页数

    private int pageSize = 10; // 每页显示记录的条数

//    private int totalCount; // 总的记录条数
    public List<VideoDisplayVo> videoDisplayVo;

}
