package compta.persistence.util.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Binder;
import com.google.inject.Module;


public class GuicePersistenceModule implements Module
{

	@Override
	public void configure(final Binder binder)
	{
		binder.bind(Session.class).toProvider(HibernateSessionProvider.class);
		binder.bind(SessionFactory.class).toProvider(HibernateSessionFactoryProvider.class);

	}

}
