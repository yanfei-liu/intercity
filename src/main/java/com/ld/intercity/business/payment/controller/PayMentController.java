package com.ld.intercity.business.payment.controller;

import com.google.gson.Gson;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import com.ld.intercity.business.payment.utils.HttpClientUtil;
import com.ld.intercity.business.payment.utils.WXPayConstants;
import com.ld.intercity.business.payment.utils.WXPayUtil;
import com.ld.intercity.business.user.model.UserModel;
import com.ld.intercity.business.user.service.UserService;
import com.ld.intercity.utils.ResponseResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RequestMapping("/PayMent")
@RestController
public class PayMentController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    /*调用支付接口*/
    @RequestMapping("/prePay")
    @ResponseBody
    public Map<String, Object> prePay(@RequestParam("orderSn") String orderSn, HttpServletRequest request){

        // 返回参数
        Map<String, Object> resMap = new HashMap<>();
        //获取请求ip地址
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if(ip.indexOf(",")!=-1){
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }

        try {

            // 拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<>();
            OrderModel oneByOrderSn = orderService.getOneByOrderSn(orderSn);
            String body = oneByOrderSn.getRouteId();//商品名称
            String orderNum = oneByOrderSn.getOrderSn();//订单号
            ResponseResult<UserModel> byId = userService.getById(oneByOrderSn.getCreatePresion());
            UserModel data = byId.getData();
            String openId = data.getWeChatId();
            String money = oneByOrderSn.getOrderAmount();
            BigDecimal bigDecimal = new BigDecimal(money).multiply(new BigDecimal("100"));
            int round = Math.round(bigDecimal.floatValue());
//       Integer price = 1;//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
            System.out.println("body= "+body);
//       body = new String(body.getBytes("ISO-8859-1"),"UTF-8").toString();
//       System.out.println("body= "+body);
            // 封装11个必需的参数
            paraMap.put("appid", WXPayConstants.APP_ID);
            paraMap.put("mch_id", WXPayConstants.MCH_ID);//商家ID
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            paraMap.put("body", body);     //商品名称
            paraMap.put("out_trade_no", orderNum);//订单号
            paraMap.put("total_fee",Integer.toString(round));
            paraMap.put("spbill_create_ip", "106.117.100.71");
            paraMap.put("notify_url",WXPayConstants.CALLBACK_URL);// 此路径是微信服务器调用支付结果通知路径
            paraMap.put("trade_type", "JSAPI");
            paraMap.put("openid", openId);
            paraMap.put("profit_sharing", "Y");
            String sign = WXPayUtil.generateSignature(paraMap, WXPayConstants.PATERNER_KEY);//商户密码
            //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
            paraMap.put("sign", sign);
            //将所有参数(map)转xml格式
            String xml = WXPayUtil.mapToXml(paraMap);
            System.err.println("xml=: "+xml);
            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
            String unifiedorder_url = WXPayConstants.UNIFIEDORDER_URL;//统一下单接口
            System.out.println("统一下单接口unifiedorder_url:"+unifiedorder_url);
            //发送post请求"统一下单接口"返回预支付id:prepay_id
            String xmlStr = HttpClientUtil.doPostXml(unifiedorder_url, xml);
            System.out.println("xmlStr:"+xmlStr);
            //以下内容是返回前端页面的json数据
            //预支付id
            String prepay_id = "";
            if (xmlStr.indexOf("SUCCESS") != -1) {
                Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);//XML格式字符串转换为Map
                prepay_id =  map.get("prepay_id").toString();
                System.err.println("prepay_id_1=  "+prepay_id);
            }
            System.err.println("prepay_id_2=  "+prepay_id);
            Map<String, String> payMap = new HashMap<String, String>();
            // 封装所需6个参数调支付页面
            payMap.put("appId", WXPayConstants.APP_ID);
            payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp()+"");//获取当前时间戳，单位秒
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id="+prepay_id);
            //生成带有 sign 的 XML 格式字符串
            String paySign = WXPayUtil.generateSignature(payMap, WXPayConstants.PATERNER_KEY);
            payMap.put("paySign", paySign);
            // 封装正常情况返回数据
            resMap.put("success",true);
            resMap.put("payMap",payMap);
        } catch (Exception e) {
            // 封装异常情况返回数据
            resMap.put("success",false);
            resMap.put("message","调用统一订单接口错误");
            e.printStackTrace();
        }
        return resMap;
    }

    /*支付成功回调*/
    @RequestMapping("/callBack")
    public ResponseResult<String> callBack(HttpServletRequest request, HttpServletResponse response){
        ResponseResult<String> result = new ResponseResult<>();
        System.err.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
        InputStream is = null;
        String resXml = "";
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = WXPayUtil.inputStream2String(is);
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
            if(notifyMap.get("return_code").equals("SUCCESS")){
                String ordersNum = notifyMap.get("out_trade_no").toString();//商户订单号
                String transactionId = notifyMap.get("transaction_id").toString();//微信支付订单号
                String totalFee = notifyMap.get("total_fee").toString();//订单金额
                //处理订单状态
                try {
                    orderService.orderSetting(ordersNum);
                    result.setSuccess(true);
                    result.setMessage("支付回调成功，修改订单状态为支付成功");
                    //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    BufferedOutputStream out = new BufferedOutputStream(
                            response.getOutputStream());
                    out.write(resXml.getBytes());
                    out.flush();
                    out.close();
                    System.err.println("返回给微信的值："+resXml.getBytes());
                    is.close();
                    //添加分账账号
                    Map<String, String> paraMap = new HashMap<>();
                    OrderModel oneByOrderSn = orderService.getOneByOrderSn(ordersNum);
                    ResponseResult<UserModel> byId = userService.getById(oneByOrderSn.getJieDanPresion());
                    String weChatId = byId.getData().getWeChatId();
                    paraMap.put("mch_id",WXPayConstants.MCH_ID);
                    paraMap.put("appid",WXPayConstants.APP_ID);
                    paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
                    String sign = WXPayUtil.generateSignature(paraMap, WXPayConstants.PATERNER_KEY);//商户密码
                    //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
                    paraMap.put("sign", sign);
                    HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                    stringObjectHashMap.put("type","PERSONAL_OPENID");
                    stringObjectHashMap.put("account",weChatId);
                    stringObjectHashMap.put("relation_type","STAFF");
                    Gson gson = new Gson();
                    String s = gson.toJson(stringObjectHashMap);
                    paraMap.put("receiver", s);
                    String s1 = WXPayUtil.mapToXml(paraMap);
                    String add_separate_accounts_url = WXPayConstants.add_separate_accounts_url;
                    String xmlStr = HttpClientUtil.doPostXml(add_separate_accounts_url, s1);
                    if (xmlStr.indexOf("SUCCESS") != -1) {
                        Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);//XML格式字符串转换为Map
                        paraMap.clear();
                        paraMap.put("mch_id",WXPayConstants.MCH_ID);
                        paraMap.put("appid",WXPayConstants.APP_ID);
                        paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
                        String sign1 = WXPayUtil.generateSignature(paraMap, WXPayConstants.PATERNER_KEY);//商户密码
                        //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
                        paraMap.put("sign", sign1);
                        paraMap.put("transaction_id",transactionId);
                        paraMap.put("out_order_no",ordersNum);
                        BigDecimal bigDecimal = new BigDecimal(totalFee);
                        HashMap<String, Object> stringObjectHashMap1 = new HashMap<>();
                        stringObjectHashMap1.put("type","PERSONAL_OPENID");
                        stringObjectHashMap1.put("account",weChatId);
                        stringObjectHashMap1.put("amount",bigDecimal.intValue());
                        stringObjectHashMap1.put("description","分到个人");
                        String s2 = gson.toJson(stringObjectHashMap);
                        paraMap.put("receivers",s2);
                        String s3 = WXPayUtil.mapToXml(paraMap);
                        String single_account_sharing_url = WXPayConstants.single_account_sharing_url;
                        String s4 = HttpClientUtil.doPostXml(single_account_sharing_url, s3);
                        // TODO 解析分账返回结果保存信息
                        Map<String, String> stringStringMap = WXPayUtil.xmlToMap(s4);
                        HashMap<String, String> stringObjectHashMap2 = new HashMap<>();
                        stringObjectHashMap2.put("sub_mchid",WXPayConstants.MCH_ID);
                        stringObjectHashMap2.put("transaction_id",transactionId);
                        stringObjectHashMap2.put("out_order_no",stringStringMap.get("out_order_no").toString());
                        stringObjectHashMap2.put("description","分账到个人");
                        String s5 = WXPayUtil.mapToXml(stringObjectHashMap2);
                        String s6 = HttpClientUtil.doPostXml(WXPayConstants.end_of_split_account_url, s5);
                    }
                }catch (Exception e){
                    result.setMessage("订单状态修改失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*调用退款接口，取消订单*/
    @RequestMapping("refund")
    @ResponseBody
    public Map<String, Object> refund(String orderSn,HttpServletResponse response){

        // 返回参数
        Map<String, Object> resMap = new HashMap<>();
        Date newtime = new Date();
        String resXml = "";
        try {
            // 拼接统一下单地址参数
            Map<String, String> paraMap = new HashMap<>();
            OrderModel order = orderService.getOneByOrderSn(orderSn);
            String orderNum = order.getOrderSn();//订单号
            String money = order.getOrderAmount();//金额
            BigDecimal bigDecimal = new BigDecimal(money);
//       Integer price = 1;//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
            System.out.println("订单号= "+orderNum);
            // 封装必需的参数
            paraMap.put("appid", WXPayConstants.APP_ID);
            paraMap.put("mch_id", WXPayConstants.MCH_ID);//商家ID
            paraMap.put("nonce_str", WXPayUtil.generateNonceStr());//获取随机字符串 Nonce Str
            paraMap.put("out_trade_no", orderNum);//订单号
            paraMap.put("out_refund_no", orderNum);//商户退款单号
            paraMap.put("total_fee",bigDecimal.toString());    //测试改为固定金额  订单金额
            paraMap.put("refund_fee",bigDecimal.toString());    //退款金额
//       paraMap.put("notify_url", WXPayConstants.notify_url);   //退款路径
            String sign = WXPayUtil.generateSignature(paraMap, WXPayConstants.PATERNER_KEY);//商户密码
            //生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
            paraMap.put("sign", sign);
            //将所有参数(map)转xml格式
            String xml = WXPayUtil.mapToXml(paraMap);
            System.out.println("xml:"+xml);
            // 退款 https://api.mch.weixin.qq.com/secapi/pay/refund
            String refund_url = WXPayConstants.REFUND_URL;//申请退款路径接口
            System.out.println("refund_url:"+refund_url);
            //发送post请求"申请退款"
            String xmlStr = HttpClientUtil.doRefund(refund_url, xml);
            System.out.println("退款xmlStr:"+xmlStr);
            /*退款成功回调修改订单状态*/
            if (xmlStr.indexOf("SUCCESS") != -1) {
                Map<String, String > map = WXPayUtil.xmlToMap(xmlStr);//XML格式字符串转换为Map
                if(map.get("return_code").equals("SUCCESS")){
                    resMap.put("success",true);//此步说明退款成功
                    resMap.put("data","退款成功");
                    System.out.println("退款成功");
                    Date refundtime = new Date();
                    Integer ordertype = -1;//-1取消订单
                    String complete = "已取消";
                    try {
                        order.setOrderType("6");
                        orderService.update(order);
                        //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        BufferedOutputStream out = new BufferedOutputStream(
                                response.getOutputStream());
                        out.write(resXml.getBytes());
                        out.flush();
                        out.close();
                        System.err.println("返回给微信的值："+resXml.getBytes());
                    }catch (Exception e){
                        resMap.put("fail","订单状态修改失败");
                    }
                }

            }else {
                resMap.put("success","fail");//此步说明退款成功
                resMap.put("data","退款失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resMap;
    }
}
