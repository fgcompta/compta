package compta.persistence.dao;

import org.hibernate.Query;

import compta.persistence.entity.InvoicePaymentMean;
import compta.persistence.entity.PaymentMean;

public class InvoicePaymentMeanDAO extends GenericHibernateDAO<InvoicePaymentMean, Integer> {

	public boolean isReference(final PaymentMean entity) {
		final String hqlQuery = "SELECT count(i) FROM InvoicePaymentMean i WHERE i.paymentMean = :paymentMean ";
		final Query q = createQuery(hqlQuery);
		q.setParameter("paymentMean", entity);
		final Long c = (Long) q.uniqueResult();
		return c > 0;
	}

}
