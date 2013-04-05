package compta.client.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;

import compta.persistence.dao.GenericDAOBase;
import compta.persistence.util.IEntity;

@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

	static Logger					log	= org.slf4j.LoggerFactory.getLogger(EntityConverter.class);

	private final GenericDAOBase	dao	= new GenericDAOBase();

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		IEntity<?> entity;
		if (value == null) {
			entity = null;
		} else {
			final Class clazz = getClazz(context, component);
			final Serializable id = getPrimaryKey(value, clazz);
			entity = dao.getById(id, false, clazz);
			if (entity == null) {
				log.debug("There is no entity with id:  " + id);
			}
		}
		return entity;
	}
	@SuppressWarnings("rawtypes")
	private Serializable getPrimaryKey(final String value, final Class clazz) {

		final Type type = (clazz.getGenericInterfaces()[0]);

		if (type instanceof ParameterizedType) {
			final Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
			if (actualType.equals(Integer.class)) {
				return new Integer(value);
			} else if (actualType.equals(String.class)) {
				return value;
			}
		}

		return new Integer(value);
	}
	@Override
	public String getAsString(final FacesContext facesContext, final UIComponent component, final Object value)
			throws ConverterException {
		if (value != null && !(value instanceof IEntity)) {
			throw new IllegalArgumentException("This converter only handles instances of BaseIdentityEntity");
		}
		if (value == null) {
			return "";
		}
		if (value instanceof String) {
			return (String) value;
		}
		final IEntity<?> entity = (IEntity<?>) value;
		return entity.getPrimaryKey() == null ? "" : entity.getPrimaryKey().toString();
	}

	// Gets the class corresponding to the context in jsf page
	@SuppressWarnings("rawtypes")
	private Class getClazz(final FacesContext facesContext, final UIComponent component) {
		return component.getValueExpression("value").getType(facesContext.getELContext());
	}
}