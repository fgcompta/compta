package compta.persistence.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T, ID extends Serializable> {

	T findById(final ID id, final boolean lock);

	T getById(final ID id, final boolean lock);

	List<T> findAll();

	List<T> findByExample(final T exampleInstance, final String[] excludeProperty);

	T saveOrUpdate(final T entity);

	void delete(final T entity);

	void delete(final ID primaryKey);
}