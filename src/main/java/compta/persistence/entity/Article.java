package compta.persistence.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.Data;

import compta.common.NumberUtil;
import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;

@Entity
@Table(name = "article")
@Data
public class Article implements IEntity<Integer> {

	private static final long	serialVersionUID	= 5766021907013418911L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer				primaryKey;

	@Column(name = "article_name", length = EntityConstant.MAX_SIZE_VARCHAR)
	private String				name;

	@Column(name = "reference", length = EntityConstant.AVG_SIZE_VARCHAR)
	private String				reference;

	@Column(name = "price_ht")
	@Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
	private BigDecimal			priceHT;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "vat_rate_id")
	private VatRate				rate;

	@Column(name = "display")
	private Boolean				display;

	@Column(name = "decimal_quantity")
	private Boolean				decimalQuantity;

	public BigDecimal getPriceTTC() {
		return NumberUtil.addVat(priceHT, getTax());
	}

	private BigDecimal getTax() {
		BigDecimal tax = BigDecimal.ZERO;
		if (rate != null) {
			tax = rate.getRate();
		}
		return tax;
	}

	public void setPriceTTC(final BigDecimal priceTTC) {
		if (priceTTC == null) {
			priceHT = BigDecimal.ZERO;
		} else {
			priceHT = NumberUtil.retainVat(priceTTC, getTax());
		}
	}
}