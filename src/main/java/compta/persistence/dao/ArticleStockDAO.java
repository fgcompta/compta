package compta.persistence.dao;

import org.hibernate.Query;

import compta.persistence.entity.Article;
import compta.persistence.entity.ArticleStock;

public class ArticleStockDAO extends GenericHibernateDAO<ArticleStock, Integer> {

	public void deleteFromArticle(final Article entity) {
		final String hqlQuery = "DELETE FROM ArticleStock WHERE article = :article ";
		final Query q = createQuery(hqlQuery);
		q.setParameter("article", entity);
		q.executeUpdate();
	}

}
