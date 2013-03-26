package compta.persistence.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable>
{

	T findById(ID id, boolean lock);

	T getById(ID id, boolean lock);

	List<T> findAll();

	List<T> findByExample(T exampleInstance, String[] excludeProperty);

    T saveOrUpdate(T entity);

    void delete(T entity);

    void delete(ID primaryKey);
}