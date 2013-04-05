package compta.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import compta.persistence.util.IEntity;
import compta.persistence.util.hibernate.HibernateUtil;

@SuppressWarnings("rawtypes")
public class GenericDAOBase {

	protected Session getSession() {
		final Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!s.getTransaction().isActive()) {
			s.beginTransaction();
		}
		return s;
	}

	public IEntity findById(final Serializable id, final boolean lock, final Class clazz) {
		if (lock) {
			return (IEntity) getSession().load(clazz, id, LockOptions.UPGRADE);
		} else {
			return (IEntity) getSession().load(clazz, id);
		}

	}

	public IEntity getById(final Serializable id, final boolean lock, final Class clazz) {
		if (lock) {
			return (IEntity) getSession().get(clazz, id, LockOptions.UPGRADE);
		} else {
			return (IEntity) getSession().get(clazz, id);
		}

	}

	public List findAll(final Class clazz) {
		return findByCriteria(clazz);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	protected List<?> findByCriteria(final Class clazz, final Criterion... criterion) {
		final Criteria crit = getSession().createCriteria(clazz);
		for (final Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	public Object saveOrUpdate(final Object entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(final Object entity) {
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	public void delete(final Serializable primaryKey, final Class clazz) {
		delete(this.findById(primaryKey, false, clazz));
	}

}
