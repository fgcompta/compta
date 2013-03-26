package compta.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import compta.persistence.entity.enums.CUSTOMER_TYPE;
import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;


@Entity
@Table(name = "customer")
@Data
public class Customer implements IEntity<Integer>
{

    private static final long serialVersionUID = -2040579423409384020L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer primaryKey;

    @Column(name = "customer_name", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String name;

    @Column(name = "name_complement", length = EntityConstant.MAX_SIZE_VARCHAR)
    private String complement;

    @Column(name = "mail", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String mail;

    @Column(name = "phone_number", length = 10)
    private String phone;

    @Column(name = "mobile_number", length = 10)
    private String mobile;

    @Column(name = "infos", length = EntityConstant.MAX_SIZE_VARCHAR)
    private String infos;

    @Column(name = "street", length = EntityConstant.MAX_SIZE_VARCHAR)
    private String street;

    @Column(name = "postal_code", length = 5)
    private String postal_code;

    @Column(name = "city", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String city;

    @Column(name = "customer_type")
    @Type(type = "com.persistence.util.hibernate.GenericEnumUserType", parameters = { @Parameter(name = "enumClass",
                value = "com.persistence.entity.enum.CUSTOMER_TYPE") })
    private CUSTOMER_TYPE type;
}
