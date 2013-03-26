package compta.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import lombok.Data;

import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;


@Entity
@Table(name = "article_stock")
@Data
public class ArticleStock implements IEntity<Integer>
{

    private static final long serialVersionUID = 1490008209973573530L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer primaryKey;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "article_id")
    private Article article;

    @Column(name = "quantity_init")
    @Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
    private BigDecimal quantityInit;

    @Column(name = "date_entry")
    @Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
    private BigDecimal entryDate;

    @Column(nullable = false, name = "date_cancel")
    @Temporal(TemporalType.DATE)
    private Date cancelDate;

    @Column(name = "invoice_supplier", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String supplier;

    @Column(name = "unit_price")
    @Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
    private BigDecimal unitPrice;

}