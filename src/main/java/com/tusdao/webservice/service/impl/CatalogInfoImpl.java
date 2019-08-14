package com.tusdao.webservice.service.impl;

import com.tusdao.webservice.dao.CatalogInfoMapper;
import com.tusdao.webservice.model.CatalogInfo;
import com.tusdao.webservice.model.CatalogInfoExample;
import com.tusdao.webservice.service.ICatalogInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yueli
 * @date 2019-08-07 15:36
 */
@Service
public class CatalogInfoImpl implements ICatalogInfo {

    private static Logger logger = LoggerFactory.getLogger(CatalogInfoImpl.class);

    @Resource
    private CatalogInfoMapper catalogInfoMapper;

    @Override
    public CatalogInfo findByWsMethod(String wsMethod, String issuer) {
        CatalogInfoExample example = new CatalogInfoExample();
        CatalogInfoExample.Criteria criteria = example.or();
        criteria.andWsMethodEqualTo(wsMethod);
        criteria.andTaskInitiatorEqualTo(issuer);
        List<CatalogInfo> catalogInfos = null;
        try {
            catalogInfos = catalogInfoMapper.selectByExample(example);
        } catch (Exception e) {
            logger.error("根据接口名称查询合约信息数据库异常：e = {}", e);
            throw new RuntimeException("查询合约数据库异常：" + e.getMessage());
        }

        if (null == catalogInfos || catalogInfos.isEmpty() || catalogInfos.size() <= 0) {
            logger.info("根据接口名称查询合约信息、合约不存在：interface:{}, issuer:{}", wsMethod, issuer);
            throw new RuntimeException("接口" + wsMethod + ":" + issuer + "对应合约不存在");
        }

        return catalogInfos.get(0);
    }
}
