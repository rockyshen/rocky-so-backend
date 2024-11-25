package com.yupi.springbootinit.datasource;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rockyshen
 * @date 2024/11/25 11:01
 * 注册
 */
@Component
public class DataSourceRegistry {
    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    private Map<String,DataSource<?>> typeDataSourceMap;

    // 单例模式，注册中心必须保证单例，保证只有唯一一个！
    @PostConstruct
    public void doInit(){
        typeDataSourceMap =  new HashMap(){{
            put("post",postDataSource);
            put("user",userDataSource);
            put("picture",pictureDataSource);
        }};
    }

//    static {
//
//    }

    public DataSource getDataSourceByType(String type){
        if(typeDataSourceMap == null){
            return null;
        }
        return typeDataSourceMap.get(type);
    }

}
