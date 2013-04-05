package compta.persistence.dao;

import org.hibernate.Query;

import compta.persistence.entity.Article;
import compta.persistence.entity.InvoiceLine;

public class InvoiceLineDAO extends GenericHibernateDAO<InvoiceLine, Integer> {

	public boolean isReference(final Article entity) {
		final String hqlQuery = "SELECT count(i) FROM InvoiceLine i WHERE i.article = :article ";
		final Query q = createQuery(hqlQuery);
		q.setParameter("article", entity);
		final Long c = (Long) q.uniqueResult();
		return c > 0;
	}

}
