package co.edu.poli.ces3.universitas.repositories;

import co.edu.poli.ces3.universitas.dao.User;
import co.edu.poli.ces3.universitas.database.ConexionMySql;
import co.edu.poli.ces3.universitas.database.Crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends ConexionMySql implements Crud {
    @Override
    public void insert(User x) {

    }

    @Override
    public void update(User x) {

    }

    @Override
    public List<User> get() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> list = new ArrayList<>();
        try {
            this.createConexion();
            Statement stmt = this.getCnn().createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while(result.next()){
                list.add(new User(result.getInt("id"),
                        result.getString("name"),
                        result.getString("lastName"),
                        result.getString("mail"),
                        result.getString("password"),
                        result.getDate("createdAt"),
                        result.getDate("updatedAt"),
                        result.getDate("deletedAt")
                ));
            }
            stmt.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(this.getCnn() != null)
                this.getCnn().close();
        }
    }

    @Override
    public User getOne(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            createConexion();
            PreparedStatement stm = super.getCnn().prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();
            if(result.next())
                return new User(result.getString("name"), result.getString("lastName"));
        } catch (SQLException error) {
            error.printStackTrace();
        } finally {
            if (getCnn() != null)
                getCnn().close();
        }
        return null;
    }

}
