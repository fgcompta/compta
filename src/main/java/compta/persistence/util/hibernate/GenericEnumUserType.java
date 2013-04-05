package compta.persistence.util.hibernate;

/*
 * Hibernate, Relational Persistence for Idiomatic Java
 * 
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution statements
 * applied by the authors. All third-party contributions are distributed under
 * license by Red Hat Middleware LLC.
 * 
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to: Free Software Foundation,
 * Inc. 51 Franklin Street, Fifth Floor Boston, MA 02110-1301 USA
 */

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.type.BasicType;
import org.hibernate.type.Type;
import org.hibernate.type.TypeResolver;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum type mapper Try and find the appropriate SQL type depending on column
 * metadata
 * <p/>
 * 
 * @author Emmanuel Bernard
 * @author Hardy Ferentschik
 */
@SuppressWarnings("all")
public class GenericEnumUserType implements EnhancedUserType, ParameterizedType, Serializable {

	/**
	 * This is the old scheme where logging of parameter bindings and value
	 * extractions was controlled by the trace level enablement on the
	 * 'org.hibernate.type' package...
	 * <p/>
	 * Originally was cached such because of performance of looking up the
	 * logger each time in order to check the trace-enablement. Driving this via
	 * a central Log-specific class would alleviate that performance hit, and
	 * yet still allow more "normal" logging usage/config.
	 */
	private static final boolean	IS_VALUE_TRACING_ENABLED	= LoggerFactory.getLogger(
																		StringHelper.qualifier(Type.class.getName()))
																		.isTraceEnabled();

	private transient Logger		log;

	private Logger log() {
		if (this.log == null) {
			this.log = LoggerFactory.getLogger(getClass());
		}
		return this.log;
	}

	public static final String		ENUM							= "enumClass";

	public static final String		SCHEMA							= "schema";

	public static final String		CATALOG							= "catalog";

	public static final String		TABLE							= "table";

	public static final String		COLUMN							= "column";

	public static final String		TYPE							= "type";

	private Class<? extends Enum>	enumClass;

	private transient Object[]		enumValues;
	// before any guessing
	private int						sqlType							= Types.INTEGER;

	private static final String		DEFAULT_IDENTIFIER_METHOD_NAME	= "getId";

	private static final String		DEFAULT_VALUE_OF_METHOD_NAME	= "valueOf";

	private Method					identifierMethod;

	private Method					valueOfMethod;

	private Class<?>				identifierType;

	public int[] sqlTypes() {
		return new int[]{this.sqlType};
	}

	public Class<? extends Enum> returnedClass() {
		return this.enumClass;
	}

	public boolean equals(final Object x, final Object y) throws HibernateException {
		return x == y;
	}

	public int hashCode(final Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor sessionImplementor,
			final Object owner) throws HibernateException, SQLException {
		final Object object = rs.getObject(names[0]);
		if (rs.wasNull()) {
			if (IS_VALUE_TRACING_ENABLED) {
				log().debug("Returning null as column {}", names[0]);
			}
			return null;
		}
		try {
			final Object valueOfKey = this.valueOfMethod.invoke(object, new Object[]{object});
			return valueOfKey;
		}
		catch (final IllegalArgumentException e) {
			throw new HibernateException(e);
		}
		catch (final IllegalAccessException e) {
			throw new HibernateException(e);
		}
		catch (final InvocationTargetException e) {
			throw new HibernateException(e);
		}

	}

