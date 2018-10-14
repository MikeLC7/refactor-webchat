package com.jfinal.weixin.sdk.api;

import com.jfinal.weixin.sdk.utils.HttpUtils;
import com.jfinal.weixin.sdk.utils.JsonUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Project: RefactorMall
 *
 * File: MediaApiV2
 *
 * Description:
 *
 * @author: MikeLC
 *
 * @date: 2018/6/26 下午 03:18
 *
 * Copyright ( c ) 2018
 *
 */
public class MediaApiV2 {

    private static String upload_url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=";
    private static String uploadVideoUrl = "http://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=";
    private static String uploadNews = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=";
    private static String get_url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=";
    private static String get_jssdk_media = "https://api.weixin.qq.com/cgi-bin/media/get/jssdk?access_token=";
    private static String add_news_url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";
    private static String uploadImgUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";
    private static String addMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=";
    private static String get_material_url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=";
    private static String del_material_url = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=";
    private static String update_news_url = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=";
    private static String get_materialcount_url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=";
    private static String batchget_material_url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";

    public MediaApiV2() {
    }

    public static ApiResult uploadMedia(MediaApiV2.MediaType mediaType, File file) {
        String url = upload_url + AccessTokenApi.getAccessTokenStr() + "&type=" + mediaType.get();
        String jsonStr = HttpUtils.upload(url, file, (String)null);
        return new ApiResult(jsonStr);
    }

    public static ApiResult uploadVideo(String mediaId, String title, String description) {
        String url = uploadVideoUrl + AccessTokenApi.getAccessTokenStr();
        HashMap mapData = new HashMap();
        mapData.put("media_id", mediaId);
        mapData.put("title", title);
        mapData.put("description", description);
        String jsonResult = HttpUtils.post(url, JsonUtils.toJson(mapData));
        return new ApiResult(jsonResult);
    }

    public static ApiResult uploadNews(List<MediaArticles> mediaArticles) {
        String url = uploadNews + AccessTokenApi.getAccessTokenStr();
        HashMap dataMap = new HashMap();
        dataMap.put("articles", mediaArticles);
        String jsonResult = HttpUtils.post(url, JsonUtils.toJson(dataMap));
        return new ApiResult(jsonResult);
    }

    public static MediaFile getMedia(String media_id) {
        String url = get_url + AccessTokenApi.getAccessTokenStr() + "&media_id=" + media_id;
        return HttpUtils.download(url);
    }

    public static MediaFile getJssdkMedia(String media_id) {
        String url = get_jssdk_media + AccessTokenApi.getAccessTokenStr() + "&media_id=" + media_id;
        return HttpUtils.download(url);
    }

    public static ApiResult addNews(List<MediaArticles> mediaArticles) {
        String url = add_news_url + AccessTokenApi.getAccessTokenStr();
        HashMap dataMap = new HashMap();
        dataMap.put("articles", mediaArticles);
        String jsonResult = HttpUtils.post(url, JsonUtils.toJson(dataMap));
        return new ApiResult(jsonResult);
    }

    public static ApiResult uploadImg(File imgFile) {
        String url = uploadImgUrl + AccessTokenApi.getAccessTokenStr();
        String jsonResult = HttpUtils.upload(url, imgFile, (String)null);
        return new ApiResult(jsonResult);
    }

    public static ApiResult addMaterial(File file, MediaApiV2.MediaType mediaType) {
        String url = addMaterialUrl + AccessTokenApi.getAccessTokenStr() + "&type=" + mediaType.get();
        String jsonResult = HttpUtils.upload(url, file, (String)null);
        return new ApiResult(jsonResult);
    }

    public static ApiResult addMaterial(File file, String title, String introduction) {
        String url = addMaterialUrl + AccessTokenApi.getAccessTokenStr();
        HashMap dataMap = new HashMap();
        dataMap.put("title", title);
        dataMap.put("introduction", introduction);
        String jsonResult = HttpUtils.upload(url, file, JsonUtils.toJson(dataMap));
        return new ApiResult(jsonResult);
    }

    public static InputStream getMaterial(String media_id) {
        String url = get_material_url + AccessTokenApi.getAccessTokenStr();
        HashMap dataMap = new HashMap();
        dataMap.put("media_id", media_id);
        return HttpUtils.download(url, JsonUtils.toJson(dataMap));
    }

    public static ApiResult delMaterial(String media_id) {
        String url = del_material_url + AccessTokenApi.getAccessTokenStr();
        HashMap dataMap = new HashMap();
        dataMap.put("media_id", media_id);
        String jsonResult = HttpUtils.post(url, JsonUtils.toJson(dataMap));
        return new ApiResult(jsonResult);
    }

    public static ApiResult updateNews(String media_id, int index, MediaArticles mediaArticles) {
        String url = update_news_url + AccessTokenApi.getAccessTokenStr();
        HashMap dataMap = new HashMap();
        dataMap.put("media_id", media_id);
        dataMap.put("index", Integer.valueOf(index));
        dataMap.put("articles", mediaArticles);
        String jsonResult = HttpUtils.post(url, JsonUtils.toJson(dataMap));
        return new ApiResult(jsonResult);
    }

    public static ApiResult getMaterialCount() {
        String url = get_materialcount_url + AccessTokenApi.getAccessTokenStr();
        String jsonResult = HttpUtils.get(url);
        return new ApiResult(jsonResult);
    }

    public static ApiResult batchGetMaterial(MediaApiV2.MediaType mediaType, int offset, int count) {
        String url = batchget_material_url + AccessTokenApi.getAccessTokenStr();
        if(offset < 0) {
            offset = 0;
        }

        if(count > 50) {
            count = 50;
        }

        if(count < 1) {
            count = 1;
        }

        HashMap dataMap = new HashMap();
        dataMap.put("type", mediaType.get());
        dataMap.put("offset", Integer.valueOf(offset));
        dataMap.put("count", Integer.valueOf(count));
        String jsonResult = HttpUtils.post(url, JsonUtils.toJson(dataMap));
        return new ApiResult(jsonResult);
    }

    public static enum MediaType {
        IMAGE,
        VOICE,
        VIDEO,
        NEWS,
        THUMB;

        private MediaType() {
        }

        public String get() {
            return this.name().toLowerCase();
        }
    }

}
