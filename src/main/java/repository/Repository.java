package repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.dbcontext.DbContext;
import domain.Interface.Identidad;


/**
 * La clase Repository proporciona métodos genéricos para realizar operaciones CRUD en la base de datos.
 * @param <T> El tipo de la entidad con la que se trabaja.
 */
public class Repository<T> {
    private DbContext db = new DbContext();

    public Repository() {
        db.getInstance();
    }

    
    /**
     * Guarda una nueva entidad en la base de datos.
     * @param Entity La entidad a guardar.
     * @return true si la operación fue exitosa, false si no.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public boolean Save(T Entity) throws SQLException {
        Qry<T> qry = new Qry<>(Entity);
        String sentencia = qry.getInsertSql();
        Connection conn = db.connect();
        PreparedStatement sentenciaPreparada = conn.prepareStatement(sentencia);
        int i = 1;
        for (Map.Entry<String, Object> entry : qry.getValues().entrySet()) {
            sentenciaPreparada.setObject(i++, entry.getValue());
        }
        int rowsAffected = sentenciaPreparada.executeUpdate();
        return rowsAffected > 0;
    }
    
    
    /**
     * Obtiene una entidad de la base de datos.
     * @param entityClass La clase de la entidad.
     * @param queryParams Los parámetros de la consulta.
     * @return La entidad encontrada, o null si no se encuentra.
     * @throws SQLException Si ocurre un error de SQL.
     * @throws InstantiationException Si hay un error al instanciar la entidad.
     * @throws IllegalAccessException Si hay un error de acceso ilegal.
     * @throws IllegalArgumentException Si hay un error de argumento ilegal.
     * @throws InvocationTargetException Si ocurre un error de invocación.
     * @throws NoSuchMethodException Si no se encuentra el método.
     * @throws SecurityException Si ocurre un error de seguridad.
     * @throws NoSuchFieldException Si no se encuentra el campo.
     */
    public T Get(Class<T> entityClass, Map<String, Object> queryParams) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
 
        String tableName = entityClass.getSimpleName();
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
        List<String> conditions = new ArrayList<>();
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            if (entry.getKey().equals("id")) {
                if ((Integer) entry.getValue() != 0) {
                    conditions.add(entry.getKey() + " = ?");
                }
            } else {
                conditions.add(entry.getKey() + " = ?");
            }
        }
        query.append(String.join(" AND ", conditions));

        try (Connection conn = db.connect();
             PreparedStatement statement = conn.prepareStatement(query.toString())) {
            int parameterIndex = 1;
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                if (!entry.getKey().equals("id") || (Integer) entry.getValue() != 0) {
                    statement.setObject(parameterIndex++, entry.getValue());
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    T resultInstance = entityClass.getDeclaredConstructor().newInstance();
                    for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                        Field field = entityClass.getDeclaredField(entry.getKey());
                        field.setAccessible(true);
                        field.set(resultInstance, resultSet.getObject(entry.getKey()));
                    }
                    return resultInstance;
                } else {
                    return null;
                }
            }
        }
    }

    
    /**
     * Busca entidades en la base de datos.
     * @param entityClass La clase de la entidad.
     * @param queryParams Los parámetros de la consulta.
     * @return Una lista de entidades encontradas.
     * @throws SQLException Si ocurre un error de SQL.
     * @throws InstantiationException Si hay un error al instanciar la entidad.
     * @throws IllegalAccessException Si hay un error de acceso ilegal.
     * @throws IllegalArgumentException Si hay un error de argumento ilegal.
     * @throws InvocationTargetException Si ocurre un error de invocación.
     * @throws NoSuchMethodException Si no se encuentra el método.
     * @throws SecurityException Si ocurre un error de seguridad.
     * @throws NoSuchFieldException Si no se encuentra el campo.
     */
    public List<T> Find(Class<T> entityClass, Map<String, Object> queryParams) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {

        String tableName = entityClass.getSimpleName();

        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName);
        if (!queryParams.isEmpty()) {
            query.append(" WHERE ");
            List<String> conditions = new ArrayList<>();
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                conditions.add(entry.getKey() + " = ?");
            }
            query.append(String.join(" AND ", conditions));
        }

        List<T> resultList = new ArrayList<>();
        try (Connection conn = db.connect();
             PreparedStatement statement = conn.prepareStatement(query.toString())) {
            int parameterIndex = 1;
            for (Object value : queryParams.values()) {
                statement.setObject(parameterIndex++, value);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T resultInstance = entityClass.getDeclaredConstructor().newInstance();
                    for (Field field : entityClass.getDeclaredFields()) {
                        field.setAccessible(true);
                        int columnType = resultSet.getMetaData().getColumnType(resultSet.findColumn(field.getName()));
                        if (columnType == Types.DOUBLE || columnType == Types.REAL) {
                            field.set(resultInstance, resultSet.getDouble(field.getName()));
                        } else if (columnType == Types.DECIMAL || columnType == Types.NUMERIC) {
                            field.set(resultInstance, resultSet.getBigDecimal(field.getName()).doubleValue());
                        } else {
                            field.set(resultInstance, resultSet.getObject(field.getName()));
                        }
                    }
                    resultList.add(resultInstance);
                }
            }
        }

        return resultList;
    }



}
