package com.refactor.webchat.rest.webchat.ouath;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.refactor.mall.common.exception.BaseException;
import com.refactor.mall.common.msg.RestResponse;
import com.refactor.mall.common.util.CookieHelper;
import com.refactor.mall.common.vo.busi.ucenter.response.ResMemberLoginVo;
import com.refactor.webchat.biz.WebchatApiOauthBiz;
import com.refactor.webchat.feign.IMemberFromWebchatService;
import constant.CommonConstant;
import constant.WebchatUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.request.WebchatUrlFindVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

/**
 * Project: TVMALL
 *
 * File: WeiXinApiOauthController
 *
 * Description:
 *
 * @author: MikeLC
 *
 * @date: 2017/11/27 下午 05:41
 *
 * Copyright ( c ) 2017
 *
 */
@RestController
@RequestMapping(value = "webchatOuath")
public class WebchatApiOauthController {
	static Log log = Log.getLog(WebchatApiOauthController.class);

	@Autowired
	private WebchatApiOauthBiz webchatApiOauthBiz;
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
	@RequestMapping(value = "/getOuathUrlByUrl")
	public RestResponse getOuathUrlBySessionId(@RequestBody WebchatUrlFindVo webchatUrlFindVo) {
		//
		String ouathUrl = this.webchatApiOauthBiz.getOuathUrlBySessionId(webchatUrlFindVo);
		//
		return RestResponse.success(null, ouathUrl);
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
	@RequestMapping(value = "/ouathOperate")
	public void ouathOperate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//
		this.webchatApiOauthBiz.ouathOperate(request, response);
	}
	



}
