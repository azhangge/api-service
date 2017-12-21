package com.huajie.appbase;

import lombok.Data;
import java.util.*;

/**
 * Created by 10070 on 2017/7/19.
 */
@Data
public class PageResult<T> {
    private int total;
    private Collection<T> items;

    public PageResult() {
        this.total = 0;
        this.items = new ArrayList<T>();
    }

    public PageResult(int total) {
        this.total = total;
    }

    public PageResult(int total, Collection items) {
        this.total = total;
        this.items = items;
    }
}
