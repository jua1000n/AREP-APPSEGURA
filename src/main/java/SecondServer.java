import java.security.KeyStoreException;

import static spark.Spark.*;

public class SecondServer {

    public static void main(String[] args) {
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        secure(getKeyStore(), "123456", null, null);
        port(getPort());
        try {
            SecureURLReader.secureURL();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        get("/hello", (req, res) -> "Se logeo de manera correta, se ecnuentra en el otro servidor");

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }

    private static String getKeyStore() {
        if (System.getenv("KEYSTORE") != null) {
            return System.getenv("KEYSTORE");
        }
        return "keystores/ecikeystore.p12";
    }
}
