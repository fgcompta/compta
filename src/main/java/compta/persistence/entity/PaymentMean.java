package compta.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;


@Entity
@Table(name = "payment_mean")
@Data
public class PaymentMean implements IEntity<Integer>
{

    private static final long serialVersionUID = -349071518074965808L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer primaryKey;

    @Column(name = "payment_mean_name", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String name;

}