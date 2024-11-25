package com.yupi.springbootinit.model.dto.search;

import lombok.Data;

import java.io.Serializable;

/**
 * 聚合搜索的统一参数
 * @author rockyshen
 * @date 2024/11/23 23:10
 */

@Data
public class SearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // 不同的搜索类型，使用枚举类限制SearchTypeEnum
    private String type;

    // 搜索关键字
    private String searchText;

    private long pageNum;

    private long pageSize;
}
