package com.tusdao.webservice.enums;

/**
 * @author yueli
 * @date 2019-08-07 16:48
 */
public enum WsInterfaceName {

    GZCXFW_HLW_WXRZ_INFO_CS("gzcxfw_hlw_wxrz_Info_cs"),
    GZCXFW_HLW_WXCX_INFO_CS("gzcxfw_hlw_wxcx_Info_cs");


    String name;

    WsInterfaceName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
