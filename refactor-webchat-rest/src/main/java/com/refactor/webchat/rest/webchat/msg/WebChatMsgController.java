package com.refactor.webchat.rest.webchat.msg;


import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MediaApiV2;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebChatMsgController extends MsgControllerAdapter {

    static Log logger = Log.getLog(WebChatMsgController.class);
    private static final String helpStr = "\t你的品位不错哦  么么哒。";

    protected void processInTextMsg(InTextMsg inTextMsg) {
        String msgContent = inTextMsg.getContent().trim();
        // 帮助提示
        if ("上传消息类型".equalsIgnoreCase(msgContent)) {

        } else if ("回复消息类型".equalsIgnoreCase(msgContent)) {

        } else if ("1".equalsIgnoreCase(msgContent)){
            //图文
            OutNewsMsg outNewsMsg = new OutNewsMsg(inTextMsg);

             renderForAll(outNewsMsg);
        }  else if ("2".equalsIgnoreCase(msgContent)){
            OutTextMsg outTextMsg = new OutTextMsg(inTextMsg);
            //
            render(outTextMsg);
        } else if (msgContent.equals("获取图片素材")) {
            ApiResult apiResult = MediaApiV2.batchGetMaterial(MediaApiV2.MediaType.IMAGE, 0, 100);
            System.out.println(apiResult.getJson());
            renderOutTextMsg("哈哈哈");
            //转发给多客服PC客户端
//			OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
//			render(outCustomMsg);
        } else if (msgContent.equals("获取语音素材")) {
            ApiResult apiResult = MediaApiV2.batchGetMaterial(MediaApiV2.MediaType.VOICE, 0, 100);
            System.out.println(apiResult.getJson());
            renderOutTextMsg("哈哈哈");
            //转发给多客服PC客户端
//			OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
//			render(outCustomMsg);
        }  else if (msgContent.equals("获取视频素材")) {
            ApiResult apiResult = MediaApiV2.batchGetMaterial(MediaApiV2.MediaType.VIDEO, 0, 100);
            System.out.println(apiResult.getJson());
            renderOutTextMsg("哈哈哈");
            //转发给多客服PC客户端
//			OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
//			render(outCustomMsg);
        }  else if (msgContent.equals("获取图文素材")) {
            ApiResult apiResult = MediaApiV2.batchGetMaterial(MediaApiV2.MediaType.NEWS, 0, 100);
            System.out.println(apiResult.getJson());
            renderOutTextMsg("哈哈哈");
            //转发给多客服PC客户端
//			OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
//			render(outCustomMsg);
        } else {
            renderOutTextMsg("无对应功能，返回您的输入："+msgContent);
        }

    }

    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg)
    {
        //转发给多客服PC客户端
        logger.info("processInVoiceMsg_mediaId="+inVoiceMsg.getMediaId());
        OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
        // 将刚发过来的语音再发回去
        outMsg.setMediaId(inVoiceMsg.getMediaId());
        render(outMsg);
    }

    @Override
    protected void processInVideoMsg(InVideoMsg inVideoMsg)
    {
		/*
		 * 腾讯 api 有 bug，无法回复视频消息，暂时回复文本消息代码测试 OutVideoMsg outMsg = new
		 * OutVideoMsg(inVideoMsg); outMsg.setTitle("OutVideoMsg 发送");
		 * outMsg.setDescription("刚刚发来的视频再发回去"); // 将刚发过来的视频再发回去，经测试证明是腾讯官方的 api
		 * 有 bug，待 api bug 却除后再试 outMsg.setMediaId(inVideoMsg.getMediaId());
		 * render(outMsg);
		 */
        logger.info("processInVideoMsg_mediaId="+inVideoMsg.getMediaId());
        OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
        outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inVideoMsg.getMediaId());
        render(outMsg);
		/*OutVideoMsg outVideoMsg = new OutVideoMsg(inVideoMsg);
		outVideoMsg.setMediaId(inVideoMsg.getMediaId());
		render(outVideoMsg);*/
    }

    @Override
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg)
    {
        //
        logger.info("processInShortVideoMsg_mediaId="+inShortVideoMsg.getMediaId());
        OutTextMsg outMsg = new OutTextMsg(inShortVideoMsg);
        outMsg.setContent("\t短视频消息已成功接收，该短视频的 mediaId 为: " + inShortVideoMsg.getMediaId());
        render(outMsg);
    }

    @Override
    protected void processInLocationMsg(InLocationMsg inLocationMsg)
    {
        OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
        outMsg.setContent("已收到地理位置消息:" + "\nlocation_X = " + inLocationMsg.getLocation_X() + "\nlocation_Y = "
                + inLocationMsg.getLocation_Y() + "\nscale = " + inLocationMsg.getScale() + "\nlabel = "
                + inLocationMsg.getLabel());
        render(outMsg);
    }

    @Override
    protected void processInLinkMsg(InLinkMsg inLinkMsg)
    {
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inLinkMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInCustomEvent(InCustomEvent inCustomEvent)
    {
        logger.debug("测试方法：processInCustomEvent()");
        renderNull();
    }

    protected void processInImageMsg(InImageMsg inImageMsg)
    {
        //转发给多客服PC客户端
//		OutCustomMsg outCustomMsg = new OutCustomMsg(inImageMsg);
//		render(outCustomMsg);

    }

    /**
     * 实现父类抽方法，处理关注/取消关注消息
     */
    protected void processInFollowEvent(InFollowEvent inFollowEvent)
    {
        if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent()))
        {
            logger.debug("关注：" + inFollowEvent.getFromUserName());
            OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
            outMsg.setContent(
                    "欢迎关注衣品楼！在衣品楼，您可以品味娇艳欲滴的顶级硅胶模特贴身穿着，您在这里品味的不只是衣服，还能让您安心释放所有欲望！极净服从，想怎么sh就怎么sh~芳与泽其杂糅兮,羌芳华自中出。 \n"+
                            "回复1一慕芳华； \n"+
                            "回复2了解预约及体验流程导读； \n"+
                            "回复3进入预约体验。 \n"
            );
            render(outMsg);
        }
        // 如果为取消关注事件，将无法接收到传回的信息
        if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent()))
        {
            logger.debug("取消关注：" + inFollowEvent.getFromUserName());
        }
    }

    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent)
    {
        System.out.println("扫码.......");
        if (InQrCodeEvent.EVENT_INQRCODE_SUBSCRIBE.equals(inQrCodeEvent.getEvent()))
        {
            logger.debug("扫码未关注：" + inQrCodeEvent.getFromUserName());
            OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
            outMsg.setContent("感谢您的关注，二维码内容：" + inQrCodeEvent.getEventKey());
            render(outMsg);
        }
        if (InQrCodeEvent.EVENT_INQRCODE_SCAN.equals(inQrCodeEvent.getEvent()))
        {
            logger.debug("扫码已关注：" + inQrCodeEvent.getFromUserName());
            renderOutTextMsg("扫码已关注,二维码内容：" + inQrCodeEvent.getEventKey());
        }

    }

    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent)
    {
        logger.debug("发送地理位置事件：" + inLocationEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
        //outMsg.setContent("授权后静默获取您的位置是：" + inLocationEvent.getLatitude());
        outMsg.setContent("授权后静默获取您的位置是:" + "\nlatitude = " + inLocationEvent.getLatitude() + "\nlongitude = "
                + inLocationEvent.getLongitude() + "\nprecision = " + inLocationEvent.getPrecision() );
        render(outMsg);
    }

    @Override
    protected void processInMassEvent(InMassEvent inMassEvent)
    {
        logger.debug("测试方法：processInMassEvent()");
        renderNull();
    }

    /**
     * 实现父类抽方法，处理自定义菜单事件
     */
    protected void processInMenuEvent(InMenuEvent inMenuEvent)
    {
        logger.debug("菜单事件：" + inMenuEvent.getFromUserName());
        String menuKey = inMenuEvent.getEventKey().trim();
        if(menuKey.equals("contract_us")){
            //联系我们
            OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
            outMsg.setContent("各种问题请尽情联系我们哦，欢迎在我们的公众号内留言，也可以联系我们的专属顾问电话：15711079126");
            render(outMsg);
        } else if(menuKey.equals("get_one_eye")){
            //一慕芳华图文
            OutNewsMsg outNewsMsg = new OutNewsMsg(inMenuEvent);
            outNewsMsg.addNews("想约谁就偷偷告诉我……","来了解每个最顶级的仿生模特娃娃吧，想约谁偷偷告诉我…","http://mmbiz.qpic.cn/mmbiz_jpg/K9yr4NKdEbrYpTqFibTOhW27xahtxN9zmg8zTzqCiamEPf5dbrAAhS5NqibDBic0ZRMPSS6hR8zaUtt7CQFcLPTySA/0?wx_fmt=jpeg","https://mp.weixin.qq.com/s?__biz=MzUzMzMzNzQ4NA==&mid=100000012&idx=1&sn=709827e022946d7c829347a098d1c8e8&chksm=7aa4c5ef4dd34cf9d76969a64f2876fd3726333fd252c92a9f72e5d15e6cfd91c69a5c60a8c1#rd");

            renderForAll(outNewsMsg);
        } else if(menuKey.equals("subscrib_guide")){
            //预约导读
            OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
            outMsg.setContent("回复3收到二维码后并识别，即可进入预约页面，您可以根据需求选择到店体验或上门服务，接下来预约体验时间。预约成功后有任何疑问、需求或想了解体验流程，可在公众号中向客服咨询，或者拨打电话15711079126，我们的专属顾问将会耐心的为您提供帮助。如果您是首次预约，建议您预约前拨打专属顾问电话15711079126进行咨询，以便我们为您提供更高效优质的服务。");
            render(outMsg);
        }  else{
            OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
            outMsg.setContent("菜单事件内容是：" + inMenuEvent.getEventKey());
            render(outMsg);
        }

    }

    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults)
    {
        logger.debug("语音识别事件：" + inSpeechRecognitionResults.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inSpeechRecognitionResults);
        outMsg.setContent("语音识别内容是：" + inSpeechRecognitionResults.getRecognition());
        render(outMsg);
    }

    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent)
    {
        logger.debug("测试方法：processInTemplateMsgEvent()");
        renderNull();
    }



}
