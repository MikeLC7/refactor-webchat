package com.jfinal.MikeLC.interceptor;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.JsTicket;
import com.jfinal.weixin.sdk.api.JsTicketApi;
import com.jfinal.weixin.sdk.api.JsTicketApi.JsApiType;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Project: TVMALL
 *
 * File: JSSDKInterceptorForApi
 *
 * Description:
 *
 * @author: MikeLC
 *
 * @date: 2017/11/20 下午 08:00
 *
 * Copyright ( c ) 2017
 *
 */
public class JSSDKInterceptorForApi {


    public static Map getJSSDKMapForUrlNormalVersion(HttpServletRequest request,String url) {
        //
        Map resultMap = new HashMap();

        JsTicket jsApiTicket = JsTicketApi.getTicket(JsApiType.jsapi);
        String jsapi_ticket = jsApiTicket.getTicket();
        String nonce_str = create_nonce_str();
        /**
         * 判断访问链接提供通用的分享链接
         */
        System.out.println("url>>>>" + url);
        String shareUrl = url;
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
        resultMap.put("appId", ApiConfigKit.getApiConfig().getAppId());
        resultMap.put("nonceStr", nonce_str);
        resultMap.put("timestamp", timestamp);
        resultMap.put("url", url);
        resultMap.put("signature", signature);
        resultMap.put("jsapi_ticket", jsapi_ticket);
        /**
         * project part
         */
        if(!shareUrl.contains("details") && !shareUrl.equals("https://prod.jbhot.cn/vueMobile/list")){
            shareUrl = "https://prod.jbhot.cn/vueMobile/";
        }
        String calbackUrl = PropKit.get("domain") + "/wxOuath/ouathOperate.html";
        String ouathUrl = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, shareUrl, true);
        shareUrl = ouathUrl;
        //
        resultMap.put("common_logo", "https://image.jbhot.cn/upload/resource/yipinlou_Logo.png");
        resultMap.put("common_title", "顶级仿生模特陪伴。");
        resultMap.put("common_desc", "合法、安全、卫生、私密，无限释放你所有欲望。");
        //
        resultMap.put("share_url",shareUrl);
        //
        return resultMap;
    }


    public static Map getJSSDKMapForUrl(HttpServletRequest request,String url) {
        //
        Map resultMap = new HashMap();

        JsTicket jsApiTicket = JsTicketApi.getTicket(JsApiType.jsapi);
        String jsapi_ticket = jsApiTicket.getTicket();
        String nonce_str = create_nonce_str();
        /**
         * 判断访问链接提供通用的分享链接
         */
        System.out.println("url>>>>" + url);
        String shareUrl = url;
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
        resultMap.put("appId", ApiConfigKit.getApiConfig().getAppId());
        resultMap.put("nonceStr", nonce_str);
        resultMap.put("timestamp", timestamp);
        resultMap.put("url", url);
        resultMap.put("signature", signature);
        resultMap.put("jsapi_ticket", jsapi_ticket);
        /**
         * project part
         */
		/*if(!shareUrl.contains("details")){
			shareUrl = "https://prod.jbhot.cn/vueMobile/index.html";
		}*/
        resultMap.put("common_logo", "https://image.jbhot.cn/upload/resource/yipinlou_Logo.png");
        resultMap.put("common_title", "顶级仿生模特陪伴。");
        resultMap.put("common_desc", "合法、安全、卫生、私密，无限释放你所有欲望。");
        /**
         * 舍弃掉redis和数据库的存储，因为分享链接只有三种情况，故直接定义三种标示
         */
		/*String urlData = "index";
		String urlData2 = "";
		if(shareUrl.contains("list")){
			urlData = "list";
		}else if (shareUrl.contains("detail")){
			urlData = "detail";
			String[] splits = shareUrl.split("=");
			urlData2 = splits[1];
		}*/
        if(shareUrl.equals("https://prod.jbhot.cn/vueMobile/list/center")){
            shareUrl = "https://prod.jbhot.cn/vueMobile/";
        }else {
            if(!shareUrl.contains("details") && !shareUrl.contains("list")){
                shareUrl = "https://prod.jbhot.cn/vueMobile/";
            }
        }
        //
        String landStr = "https://prod.jbhot.cn/vueMobile/landing"+"?urlData="+shareUrl;
        resultMap.put("share_url",landStr);
        /**
         * 添加授权
         * Q：嵌套之后链接无法正常使用JSSDK接口
         */
        //resultMap.put("share_url",shareUrl);
		/*String calbackUrl = PropKit.get("domain") + "/apiOauth";
		String ouathUrl = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, shareUrl, true);*/

        //
		/*Map<String, String> map = JedisUtils.getMap(WebFrontSession.OUATH_URL_DATA_SESSION);
		String uuid = UUID.randomUUID().toString();
		Map sessionMap = new HashMap();
		if(map != null){
			sessionMap = map;

		}
		//sessionMap.put(uuid,ouathUrl);		//WebFrontSession.putObjToSessionById(WebFrontSession.OUATH_URL_DATA_SESSION,WebFrontSession.OUATH_URL_DATA,sessionMap);
		//JedisUtils.setObjectMap(WebFrontSession.OUATH_URL_DATA_SESSION,sessionMap,0);
		//String landStr = "https://prod.jbhot.cn/vueMobile/#/landing"+"?url_data="+ouathUrl;
		String landStr = "https://prod.jbhot.cn/vueMobile/#/landing"+"?urlData="+uuid;
		resultMap.put("share_url",landStr);
		*/
        //
        return resultMap;
    }

    public static Map getJSSDKMap(HttpServletRequest request) {
        //
        Map resultMap = new HashMap();

        JsTicket jsApiTicket = JsTicketApi.getTicket(JsApiType.jsapi);
        String jsapi_ticket = jsApiTicket.getTicket();
        String nonce_str = create_nonce_str();
        /**
         * 判断访问链接提供通用的分享链接
         */
        String servletPath = request.getServletPath();
        System.out.println("++++++"+servletPath);
		/*if(!servletPath.contains("productsV3")){
			servletPath = "/floor/mobile/getFloorDatas.html";
		}*/
        // 注意 URL 一定要动态获取，不能 hardcode.
        String url = "http://" + request.getServerName() // 服务器地址
            // + ":"
            // + getRequest().getServerPort() //端口号
            + request.getContextPath() // 项目名称
            + request.getServletPath();// 请求页面或其他地址
        String qs = request.getQueryString(); // 参数
        /**
         *
         */
		/*if(!servletPath.contains("productsV3")){
			qs = "REQUEST_JSON={commandInfo:{keyWord:%27m_index%27}}";
		}*/
        if (qs != null) {
            url = url + "?" + (request.getQueryString());
        }
        System.out.println("url>>>>" + url);
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
        resultMap.put("appId", ApiConfigKit.getApiConfig().getAppId());
        resultMap.put("nonceStr", nonce_str);
        resultMap.put("timestamp", timestamp);
        resultMap.put("url", url);
        resultMap.put("signature", signature);
        resultMap.put("jsapi_ticket", jsapi_ticket);
        /**
         * project part
         */
        resultMap.put("common_logo", "http://image3.peixunfan.cn/upload/resource/yipinlou_Logo.png");
        resultMap.put("common_title", "顶级仿生模特陪伴。");
        resultMap.put("common_desc", "合法、安全、卫生、私密，无限释放你所有欲望。");
        String shareUrl = url;
        if(!servletPath.contains("productsV3")){
            shareUrl = "/floor/mobile/getFloorDatas.html";
            shareUrl += "?REQUEST_JSON={commandInfo:{keyWord:%27m_index%27}}";
        }
        resultMap.put("share_url",shareUrl);
        //
        return resultMap;
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
}
