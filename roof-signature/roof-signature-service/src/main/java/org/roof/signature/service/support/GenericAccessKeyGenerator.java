package org.roof.signature.service.support;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.roof.signature.sdk.AccessKey;
import org.roof.signature.service.AccessKeyGenerator;

import java.util.UUID;

/**
 * AccessKey 生成器
 *
 * @author hzliuxin1
 * @since 2018/3/13 0013
 */
public class GenericAccessKeyGenerator implements AccessKeyGenerator {

    private static final int SECRET_LENGTH = 32;

    public String generateId() {
        return UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
    }

    public String generateSecret() {
        return RandomStringUtils.random(SECRET_LENGTH, true, true);
    }

    public AccessKey generate() {
        return new AccessKey(generateId(), generateSecret());
    }
}
