package compta.client.util;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import compta.client.ArticlesBean;
import compta.client.VatRateBean;

public class BeanUtils {

	public static VatRateBean getVatRateBean() {
		return getBean("vatRateBean", VatRateBean.class);
	}

	public static ArticlesBean getArticlesBean() {
		return getBean("articlesBean", ArticlesBean.class);
	}

	@SuppressWarnings("rawtypes")
	private static <E extends EntityBean> E getBean(final String beanName, final Class<E> beanClass) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Application application = context.getApplication();
		return application.evaluateExpressionGet(context, "#{" + beanName + "}", beanClass);
	}

}
