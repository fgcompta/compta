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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import compta.persistence.entity.enums.INVOICE_STATE;
import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;


@Entity
@Table(name = "invoice")
@Data
public class Invoice implements IEntity<Integer>
{

    private static final long serialVersionUID = 127010877609136197L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer primaryKey;

    @Column(name = "fact_number", length = 12)
    private String factNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "customer_id")
    private Customer customer;

    @Column(nullable = false, name = "date_invoice")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false, name = "date_cancel")
    @Temporal(TemporalType.DATE)
    private Date cancelDate;

    @Column(name = "amount_ht")
    @Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
    private BigDecimal amountHT;

    @Column(name = "amount_ttc")
    @Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
    private BigDecimal amountTTC;

    @Column(name = "invoice_state")
    @Type(type = "com.persistence.util.hibernate.GenericEnumUserType", parameters = { @Parameter(name = "enumClass",
                value = "com.persistence.entity.enum.INVOICE_STATE") })
    private INVOICE_STATE state;
}