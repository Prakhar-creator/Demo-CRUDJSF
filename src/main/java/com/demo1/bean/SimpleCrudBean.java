package com.demo1.bean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
  
@ManagedBean
@SessionScoped
public class SimpleCrudBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "1234";
 
    private List<Student> list;
    private Student item;
    private Student beforeEditItem;
    private boolean edit;
 
    @PostConstruct
    public void init() {
        list = fetchStudentsFromDatabase();
        item = new Student();
    }
    
    public void add() {
         String sql = "INSERT INTO public.\"Students\" (\"Name\") VALUES (?)";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, item.getName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            item.setId(generatedId);
        }
    }
            resetSequence(connection);
}
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SimpleCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        item = new Student();
        list = fetchStudentsFromDatabase();
    }
 
    public void resetAdd() {
        item = new Student();
    }
 
    public void edit(Student selectedItem) {
        beforeEditItem = selectedItem.clone();
        this.item = selectedItem;
        edit = true;
    }
    
 
    public void cancelEdit() {
        this.item.restore(beforeEditItem);
        this.item = new Student();
        edit = false;
    }
 
    public void saveEdit() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.\"Students\" SET \"Name\" = ? WHERE \"id\"=? ");
            preparedStatement.setString(1, (item.getName()));
            preparedStatement.setInt(2, item.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(SimpleCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
 
        item = new Student();
        edit = false;
        list = fetchStudentsFromDatabase();
    }
 
    public void delete(Student selectedItem) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Students\" WHERE id = ? AND \"Students\".\"Name\" = ?");
            preparedStatement.setInt(1, selectedItem.getId());
            preparedStatement.setString(2, selectedItem.getName());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(SimpleCrudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        list = fetchStudentsFromDatabase();
    }
 
    public List<Student> getList() {
        return list;
    }
 
    public Student getItem() {
        return item;
    }
 
    public boolean isEdit() {
        return edit;
    }
 
    public void setEdit(boolean edit) {
        this.edit = edit;
    }
 
    private List<Student> fetchStudentsFromDatabase() {
        List<Student> students = new ArrayList<>();
 
        try {
            
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM public.\"Students\"");
            ResultSet resultSet = preparedStatement.executeQuery();
 
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                students.add(new Student(id, name));
            }
            
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

     private static void resetSequence(Connection connection) throws SQLException {
        String resetSequenceSql = "SELECT setval(pg_get_serial_sequence('public.\"Students\"', 'id'), 1)";
        try (PreparedStatement resetSequenceStatement = connection.prepareStatement(resetSequenceSql)) {
            resetSequenceStatement.execute();
            System.out.println("Sequence reset to 0");
        }
    }
}