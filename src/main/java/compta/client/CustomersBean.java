package compta.client;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import compta.client.util.EntityBean;
import compta.persistence.dao.CustomerDAO;
import compta.persistence.dao.InvoiceDAO;
import compta.persistence.entity.Customer;
import compta.persistence.entity.enums.CUSTOMER_TYPE;

@ManagedBean(name = "customersBean")
@RequestScoped
public class CustomersBean extends EntityBean<Customer, Integer> implements Serializable {

	private static final long	serialVersionUID	= 8821524155348009532L;
	private CustomerDAO			customerDao;
	private final InvoiceDAO	invoiceDao;

	public CustomersBean() {
		super();
		customerDao = new CustomerDAO();
		invoiceDao = new InvoiceDAO();
	}

	@Override
	public String checkBefore(final Customer rate) {
		if (Strings.isNullOrEmpty(rate.getName())) {
			return "name.needed";
		} else if (existName(rate)) {
			return "nameOrRef.alreadyExist";
		}
		return null;
	}

	private boolean existName(final Customer rate) {
		for (final Customer v : getElements()) {
			if (v.getName().equals(rate.getName()) && !v.getPrimaryKey().equals(rate.getPrimaryKey())) {
				return true;
			}
		}
		return false;
	}
	@Override
	public CustomerDAO getDao() {
		if (customerDao == null) {
			customerDao = new CustomerDAO();
		}
		return customerDao;
	}

	@Override
	public Customer newElement() {
		return new Customer();
	}

	@Override
	protected String getMsgKey() {
		return "rate";
	}

	@Override
	public boolean checkBeforeDelete(final Customer entity) {
		if (entity == null) {
			return false;
		}
		return !invoiceDao.isReference(entity);
	}

	@Override
	public void deleteElement(final Customer entity) {
		getDao().delete(entity);
	}

	@Override
	public void reloadOthersBean() {
	}

	public List<CUSTOMER_TYPE> getTypess() {
		return Lists.newArrayList(CUSTOMER_TYPE.values());
	}
}
