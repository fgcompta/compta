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

import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;

@Entity
@Table(name = "invoice_line")
@Data
public class InvoiceLine implements IEntity<Integer> {
	private static final long	serialVersionUID	= -4281003926733923400L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer				primaryKey;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "article_id")
	private Article				article;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "invoice_id")
	private Invoice				invoice;

	@Column(name = "price_ht")
	@Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
	private BigDecimal			priceHT;

	@Column(name = "quantity")
	@Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
	private BigDecimal			quantity;

	@Column(name = "discount")
	@Digits(integer = EntityConstant.DECIMAL_ALL_PERCENT, fraction = EntityConstant.DECIMAL_PART)
	private BigDecimal			discount;

	@Column(name = "vat_rate")
	@Digits(integer = EntityConstant.DECIMAL_ALL_PERCENT, fraction = EntityConstant.DECIMAL_PART)
	private BigDecimal			vatRate;

	@Override
	public String getName() {
		return invoice.getName() + " " + article.getName();
	}
}