package compta.persistence.common;

import compta.persistence.util.IEnum;

public enum STATS implements IEnum{

	SALES("enum.stats.sales"),
	//
	ARTICLES("enum.stats.articles"),
	//
	NB_SALES_BY_ARTICLES("enum.stats.nbSalesByArt"),
	//
	CA("enum.stats.ca");

	private String key;

	private STATS(String key) {
		this.key = key;
	}

	
	public String getKey() {
		return key;
	}
}
