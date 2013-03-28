package compta.client;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.RowEditEvent;

import com.google.inject.Inject;
import compta.persistence.dao.ArticleDAO;
import compta.persistence.entity.Article;

@ManagedBean
@SessionScoped
public class ArticlesBean {
	@Getter
	@Setter
	private List<Article>	filteredArticles;
	@Getter
	@Setter
	private List<Article>	articles;

	@Getter
	@Setter
	private Article			selectedArticle;
	@Getter
	@Setter
	private Article[]		selectedArticles;

	@Inject
	private ArticleDAO		articleDao;

	public ArticlesBean() {
		articles = new ArrayList<Article>();
		System.out.println("Get datas");
		articles = articleDao.findAll();
	}

	public void onEdit(final RowEditEvent event) {
		final FacesMessage msg = new FacesMessage("Article Edited", ((Article) event.getObject()).getName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(final RowEditEvent event) {
		final FacesMessage msg = new FacesMessage("Article Cancelled", ((Article) event.getObject()).getName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
