package compta.persistence.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.Data;

import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;


@Entity
@Table(name = "vat_rate")
@Data
public class VatRate implements IEntity<Integer>
{

    private static final long serialVersionUID = 2065480615076352626L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer primaryKey;

    @Column(name = "rate_name", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String name;

    @Column(name = "vat_rate")
    @Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
    private BigDecimal rate;
}