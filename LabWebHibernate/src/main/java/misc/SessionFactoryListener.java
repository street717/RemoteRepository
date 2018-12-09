package misc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import model.hibernate.HibernateUtil;

@WebListener
public class SessionFactoryListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("SessionFactory Initialized");
		HibernateUtil.getSessionfactory();
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("SessionFactory Destroyed");
		HibernateUtil.closeSessionFactory();
	}
}
