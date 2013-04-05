package compta.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import compta.persistence.util.IEntity;
import compta.persistence.util.hibernate.HibernateUtil;

public abstract class GenericHibernateDAO<T extends IEntity<ID>, ID extends Serializable> implements IGenericDAO<T, ID> {

	private final Class<T>	persistentClass;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		this.persistentClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	protected Session getSession() {
		final Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!s.getTransaction().isActive()) {
			s.beginTransaction();
			s.setFlushMode(FlushMode.COMMIT);
		}
		return s;
	}

	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(final ID id, final boolean lock) {
		T entity;
		if (lock) {
			entity = (T) getSession().load(getPersistentClass(), id, LockOptions.UPGRADE);
		} else {
			entity = (T) getSession().load(getPersistentClass(), id);
		}

		return entity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getById(final ID id, final boolean lock) {
		T entity;
		if (lock) {
			entity = (T) getSession().get(getPersistentClass(), id, LockOptions.UPGRADE);
		} else {
			entity = (T) getSession().get(getPersistentClass(), id);
		}

		return entity;
	}

	@Override
	public List<T> findAll() {
		return findByCriteria();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(final T exampleInstance, final String[] excludeProperty) {
		final Criteria crit = getSession().createCriteria(getPersistentClass());
		final Example example = Example.create(exampleInstance);
		for (final String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	@Override
	public T saveOrUpdate(final T entity) {
		try {
			getSession().saveOrUpdate(entity);
			commitAndBeginNewTransaction();
		}
		catch (final HibernateException ex) {
			throw ex;
		}
		return getById(entity.getPrimaryKey(), false);
	}

	public void commitAndBeginNewTransaction() {
		getSession().getTransaction().commit();
	}

	@Override
	public void delete(final T entity) {
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(final Criterion... criterion) {
		final Criteria crit = getSession().createCriteria(getPersistentClass());
		for (final Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	protected Query createQuery(final String hqlQuery) {
		return getSession().createQuery(hqlQuery);
	}

	/**
	 * Method used to suppress warnings
	 * 
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <X> List<X> list(final Query query) {
		return query.list();
	}

	@Override
	public void delete(final ID primaryKey) {
		delete(this.findById(primaryKey, false));
	}

}
