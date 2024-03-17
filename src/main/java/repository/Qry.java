/**
 * 
 */
package repository;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * La clase Qry se encarga de construir consultas SQL genéricas para la inserción de entidades en la base de datos.
 * @param <T> El tipo de entidad con la que se trabaja.
 */
class Qry<T> {
    private T Entity;
    private Map<String, Object> values;

    public Qry(T Entity) {
        this.Entity = Entity;
        this.values = new HashMap<>();
        mapFields();
    }

    
    /**
     * Mapea los campos de la entidad y construye la consulta SQL de inserción.
     */
    private void mapFields() {
    	// Obtengo la clase
        Class<?> EntityClass = Entity.getClass();
        // Obtengo sus propiedades
        Field[] fields = EntityClass.getDeclaredFields();
        // Obtengo el nombre de base de datos
        String tableName = EntityClass.getSimpleName();
        
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        
        for (Field field : fields) {
            String columnName = field.getName();
            columns.append(columnName).append(", ");
            placeholders.append("?, ");
            field.setAccessible(true);
            try {
                values.put(columnName, field.get(Entity));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        columns.delete(columns.length() - 2, columns.length());
        placeholders.delete(placeholders.length() - 2, placeholders.length());
        String insertSql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    /**
     * Obtiene la consulta SQL de inserción.
     * @return La consulta SQL de inserción.
     */
    public String getInsertSql() {
        return "INSERT INTO " + Entity.getClass().getSimpleName() + " (" + String.join(", ", values.keySet()) + ") VALUES (" + String.join(", ", Collections.nCopies(values.size(), "?")) + ")";
    }

    /**
     * Obtiene los valores de la entidad para la consulta SQL de inserción.
     * @return Un mapa de los valores de la entidad.
     */
    public Map<String, Object> getValues() {
        return values;
    }
}