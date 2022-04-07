import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.User;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static spark.Spark.*;

public class App {

    private static HashMap<String, String> listLogin = new HashMap<>();

    public static void main(String[] args) {
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        secure(getKeyStore(), "123456", null, null);
        port(getPort());
        try {
            SecureURLReader.secureURL();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        listLogin.put("frailejon", "b514d54458eddda4dd3d2e88eef7ef95f5b68245dd861de7d487ce974a152f3d");
        listLogin.put("juan", "8616a7c06288f4a48fd2504aeacba5160906e3ba1ccea699515290e7d40d92c1");//cadavid

        staticFileLocation("/");

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        get("/", (req, res) -> {
            System.out.println("entro /");
            res.redirect("/login.html");
            res.status(200);
            return "Hello";
        });

        post("/login", (req, res) -> {
            res.type("application/json");
            User user = (new Gson()).fromJson(req.body(), User.class);
            return validateLogin(user);
        });

        get("/hello", (req, res) -> "Hello World");
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    private static String getKeyStore() {
        if (System.getenv("KEYSTORE") != null) {
            return System.getenv("KEYSTORE");
        }
        return "keystores/ecikeystore.p12";
    }

    private static JsonObject validateLogin(User user) throws NoSuchAlgorithmException {
        String r1 = HashSha.toHexString(HashSha.getSHA(user.getPassword()));
        String r2 = listLogin.get(user.getName());
        JsonObject res = new JsonObject();
        if (r1.equals(r2)) {
            res.addProperty("status",202);
            res.addProperty("result", "OK");
            res.addProperty("server", "Se logeo de manera correcta");
            System.out.println(res.toString());
            return res;
        }

        res.addProperty("status",406);
        res.addProperty("result", "Not Acceptable");
        res.addProperty("server", "El usuario o la password es incorrecta");
        System.out.println(res.toString());
        return res;
    }
}
