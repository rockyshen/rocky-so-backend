package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


/**
 * @author rockyshen
 * @date 2024/11/25 10:30
 * 定义接入的搜索源的规范
 */
public interface DataSource<T> {
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
