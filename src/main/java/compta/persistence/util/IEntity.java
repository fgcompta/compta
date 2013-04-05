package compta.persistence.util;

import java.io.Serializable;

public interface IEntity<PK extends Serializable> extends Serializable {

	PK getPrimaryKey();

	String getName();
}
