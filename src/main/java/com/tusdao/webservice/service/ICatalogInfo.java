package com.tusdao.webservice.service;

import com.tusdao.webservice.model.CatalogInfo;

/**
 * @author yueli
 * @date 2019-08-07 15:34
 */
public interface ICatalogInfo {
    /**
     * 根据接口名称查询对应的合约信息
     *
     * @param wsMethod 接口名称
     * @param issuer   合约拥有者
     * @return 合约实体
     */
    CatalogInfo findByWsMethod(String wsMethod, String issuer);
}
