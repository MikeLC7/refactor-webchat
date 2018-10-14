package com.refactor.webchat.rest.webchat.jssdk;


import com.refactor.mall.common.msg.RestResponse;
import com.refactor.webchat.biz.WebchatJSSDKBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.request.ShareUrlFindVo;
import vo.response.ResShareUrlVo;

@RestController
@RequestMapping(value = "webchatJssdk")
public class WebChatJSSDKController {

    @Autowired
    private WebchatJSSDKBiz webchatJSSDKBiz;

    /**
     * Description: 获取分享要素
     *
     * @param:
     *
     * @author: MikeLC
     *
     * @date: 2018/7/3 下午 05:23
     */
    @RequestMapping(value = "/getJSSDKInfoBaseVersion")
    public RestResponse getJSSDKInfoBaseVersion(@RequestBody ShareUrlFindVo shareUrlFindVo) {
        ResShareUrlVo resShareUrlVo = webchatJSSDKBiz.getJSSDKBaseOperate(shareUrlFindVo);
        //
        return RestResponse.success(resShareUrlVo);
    }

    /**
     * Description: 常规方式获取分享信息
     *
     * @param:
     *
     * @author: MikeLC
     *
     * @date: 2018/6/26 下午 12:10
     */
    @RequestMapping(value = "/getJSSDKInfoForNormalVersion")
    public RestResponse getJSSDKInfoForNormalVersion(@RequestBody ShareUrlFindVo shareUrlFindVo) {
        ResShareUrlVo resShareUrlVo = webchatJSSDKBiz.getJSSDKInfoForNormalVersion(shareUrlFindVo);
        //
        return RestResponse.success(resShareUrlVo);
    }

    /**
     * Description: 落地中转形式的分享信息获取
     *
     * @param:
     *
     * @author: MikeLC
     *
     * @date: 2018/6/26 下午 03:22
     */
    @RequestMapping(value = "/getJSSDKInfoForLandingMode")
    public RestResponse getJSSDKInfoForLandingMode(@RequestBody ShareUrlFindVo shareUrlFindVo) {
        ResShareUrlVo resShareUrlVo = webchatJSSDKBiz.getJSSDKInfoForLandingMode(shareUrlFindVo);
        //
        return RestResponse.success(resShareUrlVo);
    }




}
