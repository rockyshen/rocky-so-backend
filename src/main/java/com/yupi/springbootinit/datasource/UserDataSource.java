package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author rockyshen
 * @date 2024/11/25 10:34
 */

@Service
public class UserDataSource implements DataSource<UserVO>{
    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);

        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);

        return userVOPage;
    }
}
