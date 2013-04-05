package compta.persistence.entity.enums;

import compta.persistence.util.IEnum;

public enum INVOICE_STATE implements IEnum {
	QUOTE(1, "enum.invoice.state.quote"),
	//
	INVOICE(2, "enum.invoice.state.invoice"),
	//
	INVOICE_CANCEL(3, "enum.invoice.state.invoice.cancel");

	private Integer	id;

	private String	key;

	private INVOICE_STATE(final int id, final String key) {
		this.id = id;
		this.key = key;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public INVOICE_STATE valueOf(final Integer id) {
		for (final INVOICE_STATE type : values()) {
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
