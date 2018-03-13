package org.roof.signature.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 生成签名<br />
 * 参看阿里云签名: https://help.aliyun.com/document_detail/56189.html?spm=a2c4g.11186623.6.580.r7ps1b
 *
 * @author liuxin
 * @since 2018/3/12
 */
public class GenericSignatureCreator implements SignatureCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericSignatureCreator.class);
    private static final String HMAC_SHA_1 = "HmacSHA1";
    private static final String UTF_8 = "UTF-8";

    @Override
    public String create(AccessKey accessKey, String requestMethod, Map<String, String> params) {
        return create(accessKey, new Signature(), requestMethod, params);
    }

    @Override
    public String create(AccessKey accessKey, Signature signature, String requestMethod, Map<String, String> params) {
        Map<String, String> paras = new HashMap<>();
        // 1. 系统参数
        paras.put("signatureMethod", signature.getSignatureMethod());
        if (signature.getSignatureNonce() != null) {
            paras.put("signatureNonce", signature.getSignatureNonce());
        }
        paras.put("accessKeyId", accessKey.getAccessKeyId());
        paras.put("signatureVersion", signature.getSignatureVersion());
        paras.put("timestamp", String.valueOf(signature.getTimestamp()));
        // 2. 业务API参数
        paras.putAll(params);
        // 3. 去除签名关键字Key
        if (paras.containsKey("signature"))
            paras.remove("signature");
        TreeMap<String, String> sortParas = new TreeMap<>();
        sortParas.putAll(paras);

        Iterator<String> it = sortParas.keySet().iterator();
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
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance(HMAC_SHA_1);
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes(UTF_8), HMAC_SHA_1));
        byte[] signData = mac.doFinal(stringToSign.getBytes(UTF_8));
        return URLEncoder.encode(new sun.misc.BASE64Encoder().encode(signData), UTF_8);
    }

    private String specialUrlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, UTF_8).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
