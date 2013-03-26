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
public class InvoicePaymentMean implements IEntity<Integer>
{

    private static final long serialVersionUID = -3255613343659091304L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer primaryKey;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "payment_mean_id")
    private PaymentMean paymentMean;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "invoice_id")
    private Invoice invoice;

    @Column(name = "amount")
    @Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
    private BigDecimal amount;

}