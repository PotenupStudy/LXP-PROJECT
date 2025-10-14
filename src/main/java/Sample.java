import config.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import service.CategoryService;
import utill.QueryUtil;

public class Sample {
    public static void main(String[] args) {
        CategoryService categoryService = new CategoryService();
        categoryService.selectCategory();
    }
}
