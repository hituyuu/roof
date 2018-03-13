package org.roof.signature.service.support;

import org.apache.commons.lang3.StringUtils;
import org.roof.signature.sdk.AccessKey;
import org.roof.signature.sdk.SignatureCreator;
import org.roof.signature.service.AccessKeyService;
import org.roof.signature.service.SignatureValidator;

import java.util.Map;

/**
 * 签名验证器
 *
 * @author hzliuxin1
 * @since 2018/3/13 0013
 */
public class GenericSignatureValidator implements SignatureValidator {

    private static final String ACCESS_KEY_ID_KEY = "accessKeyId";
    private AccessKeyService accessKeyService;
    private SignatureCreator signatureCreator;


    public boolean valid(String requestMethod, Map<String, String> params, String signature) {
        String accessKeyId = params.get(ACCESS_KEY_ID_KEY);
        if (StringUtils.isEmpty(accessKeyId)) {
            return false;
        }
        AccessKey accessKey = accessKeyService.queryById(accessKeyId);
        if (accessKey == null) {
            return false;
        }
        if (StringUtils.isEmpty(signature)) {
            return false;
        }
        String signatureOriginal = signatureCreator.create(accessKey, requestMethod, params);
        return StringUtils.equals(signature, signatureOriginal);
    }

    public void setAccessKeyService(AccessKeyService accessKeyService) {
        this.accessKeyService = accessKeyService;
    }

    public void setSignatureCreator(SignatureCreator signatureCreator) {
        this.signatureCreator = signatureCreator;
    }
}
