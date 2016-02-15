package marcopolo.commons;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * The <tt>HmacSha1Signature</tt> shows how to calculate 
 * a message authentication code using HMAC-SHA1 algorithm.
 *
 * <pre>
 * % java -version
 * java version "1.6.0_11"
 * % javac HmacSha1Signature.java 
 * % java -ea HmacSha1Signature
 * 104152c5bfdca07bc633eebd46199f0255c9f49d
 * </pre>
 * from: http://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/AuthJavaSampleHMACSignature.html
 */
public class HmacSha1Signature {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    public static String calculate(String data, String key) {

        String nReturn = "";    
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            nReturn = toHexString(mac.doFinal(data.getBytes()));

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nReturn;
    }
} // class