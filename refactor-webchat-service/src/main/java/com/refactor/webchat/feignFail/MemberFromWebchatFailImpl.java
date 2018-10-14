package com.refactor.webchat.feignFail;

import com.refactor.mall.common.enums.ResponseCode;
import com.refactor.mall.common.exception.BaseException;
import com.refactor.mall.common.msg.RestResponse;
import com.refactor.mall.common.vo.busi.ucenter.response.ResMemberLoginVo;
import com.refactor.webchat.feign.IMemberFromWebchatService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Project: RefactorMall
 *
 * File: MemberFromWebchatFailImpl
 *
 * Description:
 *
 * @author: MikeLC
 *
 * @date: 2018/6/26 下午 11:45
 *
 * Copyright ( c ) 2018
 *
 */
@Component
public class MemberFromWebchatFailImpl implements IMemberFromWebchatService {

    @Override
    public RestResponse<ResMemberLoginVo> getSynMemberFromWebChat(@RequestParam(value = "userInfoStr") String userInfoStr) {
        return RestResponse.response(ResponseCode.FEIGN_ERROR.getCode(), ResponseCode.FEIGN_ERROR.getName(), null);
    }
}
