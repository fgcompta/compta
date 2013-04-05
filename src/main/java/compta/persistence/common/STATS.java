package compta.persistence.common;

import compta.persistence.util.IEnum;

public enum STATS implements IEnum {

	SALES(1, "enum.stats.sales"),
	//
	ARTICLES(2, "enum.stats.articles"),
	//
	NB_SALES_BY_ARTICLES(3, "enum.stats.nbSalesByArt"),
	//
	CA(4, "enum.stats.ca");

	private String	key;

	private Integer	id;

	private STATS(final Integer id, final String key) {
		this.key = key;
		this.id = id;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getKey() {
		return key;
	}
}
