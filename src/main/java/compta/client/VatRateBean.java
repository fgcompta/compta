package compta.client;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.google.common.base.Strings;
import compta.client.util.BeanUtils;
import compta.client.util.EntityBean;
import compta.persistence.dao.ArticleDAO;
import compta.persistence.dao.VatRateDAO;
import compta.persistence.entity.VatRate;

@ManagedBean(name = "vatRateBean")
@RequestScoped
public class VatRateBean extends EntityBean<VatRate, Integer> implements Serializable {

	private static final long	serialVersionUID	= 6248557164201730174L;
	private VatRateDAO			rateDao;
	private final ArticleDAO	articleDao;

	public VatRateBean() {
		super();
		rateDao = new VatRateDAO();
		articleDao = new ArticleDAO();
	}

	@Override
	public String checkBefore(final VatRate rate) {
		if (rate.getRate() == null) {
			return "rate.needed";
		} else if (Strings.isNullOrEmpty(rate.getName())) {
			return "name.needed";
		} else if (existName(rate)) {
			return "nameOrRef.alreadyExist";
		}
		return null;
	}

	private boolean existName(final VatRate rate) {
		for (final VatRate v : getElements()) {
			if (v.getName().equals(rate.getName()) && !v.getPrimaryKey().equals(rate.getPrimaryKey())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public VatRateDAO getDao() {
		if (rateDao == null) {
			rateDao = new VatRateDAO();
		}
		return rateDao;
	}

	@Override
	public VatRate newElement() {
		return new VatRate();
	}

	@Override
	protected String getMsgKey() {
		return "rate";
	}

	@Override
	public boolean checkBeforeDelete(final VatRate entity) {
		if (entity == null) {
			return false;
		}
		return !articleDao.isReference(entity);
	}

	@Override
	public void deleteElement(final VatRate entity) {
		getDao().delete(entity);
	}

	@Override
	public void reloadOthersBean() {
		BeanUtils.getArticlesBean().init();
	}
}
