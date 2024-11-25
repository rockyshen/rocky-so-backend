package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author rockyshen
 * @date 2024/11/25 10:35
 */
@Service
public class PictureDataSource implements DataSource<Picture>{
    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
        Page<Picture> picturePage = pictureService.searchPictures(searchText, pageNum, pageSize);
        return picturePage;
    }
}
