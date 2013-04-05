package compta.client.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.RowEditEvent;

import compta.persistence.dao.GenericHibernateDAO;
import compta.persistence.entity.Article;
import compta.persistence.util.IEntity;

public abstract class EntityBean<T extends IEntity<ID>, ID extends Serializable> implements Serializable {
	private static final long	serialVersionUID	= 5446040389468966864L;
	@Getter
	@Setter
	private List<T>				filteredElements;
	@Getter
	@Setter
	private List<T>				elements;
	@Getter
	@Setter
	private T					element;
	@Getter
	@Setter
	private boolean				showCreate;
	@Getter
	@Setter
	private T					selectedElement;

	public EntityBean() {
		init();
	}

	public void init() {
		elements = new ArrayList<T>();
		elements = getDao().findAll();
		showCreate = false;
		this.element = newElement();
	}

	@SuppressWarnings("unchecked")
	public void onEdit(final RowEditEvent event) {
		createOrUpdate("crud.edited", (T) event.getObject());
	}
	private void createOrUpdate(final String defaultMsg, T entity) {
		String msg = checkBefore(entity);
		Severity sev = FacesMessage.SEVERITY_ERROR;
		if (msg == null || msg.isEmpty()) {
			entity = getDao().saveOrUpdate(entity);
			msg = defaultMsg;
			sev = FacesMessage.SEVERITY_INFO;
		}
		final FacesMessage fMsg = new FacesMessage(sev, getMsg(msg), entity.getName());
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		elements = getDao().findAll();
		reloadOthersBean();
	}

	public abstract void reloadOthersBean();

	private String getMsg(final String key) {
		return MessagesReader.getMessages(key, new String[]{MessagesReader.getMessages(getMsgKey())});
	}

	protected abstract String getMsgKey();

	public void onCancel(final RowEditEvent event) {
		final FacesMessage msg = new FacesMessage(getMsg("crud.cancelled"), ((Article) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		elements = getDao().findAll();
	}

	public abstract String checkBefore(final T entity);
	public abstract boolean checkBeforeDelete(final T entity);
	public abstract GenericHibernateDAO<T, ID> getDao();

	public void onNewElement(final ActionEvent actionEvent) {
		this.element = newElement();
		this.showCreate = true;
	}

	public void onCreate(final ActionEvent actionEvent) {
		createOrUpdate("crud.created", element);
		this.showCreate = false;
	}

	public void onDelete() {
		final boolean canDelete = checkBeforeDelete(selectedElement);
		String msg = "crud.deleted.error";
		Severity sev = FacesMessage.SEVERITY_ERROR;
		if (canDelete) {
			deleteElement(selectedElement);
			getDao().commitAndBeginNewTransaction();
			msg = "crud.deleted.success";
			sev = FacesMessage.SEVERITY_INFO;
		}
		final FacesMessage fMsg = new FacesMessage(sev, getMsg(msg), selectedElement == null
				? ""
				: selectedElement.getName());
		FacesContext.getCurrentInstance().addMessage(null, fMsg);
		elements = getDao().findAll();
		selectedElement = null;
	}
	public abstract T newElement();

	public abstract void deleteElement(final T entity);
}
