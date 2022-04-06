import com.google.gson.Gson;
import model.User;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.*;

public class App {

    private static HashMap<String, String> listLogin = new HashMap<>();

    public static void main(String[] args) {
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        //secure(getKeyStore(), "123456", null, null);
        port(getPort());
        listLogin.put("frailejon", "b514d54458eddda4dd3d2e88eef7ef95f5b68245dd861de7d487ce974a152f3d");
        listLogin.put("juan", "8616a7c06288f4a48fd2504aeacba5160906e3ba1ccea699515290e7d40d92c1");

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
            System.out.println(user.getName()+" "+user.getPassword());
            System.out.println(validateLogin(user));
            return "asdasd";
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

    private static Boolean validateLogin(User user) throws NoSuchAlgorithmException {
        String r1 = HashSha.toHexString(HashSha.getSHA(user.getPassword()));
        String r2 = listLogin.get(user.getName());

        if (r1.equals(r2)) {
            System.out.println("2");
            return true;
        }
        return false;
    }
}
