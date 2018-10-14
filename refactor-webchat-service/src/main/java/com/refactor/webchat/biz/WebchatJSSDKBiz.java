package com.refactor.webchat.biz;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.JsTicket;
import com.jfinal.weixin.sdk.api.JsTicketApi;
import com.jfinal.weixin.sdk.api.JsTicketApi.JsApiType;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.refactor.mall.common.util.StringsUtils;
import constant.WebchatUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vo.request.ShareUrlFindVo;
import vo.response.ResShareUrlVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Project: RefactorMall
 *
 * File: WebchatJSSDKBiz
 *
 * Description: Webchat-JSSDK-相关处理类
 *
 * @author: MikeLC
 *
 * @date: 2018/6/29 下午 12:00
 *
 * Copyright ( c ) 2018
 *
 */
@Service
public class WebchatJSSDKBiz {

    /**
     * Description: 微信-获取分享信息-基础处理防范
     *
     * @param:
     *
     * @author: MikeLC
     *
     * @date: 2018/6/26 上午 11:59
     */
    public ResShareUrlVo getJSSDKBaseOperate(ShareUrlFindVo shareUrlFindVo) {
        //
        ResShareUrlVo resShareUrlVo = new ResShareUrlVo();
        //
        String url = shareUrlFindVo.getRequestUrl();
        String shareUrl = shareUrlFindVo.getShareUrl();
        JsTicket jsApiTicket = JsTicketApi.getTicket(JsApiType.jsapi);
        String jsapi_ticket = jsApiTicket.getTicket();
        String nonce_str = create_nonce_str();
        /**
         * 判断访问链接提供通用的分享链接
         */
        System.out.println("url>>>>" + url);
        String[] split = url.split("#");
        url = split[0];
        String timestamp = create_timestamp();
        // 这里参数的顺序要按照 key 值 ASCII 码升序排序
        //注意这里参数名必须全部小写，且必须有序
        String  str = "jsapi_ticket=" + jsapi_ticket +
            "&noncestr=" + nonce_str +
            "&timestamp=" + timestamp +
            "&url=" + url;

        String signature = HashKit.sha1(str);

        System.out.println("appId " + ApiConfigKit.getApiConfig().getAppId()
            + "  nonceStr " + nonce_str + " timestamp " + timestamp);
        System.out.println("url " + url + " signature " + signature);
        System.out.println("nonceStr " + nonce_str + " timestamp " + timestamp);
        System.out.println(" jsapi_ticket " + jsapi_ticket);
        System.out.println("nonce_str  " + nonce_str);
        //
        resShareUrlVo.setAppId(ApiConfigKit.getApiConfig().getAppId());
        resShareUrlVo.setNonceStr(nonce_str);
        resShareUrlVo.setTimestamp(timestamp);
        resShareUrlVo.setRequestUrl(url);
        resShareUrlVo.setSignature(signature);
        resShareUrlVo.setJsapi_ticket(jsapi_ticket);
        /**
         * ShareInfo
         */
        //
        if (StringUtils.isBlank(shareUrlFindVo.getShareUrl())){
            resShareUrlVo.setShareUrl(PropKit.get("base_domain") + PropKit.get("share_default_url"));
        } else{
            resShareUrlVo.setShareUrl(shareUrlFindVo.getShareUrl());
        }
        if (StringUtils.isBlank(shareUrlFindVo.getShareLogo())){
            resShareUrlVo.setShareLogo(PropKit.get("share_default_logo"));
        } else {
            resShareUrlVo.setShareLogo(shareUrlFindVo.getShareLogo());
        }
        if (StringUtils.isBlank(shareUrlFindVo.getShareTitle())){
            resShareUrlVo.setShareTitle(PropKit.get("share_default_title"));
        } else {
            resShareUrlVo.setShareTitle(shareUrlFindVo.getShareTitle());
        }
        if (StringUtils.isBlank(shareUrlFindVo.getShareDesc())){
            resShareUrlVo.setShareDesc(PropKit.get("share_default_desc"));
        } else {
            resShareUrlVo.setShareDesc(shareUrlFindVo.getShareDesc());
        }
        //
        return resShareUrlVo;
    }

    /**
     * Description: 常规方式获取分享信息
     *
     * @param:
     *
     * @author: MikeLC
     *
     * @date: 2018/6/26 上午 11:56
     */
    public ResShareUrlVo getJSSDKInfoForNormalVersion(ShareUrlFindVo shareUrlFindVo) {
        //BaseOperate
        String shareUrl = StringUtils.isBlank(shareUrlFindVo.getShareUrl())? shareUrlFindVo.getRequestUrl() : shareUrlFindVo.getShareUrl();
        ResShareUrlVo resShareUrlVo = getJSSDKBaseOperate(shareUrlFindVo);
        /**
         * ShareInfo
         */
        String calbackUrl = PropKit.get("domain") + WebchatUrl.OUATH_URL;
        String ouathUrl = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, shareUrl, shareUrlFindVo.getSnsapiBase());
        shareUrl = ouathUrl;
        resShareUrlVo.setShareUrl(shareUrl);
        //
        return resShareUrlVo;
    }

    /**
     * Description: 落地中转形式的分享信息获取
     *
     * @param:
     *
     * @author: MikeLC
     *
     * @date: 2018/6/26 上午 11:57
     */
    public ResShareUrlVo getJSSDKInfoForLandingMode(ShareUrlFindVo shareUrlFindVo) {
        //BaseOperate
        String shareUrl = shareUrlFindVo.getRequestUrl();
        ResShareUrlVo resShareUrlVo = getJSSDKBaseOperate(shareUrlFindVo);
        //
        String landStr = PropKit.get("base_domain") + PropKit.get("share_landing_url") + "?" + "urlData=" + shareUrl;
        resShareUrlVo.setShareUrl(landStr);
        //
        return resShareUrlVo;
    }


    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

}
