package compta.persistence.dao;

import org.hibernate.Query;

import compta.persistence.entity.Customer;
import compta.persistence.entity.Invoice;

public class InvoiceDAO extends GenericHibernateDAO<Invoice, Integer> {

	public boolean isReference(final Customer entity) {
		final String hqlQuery = "SELECT count(i) FROM Invoice i WHERE i.customer = :customer ";
		final Query q = createQuery(hqlQuery);
		q.setParameter("customer", entity);
		final Long c = (Long) q.uniqueResult();
		return c > 0;
	}

}
