package com.yupi.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.datasource.*;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rockyshen
 * @date 2024/11/25 10:15
 * 聚合搜索的门面模式
 * 将searchAll的方法，提取到这里，不要在controller层写大量逻辑
 */
@Component
public class SearchFacade {
    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        String searchText = searchRequest.getSearchText();
        long pageNum = searchRequest.getPageNum();
        long pageSize = searchRequest.getPageSize();

        // type为空，查全部
        if(searchTypeEnum == null){
            // 查用户
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userDataSource.doSearch(searchText, pageNum, pageSize);
            // 查帖子
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postDataSource.doSearch(searchText, pageNum, pageSize);
            // 查图片
            Page<Picture> picturePage = pictureDataSource.doSearch(searchText, pageNum, pageSize);
            // 聚合结果
            SearchVO searchVO = new SearchVO();
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            return searchVO;
        }else{  // 不为空的话，依据type查对应的
//            DataSource dataSource = typeDataSourceMap.get(type);
            SearchVO searchVO = new SearchVO();
            DataSource dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page page = dataSource.doSearch(searchText, pageNum, pageSize);
            searchVO.setDataList(page.getRecords());
//            switch(searchTypeEnum){
//                case POST:
//                    // 查帖子
//                    PostQueryRequest postQueryRequest = new PostQueryRequest();
//                    postQueryRequest.setSearchText(searchText);
//                    Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
//                    searchVO.setPostList(postVOPage.getRecords());
//                    break;
//                case USER:
//                    // 查用户
//                    UserQueryRequest userQueryRequest = new UserQueryRequest();
//                    userQueryRequest.setUserName(searchText);
//                    Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
//                    searchVO.setUserList(userVOPage.getRecords());
//                    break;
//                case PICTIRE:
//                    // 查图片
//                    Page<Picture> picturePage = pictureService.searchPictures(searchText, 1, 10);
//                    searchVO.setPictureList(picturePage.getRecords());
//                    break;
//                default:
//            }
            return searchVO;
        }
    }
}
