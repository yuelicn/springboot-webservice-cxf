package com.tusdao.webservice.service.impl;

import com.tusdao.webservice.service.WebService;
import com.tusdao.webservice.utils.ContractUtils;
import com.tusdao.webservice.utils.WebServiceMethodParamsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tusdao.webservice.enums.WsInterfaceName.GZCXFW_HLW_WXCX_INFO_CS;
import static com.tusdao.webservice.enums.WsInterfaceName.GZCXFW_HLW_WXRZ_INFO_CS;


/**
 * @author yueli
 * @date 2019-08-05 19:17
 */
@javax.jws.WebService(serviceName = "gzcxfw_wx_webserviceService",
        targetNamespace = "http://36.101.208.59:8090/axis/services/bdcgzcxfw_wx")
public class WebServiceImpl implements WebService {

    private static Logger logger = LoggerFactory.getLogger(CatalogInfoImpl.class);

    @Autowired
    ContractUtils contractParams;


    @Override
    public String gzcxfwHlwWxrzInfoCs(String wxid, String xm, String sfzh, String sjh, String macId, String password) {
        logger.info("gzcxfwHlwWxrzInfoCs: 入参- wxid:{}, xm:{}, sfzh:{}, sjh:{}, macId:{}, pawd:{}", wxid, xm, sfzh,
                macId, password);

        String wxrz = WebServiceMethodParamsUtils.creareWxrzParams(wxid, xm, sfzh, sjh, macId, password);
        String responseStr = contractParams.remoteConstract(GZCXFW_HLW_WXRZ_INFO_CS.getName(), wxrz);

        logger.info("gzcxfwHlwWxrzInfoCs: 出参- responseStr:{}", responseStr);
        return responseStr;
    }

    @Override
    public String gzcxfwHlwWxcxInfoCs(String wxid, String password, String macId) {
        logger.info("gzcxfwHlwWxcxInfoCs: 入参- wxid:{}, macId:{}, pawd:{}", wxid, macId, password);

        String wxcx = WebServiceMethodParamsUtils.creareWxcxParams(wxid, password, macId);
        String responseStr = contractParams.remoteConstract(GZCXFW_HLW_WXCX_INFO_CS.getName(), wxcx);

        logger.info("gzcxfwHlwWxrzInfoCs: 出参- responseStr:{}", responseStr);
        return responseStr;
    }

}
