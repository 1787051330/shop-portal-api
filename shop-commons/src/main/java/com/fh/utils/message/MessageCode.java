package com.fh.utils.message;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;

@Data
public class MessageCode {
    public static int num = (int)(Math.random()*1000000);

    public static String code = "{\"code\":'"+num+"'}";

    public static void queryTest(String phone){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FkJc5YBgDZFkgQsaRh6", "uvdV6YsKxRcBSMcqO0wOnZlobVAhaV");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "小星星");
        request.putQueryParameter("TemplateCode", "SMS_180765588");
        request.putQueryParameter("TemplateParam", code);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


}
