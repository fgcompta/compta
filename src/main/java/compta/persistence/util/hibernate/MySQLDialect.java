package compta.persistence.util.hibernate;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySQLDialect extends MySQL5InnoDBDialect
{

	public MySQLDialect()
	{
		super();

		registerFunction( "yeardiff", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "TIMESTAMPDIFF(YEAR, ?1, ?2)") );
		registerFunction( "addmonths", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "DATE_ADD(?1, INTERVAL ?2 MONTH)") );
		registerFunction( "strtodate", new SQLFunctionTemplate( StandardBasicTypes.DATE, "STR_TO_DATE(?1, '%Y-%m-%d %h:%i:%s')") );
		registerFunction( "datesubdays", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "DATE_SUB(?1, INTERVAL ?2 DAY)") );
		registerFunction( "dateadddays", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "DATE_ADD(?1, INTERVAL ?2 DAY)") );
	}
}
