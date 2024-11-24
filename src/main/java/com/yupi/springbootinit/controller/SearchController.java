package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 聚合搜索接口
 * 传递一种参数类型，查询到：Post、User、Picture三种数据
 * @author rockyshen
 * @date 2024/11/23 23:05
 */

@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    @Resource
    private PictureService pictureService;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        String searchText = searchRequest.getSearchText();
        // 查用户
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);

        // 查帖子
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);

        // 查图片
        Page<Picture> picturePage = pictureService.searchPictures(searchText, 1, 10);

        // 聚合结果
        SearchVO searchVO = new SearchVO();
        searchVO.setUserList(userVOPage.getRecords());
        searchVO.setPostList(postVOPage.getRecords());
        searchVO.setPictureList(picturePage.getRecords());

        return ResultUtils.success(searchVO);
    }
}
