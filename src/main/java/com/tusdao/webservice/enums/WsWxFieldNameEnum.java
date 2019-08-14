package com.tusdao.webservice.enums;

/**
 * @author yueli
 * @date 2019-08-07 13:24
 */
public enum WsWxFieldNameEnum {
    WX_WXID("WXID"),
    WX_MACID("mac_id"),

    WXCX_FIELD_PASSWOED("password"),

    WXRZ_FIELD_XM("XM"),
    WXRZ_FIELD_SFZH("SFZH"),
    WXRZ_FIELD_SJH("SJH"),
    WXRZ_FIELD_PASSWORD("password")





    ;


    String fieldName;

    WsWxFieldNameEnum(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
