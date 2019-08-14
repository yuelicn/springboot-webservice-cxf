package com.tusdao.webservice.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tusdao.base.utils.Base64Utils;
import com.tusdao.base.utils.XmlUtils;

import static com.tusdao.webservice.enums.WsInterfaceName.*;

import static com.tusdao.webservice.enums.WsWxFieldNameEnum.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装 webService params to call contract params
 *
 * @author yueli
 * @date 2019-08-07 13:04
 */
public class WebServiceMethodParamsUtils {
    /**
     * 封装gzcxfw_hlw_wxcx_Info_cs接口的参数
     *
     * @param wxid
     * @param password
     * @param macId
     * @return
     */
    public static String creareWxcxParams(String wxid, String password, String macId) {
        Map<String, String> xmlMap = new HashMap<>(3);
        xmlMap.put(WX_WXID.getFieldName(), wxid);
        xmlMap.put(WXCX_FIELD_PASSWOED.getFieldName(), password);
        xmlMap.put(WX_MACID.getFieldName(), macId);
        String wxcxParams = Base64Utils.encodeToStr(XmlUtils.makeXML(GZCXFW_HLW_WXCX_INFO_CS.getName()
                , xmlMap));
        return contractParamBase64(GZCXFW_HLW_WXCX_INFO_CS.getName(), "", wxcxParams);
    }

    /**
     * 封装gzcxfw_hlw_wxrz_Info_cs接口的参数
     *
     * @param wxid
     * @param xm
     * @param sfzh
     * @param sjh
     * @param macId
     * @param password
     * @return
     */
    public static String creareWxrzParams(String wxid, String xm, String sfzh, String sjh, String macId,
                                          String password) {
        Map<String, String> xmlMap = new HashMap<>(6);
        xmlMap.put(WX_WXID.getFieldName(), wxid);
        xmlMap.put(WXRZ_FIELD_XM.getFieldName(), xm);
        xmlMap.put(WXRZ_FIELD_SFZH.getFieldName(), sfzh);
        xmlMap.put(WXRZ_FIELD_SJH.getFieldName(), sjh);
        xmlMap.put(WX_MACID.getFieldName(), macId);
        xmlMap.put(WXRZ_FIELD_PASSWORD.getFieldName(), password);
        String wxrzParams = Base64Utils.encodeToStr(XmlUtils.makeXML(GZCXFW_HLW_WXRZ_INFO_CS.getName()
                , xmlMap));

        return contractParamBase64(GZCXFW_HLW_WXRZ_INFO_CS.getName(), "", wxrzParams);
    }

    public static String contractParamBase64(String methodName, String SOAPAction, String body) {
        Map<String, String> requestMap = new HashMap<>(3);
        requestMap.put("SOAPAction", SOAPAction);
        requestMap.put("InterfaceName", methodName);
        requestMap.put("Body", body);
        JSONObject requestParam = JSONObject.parseObject(JSON.toJSONString(requestMap));
        String requestParams = Base64Utils.encodeToStr(requestParam.toJSONString());
        return requestParams;
    }
}
