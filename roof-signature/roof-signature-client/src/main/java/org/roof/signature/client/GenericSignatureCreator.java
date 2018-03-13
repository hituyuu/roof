package org.roof.signature.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @author liuxin
 * @since 2018/3/12
 */
public class GenericSignatureCreator implements SignatureCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericSignatureCreator.class);


    @Override
    public String create(AccessKey accessKey, Signature signature, String requestMethod, Map<String, String> params) {
        java.util.Map<String, String> paras = new java.util.HashMap<>();
        // 1. 系统参数
        paras.put("SignatureMethod", signature.getSignatureMethod());
        paras.put("SignatureNonce", UUID.randomUUID().toString());
        paras.put("AccessKeyId", accessKey.getAccessKeyId());
        paras.put("SignatureVersion", signature.getSignatureVersion());
        paras.put("Timestamp", String.valueOf(System.currentTimeMillis()));
        // 2. 业务API参数
        paras.putAll(params);
        // 3. 去除签名关键字Key
        if (paras.containsKey("Signature"))
            paras.remove("Signature");
        TreeMap<String, String> sortParas = new TreeMap<>();
        sortParas.putAll(paras);

        java.util.Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);// 去除第一个多余的&符号
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append(requestMethod).append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(sortedQueryString));

        try {
            return sign(accessKey.getAccessKeySecret(), stringToSign.toString());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
    }

    private String specialUrlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
