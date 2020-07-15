package com.fh.github.wxpay.sdk;

import java.io.InputStream;

/**
 * @author xiezhuang
 * @ClassName MyWXConfig
 * @date 2020/7/7 12:40
 */
public class MyWXConfig extends WXPayConfig{
    /**
     * 应用的ID
     * @return
     */
    @Override
    public String getAppID() {
        return "wxa1e44e130a9a8eee";
    }

    /**
     * 商家ID
     * @return
     */
    @Override
    public String getMchID() {
        return "1507758211";
    }

    /**
     * 安全密钥
     * @return
     */
    @Override
    public String getKey() {
        return "feihujiaoyu12345678yuxiaoyang123";
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
    }
}
