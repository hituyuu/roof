package org.roof.signature.service;

import org.roof.signature.sdk.AccessKey;

/**
 * accessKey service
 *
 * @author hzliuxin1
 * @since 2018/3/13 0013
 */
public interface AccessKeyService {
    /**
     * 保存accessKey
     *
     * @param accessKey
     */
    void save(AccessKey accessKey);

    /**
     * 根据AccessKeyId 查询AccessKey
     *
     * @param accessKeyId
     * @return
     */
    AccessKey queryById(String accessKeyId);


}