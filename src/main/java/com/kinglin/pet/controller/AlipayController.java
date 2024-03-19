package com.kinglin.pet.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.kinglin.pet.common.Constant;
import com.kinglin.pet.config.AliPayConfig;
import com.kinglin.pet.entity.Appointment;
import com.kinglin.pet.enums.PayStateEnum;
import com.kinglin.pet.model.Alipay;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.service.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付宝
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@RestController
@RequestMapping(Constant.API_NAME + "/alipay")
public class AlipayController {
    private static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";
    @Resource
    private AliPayConfig aliPayConfig;
    @Autowired
    private AppointmentServiceImpl appointmentService;

    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(Alipay alipay, HttpServletResponse httpResponse) throws Exception {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);

        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("out_trade_no", alipay.getTradeNo());  // 我们自己生成的订单编号
        jsonObject.set("total_amount", alipay.getTotalAmount()); // 订单的总金额
        jsonObject.set("subject", alipay.getSubject());   // 支付的名称
        jsonObject.set("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(jsonObject.toString());

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String sn = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String tradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 更新订单未已支付
                appointmentService.updateState(sn, PayStateEnum.PAID, gmtPayment, tradeNo);
            }
        }
        return "success";
    }

    @GetMapping("/return")
    public Result returnPay(Alipay alipay) throws AlipayApiException {
        LocalDateTime now = LocalDateTime.now();
        // 7天无理由退款
        Appointment appointment = appointmentService.getBySn(alipay.getTradeNo());
        if (appointment != null) {
            // 判断时间间隔
            long between = LocalDateTimeUtil.between(LocalDateTime.parse(appointment.getGmtPayment()), now, ChronoUnit.DAYS);
            if (between > 7) {
                return Result.error("-1", "该订单已超过7天，不支持退款");
            }
        }
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL,
                aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET,
                aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
        // 2. 创建 Request，设置参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.set("trade_no", alipay.getAlipayTraceNo());  // 支付宝回调的订单流水号
        bizContent.set("refund_amount", alipay.getTotalAmount());  // 订单的总金额
        bizContent.set("out_request_no", alipay.getTradeNo());   //  我的订单编号

        // 返回参数选项，按需传入
        //JSONArray queryOptions = new JSONArray();
        //queryOptions.add("refund_detail_item_list");
        //bizContent.put("query_options", queryOptions);

        request.setBizContent(bizContent.toString());

        // 3. 执行请求
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {  // 退款成功，isSuccess 为true
            System.out.println("调用成功");

            // 4. 退款，更新数据库状态
            appointmentService.updatePayState(alipay.getTradeNo(), PayStateEnum.REFUNDED);
            return Result.success();
        } else {   // 退款失败，isSuccess 为false
            System.out.println(response.getBody());
            return Result.error(response.getCode(), response.getBody());
        }
    }
}
