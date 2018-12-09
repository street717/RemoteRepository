package test;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.dao.CustomerDAOHibernate;

public class Test {

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("beans.config.xml");
		SessionFactory sessionFactory=(SessionFactory)context.getBean("sessionFactory");
		sessionFactory.getCurrentSession().beginTransaction();
		CustomerDAOHibernate dao=(CustomerDAOHibernate)context.getBean("customerDAOHibernate");
		System.out.println(dao.findByPrimaryKey("Alex"));
		//System.out.println(sessionFactory.getCurrentSession().get(CustomerBean.class, "Alex"));
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		((ConfigurableApplicationContext)context).close();

	}

}
