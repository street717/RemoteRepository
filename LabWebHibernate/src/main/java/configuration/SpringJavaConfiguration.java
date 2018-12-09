package configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

@Configuration
public class SpringJavaConfiguration {
	@Bean
    public SessionFactory sessionFactory() {
    	LocalSessionFactoryBuilder builder=new LocalSessionFactoryBuilder(dataSource());
//   	builder.addAnnotatedClasses(model.ProductBean.class);
//    	Properties props=new Properties();
//    	props.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
//    	props.setProperty("hibernate.show_sql", "0true");
//    	props.setProperty("hibernate.current_session_context_class", "thread");
//    	builder.addProperties(props);
    	builder.configure("hibernate.cfg.xml");
    		
    	return builder.buildSessionFactory();
    }
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dmds=new DriverManagerDataSource();
		dmds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dmds.setUrl("jdbc:sqlserver://localhost:1433;database=java");
		dmds.setUsername("sa");
		dmds.setPassword("sa123456");
		return dmds;
	}
	
	public static void main(String[]args) {
		ApplicationContext context=
				new AnnotationConfigApplicationContext(configuration.SpringJavaConfiguration.class);
		SessionFactory sessionFactory=(SessionFactory)context.getBean("sessionFactory");
		sessionFactory.getCurrentSession().beginTransaction();
		System.out.println(sessionFactory.getCurrentSession().get(model.ProductBean.class, 2));
		
		sessionFactory.getCurrentSession().getTransaction().commit();
		((ConfigurableApplicationContext)context).close();

	}
}
