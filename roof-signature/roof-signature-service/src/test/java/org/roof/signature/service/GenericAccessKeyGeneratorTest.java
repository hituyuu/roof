package org.roof.signature.service;

import org.junit.Test;
import org.roof.signature.sdk.AccessKey;
import org.roof.signature.service.support.GenericAccessKeyGenerator;

/**
 * @author hzliuxin1
 * @since 2018/3/13 0013
 */
public class GenericAccessKeyGeneratorTest {

    @Test
    public void generateId() {
        AccessKeyGenerator accessKeyGenerator = new GenericAccessKeyGenerator();
        AccessKey accessKey = accessKeyGenerator.generate();
        System.out.println(accessKey);
    }

    @Test
    public void generateSecret() {
    }

    @Test
    public void generate() {
    }
}