	public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
			final SessionImplementor sessionImplementor) throws HibernateException, SQLException {
		if (value == null) {
			if (IS_VALUE_TRACING_ENABLED) {
				log().debug("Binding null to parameter: {}", index);
			}
			st.setNull(index, this.sqlType);
		} else {
			try {
				final Object valForDB = this.identifierMethod.invoke(value, new Object[0]);
				st.setObject(index, valForDB, this.sqlType);
			}
			catch (final IllegalArgumentException e) {
				throw new HibernateException(e);
			}
			catch (final IllegalAccessException e) {
				throw new HibernateException(e);
			}
			catch (final InvocationTargetException e) {
				throw new HibernateException(e);
			}
		}
	}

	private boolean isOrdinal(final int paramType) {
		switch (paramType) {
			case Types.INTEGER :
			case Types.NUMERIC :
			case Types.SMALLINT :
			case Types.TINYINT :
			case Types.BIGINT :
			case Types.DECIMAL : // for Oracle Driver
			case Types.DOUBLE : // for Oracle Driver
			case Types.FLOAT : // for Oracle Driver
				return true;
			case Types.CHAR :
			case Types.LONGVARCHAR :
			case Types.VARCHAR :
				return false;
			default :
				throw new HibernateException("Unable to persist an Enum in a column of SQL Type: " + paramType);
		}
	}

	public Object deepCopy(final Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(final Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
		return cached;
	}

	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}

	public void setParameterValues(final Properties parameters) {
		final String enumClassName = parameters.getProperty(ENUM);
		try {
			this.enumClass = ReflectHelper.classForName(enumClassName, this.getClass()).asSubclass(Enum.class);
		}
		catch (final ClassNotFoundException exception) {
			throw new HibernateException("Enum class not found", exception);
		}
		final String identifierMethodName = parameters.getProperty("identifierMethod", DEFAULT_IDENTIFIER_METHOD_NAME);

		try {
			this.identifierMethod = this.enumClass.getMethod(identifierMethodName, new Class[0]);
			this.identifierType = this.identifierMethod.getReturnType();
		}
		catch (final Exception e) {
			throw new HibernateException(e);
		}
		final String valueOfMethodName = parameters.getProperty("valueOfMethod", DEFAULT_VALUE_OF_METHOD_NAME);
		try {
			this.valueOfMethod = this.enumClass.getMethod(valueOfMethodName, new Class[]{this.identifierType});
		}
		catch (final Exception e) {
			throw new HibernateException("Failed to obtain valueOf method", e);
		}
		final String type = parameters.getProperty(TYPE);
		final TypeResolver tr = new TypeResolver();
		int[] sqlTypes = null;
		BasicType basic = null;
		if (type != null) {
			basic = tr.basic(type);
		} else {
			basic = tr.basic(this.identifierType.getSimpleName().toLowerCase());
		}
		sqlTypes = basic.sqlTypes(null);
		this.sqlType = sqlTypes[0];
	}

	/**
	 * Lazy init of {@link #enumValues}.
	 */
	private void initEnumValues() {
		if (this.enumValues == null) {
			this.enumValues = this.enumClass.getEnumConstants();
			if (this.enumValues == null) {
				throw new NullPointerException("Failed to init enumValues");
			}
		}
	}

	public String objectToSQLString(final Object value) {
		final boolean isOrdinal = isOrdinal(this.sqlType);
		if (isOrdinal) {
			final int ordinal = ((Enum) value).ordinal();
			return Integer.toString(ordinal);
		} else {
			return '\'' + ((Enum) value).name() + '\'';
		}
	}

	public String toXMLString(final Object value) {
		final boolean isOrdinal = isOrdinal(this.sqlType);
		if (isOrdinal) {
			final int ordinal = ((Enum) value).ordinal();
			return Integer.toString(ordinal);
		} else {
			return ((Enum) value).name();
		}
	}

	public Object fromXMLString(final String xmlValue) {
		try {
			final int ordinal = Integer.parseInt(xmlValue);
			initEnumValues();
			if (ordinal < 0 || ordinal >= this.enumValues.length) {
				throw new IllegalArgumentException("Unknown ordinal value for enum " + this.enumClass + ": " + ordinal);
			}
			return this.enumValues[ordinal];
		}
		catch (final NumberFormatException e) {
			try {
				return Enum.valueOf(this.enumClass, xmlValue);
			}
			catch (final IllegalArgumentException iae) {
				throw new IllegalArgumentException("Unknown name value for enum " + this.enumClass + ": " + xmlValue,
						iae);
			}
		}
	}
}