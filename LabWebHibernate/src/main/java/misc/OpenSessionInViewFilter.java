package misc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.hibernate.HibernateException;

import model.hibernate.HibernateUtil;

@WebFilter("/*")
public class OpenSessionInViewFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	@Override
	public void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HibernateUtil.getSessionfactory().getCurrentSession().beginTransaction();
			chain.doFilter(request, response);
			HibernateUtil.getSessionfactory().getCurrentSession().getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			HibernateUtil.getSessionfactory().getCurrentSession().getTransaction().rollback();
			chain.doFilter(request, response);
		}
	}
	@Override
	public void destroy() {

	}
}
