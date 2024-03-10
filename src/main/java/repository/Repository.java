package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database.dbcontext.DbContext;
import domain.Identidad;

public class Repository<T> {
    private final DbContext dbContext = DbContext.getInstance();
    private final Connection connection;

    public Repository() {
        this.connection = dbContext.getConnection();
    }

    // Método para agregar un nuevo registro
    public void add(String tableName, String[] columns, Object[] values) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        for (String column : columns) {
            sql.append(column).append(",");
        }
        sql.deleteCharAt(sql.length() - 1); // Eliminar la última coma
        sql.append(") VALUES (");
        for (int i = 0; i < values.length; i++) {
            sql.append("?,");
        }
        sql.deleteCharAt(sql.length() - 1); // Eliminar la última coma
        sql.append(")");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            statement.executeUpdate();
        }
    }

    // Método para actualizar un registro existente
    public void update(String tableName, String[] columns, Object[] values, String condition) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        for (int i = 0; i < columns.length; i++) {
            sql.append(columns[i]).append("=?,");
        }
        sql.deleteCharAt(sql.length() - 1); // Eliminar la última coma
        sql.append(" WHERE ").append(condition);

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            statement.executeUpdate();
        }
    }

    // Método para eliminar un registro
    public void delete(String tableName, String condition) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + condition;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

}
