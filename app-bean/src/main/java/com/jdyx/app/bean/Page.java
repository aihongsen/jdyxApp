package com.jdyx.app.bean;


import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Page<T> implements Serializable {

    private int pageNow = 0; // 当前页数

    private int pageSize = 10; // 每页显示记录的条数

    private int totalRow; // 共多少行

    private int totalPage; // 总的记录页数

    public List<T> videoDisplayVo;

    @Setter
    public void setTotalPage(int totalRow) {
        this.totalPage=totalRow/pageSize;
        if (totalRow % pageSize > 0){
            this.totalPage += 1;
        }
    }
    public int getTotalPage() {
        return this.totalPage;
    }

    public static void main(String[] args) {
        System.out.println(2/10);
    }
}
