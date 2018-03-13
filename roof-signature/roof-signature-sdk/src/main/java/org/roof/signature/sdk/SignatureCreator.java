package org.roof.signature.sdk;

import java.util.Map;

/**
 * 签名生成器
 *
 * @author liuxin
 * @since 2018/3/12
 */
public interface SignatureCreator {
    /**
     * 生成签名<br/>
     * 签名失败返回空
     *
     * @param accessKey     密钥
     * @param signature     签名系统参数
     * @param requestMethod 请求方法(POST, GET)
     * @param params        参数列表
     * @return 签名
     */
    String create(AccessKey accessKey, Signature signature, String requestMethod, Map<String, String> params);

    /**
     * 生成签名<br/>
     * 签名失败返回空
     *
     * @param accessKey     密钥
     * @param requestMethod 请求方法(POST, GET)
     * @param params        参数列表
     * @return 签名
     */
    String create(AccessKey accessKey, String requestMethod, Map<String, String> params);
}
