package compta.client;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.google.common.base.Strings;
import compta.client.util.EntityBean;
import compta.persistence.dao.InvoicePaymentMeanDAO;
import compta.persistence.dao.PaymentMeanDAO;
import compta.persistence.entity.PaymentMean;

@ManagedBean(name = "paymentMeanBean")
@RequestScoped
public class PaymentMeanBean extends EntityBean<PaymentMean, Integer> implements Serializable {

	private static final long			serialVersionUID	= 6248557164201730174L;
	private PaymentMeanDAO				paymentMeanDao;
	private final InvoicePaymentMeanDAO	invoicePaymentMeanDao;

	public PaymentMeanBean() {
		super();
		paymentMeanDao = new PaymentMeanDAO();
		invoicePaymentMeanDao = new InvoicePaymentMeanDAO();
	}

	@Override
	public String checkBefore(final PaymentMean rate) {
		if (Strings.isNullOrEmpty(rate.getName())) {
			return "name.needed";
		} else if (existName(rate)) {
			return "nameOrRef.alreadyExist";
		}
		return null;
	}

	private boolean existName(final PaymentMean rate) {
		for (final PaymentMean v : getElements()) {
			if (v.getName().equals(rate.getName()) && !v.getPrimaryKey().equals(rate.getPrimaryKey())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public PaymentMeanDAO getDao() {
		if (paymentMeanDao == null) {
			paymentMeanDao = new PaymentMeanDAO();
		}
		return paymentMeanDao;
	}

	@Override
	public PaymentMean newElement() {
		return new PaymentMean();
	}

	@Override
	protected String getMsgKey() {
		return "payment";
	}

	@Override
	public boolean checkBeforeDelete(final PaymentMean entity) {
		if (entity == null) {
			return false;
		}
		return !invoicePaymentMeanDao.isReference(entity);
	}

	@Override
	public void deleteElement(final PaymentMean entity) {
		getDao().delete(entity);
	}

	@Override
	public void reloadOthersBean() {
	}
}
