package compta.persistence.util.hibernate;

import org.hibernate.SessionFactory;

import com.google.inject.Provider;

public class HibernateSessionFactoryProvider implements Provider<SessionFactory>
{

	@Override
	public SessionFactory get()
	{
		return HibernateUtil.getSessionFactory();
	}
}
