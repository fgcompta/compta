package compta.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;

import com.google.common.base.Strings;
import compta.client.util.EntityBean;
import compta.common.NumberUtil;
import compta.persistence.dao.ArticleDAO;
import compta.persistence.dao.ArticleStockDAO;
import compta.persistence.dao.InvoiceLineDAO;
import compta.persistence.dao.VatRateDAO;
import compta.persistence.entity.Article;
import compta.persistence.entity.VatRate;

@ManagedBean(name = "articlesBean")
@RequestScoped
public class ArticlesBean extends EntityBean<Article, Integer> implements Serializable {
	private static final long		serialVersionUID	= -31889872039615330L;

	@Getter
	@Setter
	private List<VatRate>			rates;

	private ArticleDAO				articleDao;

	private final ArticleStockDAO	articleStockDao;

	private final VatRateDAO		rateDao;
	private final InvoiceLineDAO	invoiceLineDao;

	public ArticlesBean() {
		super();
		articleDao = new ArticleDAO();
		rateDao = new VatRateDAO();
		invoiceLineDao = new InvoiceLineDAO();
		articleStockDao = new ArticleStockDAO();
	}

	@Override
	public void init() {
		super.init();
		rates = rateDao.findAll();
	}

	@Override
	public String checkBefore(final Article art) {
		if (art.getRate() == null) {
			return "rate.needed";
		} else if (Strings.isNullOrEmpty(art.getName())) {
			return "name.needed";
		} else if (art.getPriceHT() == null || NumberUtil.compare(art.getPriceHT(), BigDecimal.ZERO, true)) {
			return "price.needed";
		} else if (existRefName(art)) {
			return "nameOrRef.alreadyExist";
		}
		return null;
	}

	private boolean existRefName(final Article art) {
		for (final Article a : getElements()) {
			if ((a.getName().equals(art.getName()) || a.getReference().equals(art.getReference()))
					&& !a.getPrimaryKey().equals(art.getPrimaryKey())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public ArticleDAO getDao() {
		if (articleDao == null) {
			articleDao = new ArticleDAO();
		}
		return articleDao;
	}

	@Override
	public Article newElement() {
		return new Article();
	}

	@Override
	protected String getMsgKey() {
		return "article";
	}

	@Override
	public boolean checkBeforeDelete(final Article entity) {
		if (entity == null) {
			return false;
		}
		return !invoiceLineDao.isReference(entity);
	}

	@Override
	public void deleteElement(final Article entity) {
		articleStockDao.deleteFromArticle(entity);
		getDao().delete(entity);
	}

	@Override
	public void reloadOthersBean() {
	}
}
