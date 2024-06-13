package co.edu.poli.ces3.universitas.database;

import co.edu.poli.ces3.universitas.dao.User;

import java.sql.SQLException;
import java.util.List;

public interface Crud<E> {
    public void insert(E x);

    public void update(E x);

    public List<E> get() throws SQLException;

    public E getOne(int id) throws SQLException;
}
