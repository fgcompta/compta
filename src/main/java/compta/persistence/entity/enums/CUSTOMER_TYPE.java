package compta.persistence.entity.enums;

import compta.persistence.util.IEnum;

public enum CUSTOMER_TYPE implements IEnum {
	CLIENT(1, "enum.custType.client"),
	//
	COLLECTIVITE(2, "enum.custType.collectivite"),
	//
	SOCIETE(3, "enum.custType.societe");

	private Integer	id;

	private String	key;

	private CUSTOMER_TYPE(final int id, final String key) {
		this.id = id;
		this.key = key;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public CUSTOMER_TYPE valueOf(final Integer id) {
		for (final CUSTOMER_TYPE type : values()) {
			if (type.id.equals(id)) {
				return type;
			}
		}
		return null;
	}

	@Override
	public String getKey() {
		return this.key;
	}
}
