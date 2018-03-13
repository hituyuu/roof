package org.roof.signature.sdk;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hzliuxin1
 * @since 2018/3/13 0013
 */
public class GenericSignatureCreatorTest {

    @Test
    public void create() {
        SignatureCreator signatureCreator = new GenericSignatureCreator();
        Map<String, String> params = new HashMap<>();
        params.put("Action", "2017-05-25");
        params.put("Version", "SendSms");
        params.put("RegionId", "cn-hangzhou");
        String s = signatureCreator.create(new AccessKey("bbxgOwZnIjeeWSRE", "Nnq2c6OzZmSkngolBzTTx8sa5VJww3sQ"),
                new Signature(1520901135000L, "6b5427c2-5409-4e5a-83f6-9b7b4a29b387"), "GET", params);
        System.out.println(s);


        s = signatureCreator.create(new AccessKey("bbxgOwZnIjeeWSRE", "Nnq2c6OzZmSkngolBzTTx8sa5VJww3sQ"), "GET", params);
        System.out.println(s);
    }
}