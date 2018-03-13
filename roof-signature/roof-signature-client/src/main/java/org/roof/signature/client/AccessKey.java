package org.roof.signature.client;

/**
 * 请求密钥
 *
 * @author liuxin
 * @since 2018/3/12
 */
public class AccessKey {
    private String accessKeyId;
    private String accessKeySecret;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
