package com.pay.alipay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignUtils {
	private static final String ALGORITHM = "RSA"; // 算法
    // private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String sign(String content, String privateKey) {
        String strSign = "";

        try {
            PKCS8EncodedKeySpec priPKC8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKC8);

            Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            strSign = Base64.encode(signed);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strSign;
    }
    
} // end public class SignUtils