package compta.persistence.util.hibernate;

import org.hibernate.Session;

import com.google.inject.Provider;

public class HibernateSessionProvider implements Provider<Session>
{

	@Override
	public Session get()
	{
		final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		return session;
	}
}
