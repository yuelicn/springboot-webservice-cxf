package com.tusdao.base.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author yueli
 * @date 2019-08-07 16:32
 */
@Configuration
public class ContractConfig {
    @Value("${constarct.url}")
    private String constarctUrl;

    @Value("${constarct.issuer}")
    private String issuer;

    public String getConstarctUrl() {
        return constarctUrl;
    }

    public void setConstarctUrl(String constarctUrl) {
        this.constarctUrl = constarctUrl;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
