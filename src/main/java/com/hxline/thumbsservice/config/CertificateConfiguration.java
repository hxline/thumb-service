package com.hxline.thumbsservice.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Handoyo
 */
public class CertificateConfiguration {

    public void create() {
        try {
            PrintWriter caWriter = new PrintWriter("/tmp/ca.pem", "UTF-8");
            String ca = "-----BEGIN CERTIFICATE-----\n"
                    + "MIIC/TCCAeWgAwIBAgIJAInMkmuFOue3MA0GCSqGSIb3DQEBCwUAMBUxEzARBgNV\n"
                    + "BAMMCnN0ZWFtZXIgQ0EwHhcNMTcwMzIwMTAxNDQ3WhcNMjcwMzE4MTAxNDQ3WjAV\n"
                    + "MRMwEQYDVQQDDApzdGVhbWVyIENBMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB\n"
                    + "CgKCAQEA1Ynya8GIQUp+M+y/ZLTGB5NKeohmf6DotK8H3bVziZA4kYQQbnGos1Bt\n"
                    + "DhaJElOmiN1jn9yed98qWU9O0LwpP0iKe3iqlbA/j3hm88xaNNq8DTC56L3jsEhb\n"
                    + "upTeqNSNYy9guLIanwWCb2h9mZJrDCsFNzcIlAYjOwSm6X9qDl/GACO2U24UF0V2\n"
                    + "On+k6CsRFYaSzFI3PqtbUkO33MsAOEQAMclh5usLC/Kh75kIFeDPYqf2wdfzQIaY\n"
                    + "pZUpZsxYFR1n8OCiFcWBC9xPdPiktRhKS114mf68sp7t2Mv5dFwLvDHKc4zVTM10\n"
                    + "8v+NPThlFdU0M0zs9fUToEZr6g7d9QIDAQABo1AwTjAdBgNVHQ4EFgQU/icA40s8\n"
                    + "OKt5Mers/V4KoqEN3YUwHwYDVR0jBBgwFoAU/icA40s8OKt5Mers/V4KoqEN3YUw\n"
                    + "DAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQsFAAOCAQEAS0INqy+tH8HqP6Nvpl8g\n"
                    + "NfaKtc0sktrILR9iMNjAvvoKmHP807fjcA0qmpcRKI8bshBhhuD/zroK82mWciqY\n"
                    + "QVrD5bcW1QcTb3HuuISF0R71qnDO/TjtG3fsYsltORzp97AcaOQblvDbNIKkUx0R\n"
                    + "bg3cBWjY7TjGSqQzwAk5oi4FyX+CsJfA+1GVwIuMFkr0lMyY/rGiHxqFypP05HGS\n"
                    + "iMqJNv16haPgK7Eg55fMuaMudFOcz+7838cmHOw+DZkjG7hFiVk0uOlhXK2cEyr/\n"
                    + "UOaCsbJaeA3l7Tk4/RM1ZywRB5qeeQyspwQhp3aSil04L8b0cZ/Qw29KE8YeXtpi\n"
                    + "hw==\n"
                    + "-----END CERTIFICATE-----";
            caWriter.println(ca);
            caWriter.close();

            PrintWriter certWriter = new PrintWriter("/tmp/cert.pem", "UTF-8");
            String cert = "-----BEGIN CERTIFICATE-----\n"
                    + "MIICoDCCAYgCCQDW0wSw88/hMjANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDDApz\n"
                    + "dGVhbWVyIENBMB4XDTE3MDUwOTA2NDk1OFoXDTI3MDUwNzA2NDk1OFowDzENMAsG\n"
                    + "A1UEAwwEd21qdTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALpnGnzt\n"
                    + "Uk54fYwLJ24pr6vna4DDF4Qxg6492qHPbJkx4GiF8NeXnCWdurj6FpMMJH27fNfL\n"
                    + "r+cxlw2E7Yz5/zvGbN/q4M6DhM2+iba3O37P9OlRjAFvpscLB9OicEe39Eaoa+p9\n"
                    + "mIP60hpYNizsKHF9682cYYCQ/6dGToVAftLlrgKWGwL3H5jHWwES57CMAM4XSvGM\n"
                    + "3vq7Wc3lMFon7O8RHgGiKN81Av67RoHfGfNGcK/3Zf0SZGnRXom5XKh1cHBafXxi\n"
                    + "KQFnd8beTaJEdUcIt3lFemUOEojetl+oSWRETNHBo1iWC78zOUs+MrRLSgyjUmKb\n"
                    + "4oesIWHvv7FaMlUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAyt4GGCttStRQn87j\n"
                    + "3e7lLPTeGwx9e3PkxSOmozwqCGmSNWukfrhIrIzvQLXDY4E5AgMw/DwPb+Bd4O5F\n"
                    + "mN3unoG6DVBxQGq/olOSfMkoQIZ4GuYhFKmka8bvPH01BX6NpE0ZxH89ixI9g5g4\n"
                    + "gK4dvuY9DCQF1Xi0tLTnvc/n5hpsO92o3eiSfawQ4JAFzXWaOD+6hMj9ZvBaCk3u\n"
                    + "IR9mrlFYPyoPoOxh9WTGo0cnkRxYfx6ahw0KlgDNgXUdyY9mJpDvp34xcHQ4YJS/\n"
                    + "qVuR8l0A2VbpfLK8r5u6ahSsWkt0K25F9Wsx8uHox74Xd2FFz3VcFrL1Cpqvyh4m\n"
                    + "0DwxyA==\n"
                    + "-----END CERTIFICATE-----";
            certWriter.println(cert);
            certWriter.close();

            PrintWriter keyWriter = new PrintWriter("/tmp/key.pem", "UTF-8");
            String privateKey = "-----BEGIN PRIVATE KEY-----\n"
                    + "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC6Zxp87VJOeH2M\n"
                    + "CyduKa+r52uAwxeEMYOuPdqhz2yZMeBohfDXl5wlnbq4+haTDCR9u3zXy6/nMZcN\n"
                    + "hO2M+f87xmzf6uDOg4TNvom2tzt+z/TpUYwBb6bHCwfTonBHt/RGqGvqfZiD+tIa\n"
                    + "WDYs7ChxfevNnGGAkP+nRk6FQH7S5a4ClhsC9x+Yx1sBEuewjADOF0rxjN76u1nN\n"
                    + "5TBaJ+zvER4BoijfNQL+u0aB3xnzRnCv92X9EmRp0V6JuVyodXBwWn18YikBZ3fG\n"
                    + "3k2iRHVHCLd5RXplDhKI3rZfqElkREzRwaNYlgu/MzlLPjK0S0oMo1Jim+KHrCFh\n"
                    + "77+xWjJVAgMBAAECggEAILM/sVTM0JlQpQW76BaGC+kwMcmH0ZgCGsodFa/WxFU8\n"
                    + "ofmFU7z0FqFCBXtSBH/n0urWLuE7vZo0fpmoNsgQn4LizLFeuLvXy2Q2sogYdtmy\n"
                    + "hE/a+NGGd3qW5+iiO+KohtC7Bcr8u4J/40FAV8ciHS7S9CIP9Mb7InQyCCxVU1fM\n"
                    + "6FpUg8iOPqmbnmBLHQQ9ZNpEtsI7+X6VgkmEUbsuqInWieewLQhkqyNecZt9GjhM\n"
                    + "aTvLWlkz8K4Urbr02wVzPWkTWHXeH0qVNRayMfkBn/IT/ko5jIeE0BCrQQ1mOqFw\n"
                    + "VknGAUR7fPvcEo9GsrBZ2BQp8zjfddEyEqz2isHnrQKBgQDjEHz6Y6GzJvAer9hL\n"
                    + "KsOUVK6SyyQij4GEEoyqTd/CHW+2wzkaelY3B3IVV5YQcmfqLyRo4uqyBLj2DvaC\n"
                    + "z20Hv2agX3GnASxlJdBywsKxjBDiKvq7r09tWahpQ5HH6Y9Bxy11+n9N4aIQ/qHd\n"
                    + "VahDT5vZQqWsOrI2GicwvvoBVwKBgQDSKBygUbuVHv1fRr5JIIGVk5R181pQKg+p\n"
                    + "ZtfabuPNe7E7siXDt8GoBAWIbcbwe21f1h+M/HvE98mqSCKEkSB+5Op+sL2uqhC1\n"
                    + "35p2dS3UAiOWrwksVZLLZz8iTA7crtdEijsxa03shqy46Xi+3rayvSmhLIvfn5fh\n"
                    + "npm0VrjCMwKBgQCijj1K7GTo4ulorq8EMRtPZ8iK4s99xosMSFNX4OReDcDacKzC\n"
                    + "3e42+yebS9bs81qddx5z0HAA/gyEa3XjdCg0MbyA95Nf4iEsUIQIhJHplcLIh8h0\n"
                    + "8S7FtDgzE+Kb4gNjLfrHyYu17+Cclg6/bAFIHwgN4/7DbEoFHXgNF8vXhQKBgQCw\n"
                    + "V5gtrmeIqYhR+jqRggU6Tau1jY2q1qBttS5Ky+G6+p+tR78Ii5ikMzuxTuwvvmH4\n"
                    + "mOPAmdwBWyqNCBpuwphR4iQiSovflDSRaB9kZs8dpu3V8unvmZ6jru77CHcwFkXr\n"
                    + "7VHeFfw9DITCZKY9AwSq1q+n3olAC9qWJ08yU9KUoQKBgQC8G3BURDBVX0sck70Z\n"
                    + "BW1H1Wx8Wv8QjgG4EWfovnucsOBRxj6DqugbqeXUctPJvca1PWRnzdZVwaxIyQdP\n"
                    + "8qDYFtA2OgM1+2IzNXcmL7qDIKopkvsGJkSnQzNMghGdr2sPzb4Ep40tggxtM83j\n"
                    + "ljrx4ZMzcKCd9RFHaY7iorKTuQ==\n"
                    + "-----END PRIVATE KEY-----";
            keyWriter.println(privateKey);
            keyWriter.close();

            Runtime r = Runtime.getRuntime();
            Process p = r.exec("openssl pkcs12 -export -password pass:123qweasd -out /tmp/store.pkcs12 -inkey /tmp/key.pem -certfile /tmp/ca.pem -in /tmp/cert.pem -caname 'CA Root' -name client");
            p.waitFor();

            p = r.exec("keytool -importkeystore -noprompt -srckeystore /tmp/store.pkcs12 -destkeystore /tmp/keystore.jks -srcstoretype pkcs12 -srcstorepass 123qweasd -srckeypass 123qweasd -destkeypass 123qweasd -deststorepass 123qweasd -alias client");
            p.waitFor();

            p = r.exec("keytool -noprompt -keystore /tmp/truststore.jks -alias CARoot -import -file /tmp/ca.pem -storepass 123qweasd");
            p.waitFor();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
