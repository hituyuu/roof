package org.roof.signature.service;

import org.roof.signature.sdk.AccessKey;

/**
 * 密钥生成器
 *
 * @author hzliuxin1
 * @since 2018/3/13 0013
 */
public interface AccessKeyGenerator {
    /**
     * 生成AccessKeyId
     *
     * @return
     */
    String generateId();

    /**
     * 生成AccessKeySecret
     *
     * @return
     */
    String generateSecret();

    /**
     * 生成AccessKey
     *
     * @return
     */
    AccessKey generate();
}
