package compta.persistence.util.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
            final Configuration configuration = new Configuration ();
            configuration.configure ();
            final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder ().applySettings (configuration.getProperties ()).buildServiceRegistry ();
            sessionFactory = configuration.buildSessionFactory (serviceRegistry);
		} catch (final Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void init()
	{
	}

	public static void destroy()
	{
		sessionFactory.close();
	}

}