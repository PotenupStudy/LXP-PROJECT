import config.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import service.CategoryService;
import utill.QueryUtil;

public class Sample {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            Connection conn = JDBCConnection.getConnection();
            CategoryService categoryService = new CategoryService(conn);

        }catch(Exception e){

        }

    }
}
