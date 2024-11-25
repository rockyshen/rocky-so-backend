package com.yupi.springbootinit.datasource;
import java.util.ArrayList;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author rockyshen
 * @date 2024/11/25 10:34
 * 利用适配器模式，适配DataSource接口的参数
 */

@Service
public class PostDataSource implements DataSource<PostVO>{
    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);

        return postVOPage;
    }
}
