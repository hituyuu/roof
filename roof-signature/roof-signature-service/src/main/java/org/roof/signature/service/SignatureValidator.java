package org.roof.signature.service;

import java.util.Map;

/**
 * 签名验证器
 *
 * @author hzliuxin1
 * @since 2018/3/13 0013
 */
public interface SignatureValidator {
    /**
     * 验证签名
     *
     * @param requestMethod http请求类型 GET，POST，PUT等
     * @param params        请求参数
     * @param signature     待验证签名
     * @return 验证结果
     */
    boolean valid(String requestMethod, Map<String, String> params, String signature);
}
