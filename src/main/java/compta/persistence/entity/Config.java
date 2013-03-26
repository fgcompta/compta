package compta.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;


@Entity
@Table(name = "config")
@Data
public class Config implements IEntity<String>
{

    private static final long serialVersionUID = -9156221882647293844L;

    @Id
    @Column(name = "config_name", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String name;

    @Column(name = "config_value", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String value;

    @Override
    public String getPrimaryKey()
    {
        return this.name;
    }
}
