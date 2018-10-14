package com.refactor.webchat.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.api.JsTicketApi.JsApiType;
import com.refactor.mall.common.exception.BaseException;
import com.refactor.mall.common.msg.RestResponse;
import com.refactor.mall.common.util.CookieHelper;
import com.refactor.mall.common.vo.busi.ucenter.response.ResMemberLoginVo;
import com.refactor.webchat.feign.IMemberFromWebchatService;
import constant.CommonConstant;
import constant.WebchatUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vo.request.ShareUrlFindVo;
import vo.request.WebchatUrlFindVo;
import vo.response.ResShareUrlVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Project: RefactorMall
 *
 * File: WebchatApiOauthBiz
 *
 * Description: Webchat-ouath-相关处理类
 *
 * @author: MikeLC
 *
 * @date: 2018/6/29 下午 12:05
 *
 * Copyright ( c ) 2018
 *
 */
@Service
public class WebchatApiOauthBiz {

    static Log log = Log.getLog(WebchatApiOauthBiz.class);

    @Autowired
    private IMemberFromWebchatService iMemberFromWebchatService;

    /**
     * Description: 获取指定链接的授权地址
     *
     * @param:
     *
     * @author: MikeLC
     *
     * @date: 2018/6/26 下午 02:28
     */
    public String getOuathUrlBySessionId(WebchatUrlFindVo webchatUrlFindVo) {

        String urlData = webchatUrlFindVo.getRequestUrl();
        //
        String shareUrl = urlData;
        //
        String calbackUrl = PropKit.get("domain") + WebchatUrl.OUATH_URL;
        String ouathUrl = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, shareUrl, webchatUrlFindVo.getSnsapiBase());
        //
        return ouathUrl;
    }

    /**
     * Description: 授权相关操作
     *
     * @param:
     *
     * @author: MikeLC
     *
     * @date: 2018/6/26 下午 02:57
     */
    public void ouathOperate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //
        //用户同意授权，获取code
        String code=request.getParameter("code");
        String state=request.getParameter("state");
        if (code!=null) {
            String appId= ApiConfigKit.getApiConfig().getAppId();
            String secret= ApiConfigKit.getApiConfig().getAppSecret();
            //通过code换取网页授权access_token
            SnsAccessToken snsAccessToken= SnsAccessTokenApi.getSnsAccessToken(appId,secret,code);
            String token=snsAccessToken.getAccessToken();
            String openId=snsAccessToken.getOpenid();
            //拉取用户信息(需scope为 snsapi_userinfo)
            ApiResult apiResult= SnsApi.getUserInfo(token, openId);

            log.warn("getUserInfo:"+apiResult.getJson());
            if (apiResult.isSucceed()) {
                JSONObject jsonObject= JSON.parseObject(apiResult.getJson());
                //
                ResMemberLoginVo resMemberLoginVo = iMemberFromWebchatService.getSynMemberFromWebChat(apiResult.getJson()).getData();
                if (resMemberLoginVo == null){
                    throw BaseException.message("Webchat_ouathOperate：Fail");
                }
                //通过cookie传递给前端用户登录token
                CookieHelper.setCookie(CommonConstant.TOKEN_COOKIE_NAME, resMemberLoginVo.getToken());
            }
            //
            response.sendRedirect(state);
        }else {
            System.out.println("code is  null");
        }
    }



}
