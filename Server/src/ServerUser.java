import java.sql.SQLException;

public class ServerUser {
    public static void create (String email, String password){
        try {
            ServerUtils.dbWork("users", "CREATE_USER", email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
