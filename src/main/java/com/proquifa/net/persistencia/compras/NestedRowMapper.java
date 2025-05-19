/**
 * 
 */
package com.proquifa.net.persistencia.compras;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author ymendez
 *
 */
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class NestedRowMapper<T> implements RowMapper<T> {

	private Class<T> mappedClass;

	public NestedRowMapper(Class<T> mappedClass) {
		this.mappedClass = mappedClass;
	}

	@SuppressWarnings("deprecation")
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {

		T mappedObject = BeanUtils.instantiate(this.mappedClass);
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);

		bw.setAutoGrowNestedPaths(true);

		ResultSetMetaData meta_data = rs.getMetaData();
		int columnCount = meta_data.getColumnCount();

		for (int index = 1; index <= columnCount; index++  ) {

			try {

				String column = JdbcUtils.lookupColumnName(meta_data, index);
				Object value = JdbcUtils.getResultSetValue(rs, index, Class.forName(meta_data.getColumnClassName(index)));

				bw.setPropertyValue(column, value);

			} catch (TypeMismatchException | NotWritablePropertyException | ClassNotFoundException e) {
				// Ignore
			}
		}

		return mappedObject;
	}
}
