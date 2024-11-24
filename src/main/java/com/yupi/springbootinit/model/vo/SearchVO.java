package com.yupi.springbootinit.model.vo;

import com.yupi.springbootinit.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 统一返回多种数据类型的集合
 * @author rockyshen
 * @date 2024/11/23 23:07
 */
@Data
public class SearchVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<PostVO> postList;

    private List<UserVO> userList;

    private List<Picture> pictureList;
}
