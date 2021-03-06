package org.roof.signature.sdk;

/**
 * 签名系统参数
 * 请求需要增加4个系统参数
 *  <li>1.signatureMethod 签名方法，默认HMAC-SHA1</li>
 *  <li>2.timestamp 时间戳</li>
 *  <li>3.signatureVersion 签名版本，默认1.0</li>
 *  <li>3.signatureNonce 请求唯一值，java.util.UUID.randomUUID()</li>
 * @author liuxin
 * @since 2018/3/12
 */
public class Signature {
    /**
     * 建议固定值：HMAC-SHA1
     */
    private String signatureMethod = "HMAC-SHA1";
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * 建议固定值：1.0
     */
    private String signatureVersion = "1.0";
    /**
     * 用于请求的防重放攻击，每次请求唯一，JAVA语言建议用：java.util.UUID.randomUUID()生成即可
     */
    private String signatureNonce;

    public Signature() {
    }

    public Signature(long timestamp, String signatureNonce) {
        this.timestamp = timestamp;
        this.signatureNonce = signatureNonce;
    }

    public String getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignatureVersion() {
        return signatureVersion;
    }

    public void setSignatureVersion(String signatureVersion) {
        this.signatureVersion = signatureVersion;
    }

    public String getSignatureNonce() {
        return signatureNonce;
    }

    public void setSignatureNonce(String signatureNonce) {
        this.signatureNonce = signatureNonce;
    }
}
