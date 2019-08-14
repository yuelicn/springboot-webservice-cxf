package com.tusdao.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * @author yueli
 * @date 2019-08-05 19:14
 */
@javax.jws.WebService(serviceName = "gzcxfw_wx_webserviceService",
        targetNamespace = "http://36.101.208.59:8090/axis/services/bdcgzcxfw_wx")
public interface WebService {
    /**
     * 主要用于校验此微信号是否准许访问查询不动产登记信息接口，微信在聚合数据三网实名认证通过以后，
     * 继续在国土厅服务端进行认证，调用该接口返回是否认证成功或者失败的信息
     *
     * @param wxid     微信ID
     * @param xm       姓名
     * @param sfzh     身份证号
     * @param sjh      手机号
     * @param macId    预定用户
     * @param password 密码
     * @return 查询结果 Base64字符串
     */
    @WebMethod(operationName = "gzcxfw_hlw_wxrz_Info_cs")
    @WebResult(name = "gzcxfw_hlw_wxrz_Info_csReturn")
    String gzcxfwHlwWxrzInfoCs(@WebParam(name = "WXID", targetNamespace = "http://36.101.208.59:8090/axis/services/bdcgzcxfw_wx") String wxid, @WebParam(name = "XM") String xm,
                               @WebParam(name = "SFZH", targetNamespace = "http://36.101.208.59:8090/axis/services/bdcgzcxfw_wx") String sfzh, @WebParam(name = "SJH") String sjh,
                               @WebParam(name = "mac_id", targetNamespace = "http://36.101.208.59:8090/axis/services/bdcgzcxfw_wx") String macId, @WebParam(name = "password") String password
    );

    /**
     * 调用该接口返回不动产（房地产权）查询结果信息
     *
     * @param wxid     微信ID
     * @param password 密码
     * @param macId    预定用户
     * @return 查询结果Base64串
     */
    @WebMethod(operationName = "gzcxfw_hlw_wxcx_Info_cs")
    @WebResult(name = "gzcxfw_hlw_wxcx_Info_csReturn")
    String gzcxfwHlwWxcxInfoCs(@WebParam(name = "WXID", targetNamespace = "http://36.101.208.59:8090/axis/services/bdcgzcxfw_wx") String WXID,
                               @WebParam(name = "password", targetNamespace = "http://36.101.208.59:8090/axis/services/bdcgzcxfw_wx") String password,
                               @WebParam(name = "mac_id", targetNamespace = "http://36.101.208.59:8090/axis/services/bdcgzcxfw_wx") String mac_id
    );
}
