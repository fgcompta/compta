package compta.persistence.dao;

import org.hibernate.Query;

import compta.persistence.entity.Article;
import compta.persistence.entity.VatRate;

public class ArticleDAO extends GenericHibernateDAO<Article, Integer> {

	public boolean isReference(final VatRate entity) {
		final String hqlQuery = "SELECT count(a) FROM Article a WHERE a.rate = :rate ";
		final Query q = createQuery(hqlQuery);
		q.setParameter("rate", entity);
		final Long c = (Long) q.uniqueResult();
		return c > 0;
	}

}
