package rendezvous.activiti;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by ksingletary on 4/20/16.
 */
public class AllowSSLCerts {
    protected static final String TAG = "AllowSSLCerts";

    public static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X
                    }
            }
        }
    }
}
