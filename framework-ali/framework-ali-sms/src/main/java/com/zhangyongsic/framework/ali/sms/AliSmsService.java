package com.zhangyongsic.framework.ali.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.nio.cs.ext.ISCII91;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/07/13
 */
public class AliSmsService {

    private AliSmsConfig aliSmsConfig;

    public AliSmsService(AliSmsConfig aliSmsConfig) {
        this.aliSmsConfig = aliSmsConfig;
    }

    public SendSmsResponse smsRequest(SendSmsRequest request) {
        try {
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliSmsConfig.getAccessKeyId(), aliSmsConfig.getAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
            IAcsClient acsClient = new DefaultAcsClient(profile);
            request.setMethod(MethodType.POST);
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equals("OK")) {
                throw new BusinessException();
            }
            return sendSmsResponse;
        }catch (ClientException e){
            throw new BusinessException();
        }
    }
}
