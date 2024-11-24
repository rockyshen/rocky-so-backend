package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.picture.PictureQueryRequest;
import com.yupi.springbootinit.model.dto.post.PostAddRequest;
import com.yupi.springbootinit.model.dto.post.PostEditRequest;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.post.PostUpdateRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 搜索图片接口
 *
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;

    // 搜索图片
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPostVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
            HttpServletRequest request) {

        String searchText = pictureQueryRequest.getSearchText();
        long current = pictureQueryRequest.getCurrent();
        long pageSize = pictureQueryRequest.getPageSize();
        Page<Picture> picturePage = pictureService.searchPictures(searchText, current, pageSize);

        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(picturePage);
    }



}
