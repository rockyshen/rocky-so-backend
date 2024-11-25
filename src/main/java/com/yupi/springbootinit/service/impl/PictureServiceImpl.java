package com.yupi.springbootinit.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author rockyshen
 * @date 2024/11/23 15:50
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<Picture> searchPictures(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1)* pageSize;
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%s",searchText,current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"获取数据异常");
        }
//        System.out.println(doc);
        Elements elements = doc.select(".iuscp.isv.smallheight");
//        System.out.println(elements);
        List<Picture> pictureList = new ArrayList<>();
        for(Element element : elements){
            String m = element.select(".iusc").get(0).attr("m");
            Map<String,Object> map = JSONUtil.toBean(m,Map.class);
            String murl = (String)map.get("murl");
//            System.out.println(murl);
            String title = element.select(".inflnk").get(0).attr("aria-label");
//            System.out.println(title);
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
//            if(pictureList.size() >= pageSize){
//                break;    // 达到每页数量，退出循环！
//            }
            pictureList.add(picture);
        }
//        System.out.println(pictureList);
        Page<Picture> picturePage = new Page<>(pageNum,pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
