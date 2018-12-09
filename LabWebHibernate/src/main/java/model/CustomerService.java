package model;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.dao.CustomerDAOHibernate;
import model.hibernate.HibernateUtil;
@Service
public class CustomerService {
	public static void main(String[] args) {
//		try {
//			HibernateUtil.getSessionfactory().getCurrentSession().beginTransaction();
//
//			CustomerService customerService = new CustomerService(
//					new CustomerDAOHibernate(HibernateUtil.getSessionfactory()));
//			CustomerBean login = customerService.login("Alex", "A");
//			System.out.println("login="+login);
//			
//			boolean change = customerService.changePassword("Ellen", "EEE", "E");
//			System.out.println("change="+change);
//			
//			HibernateUtil.getSessionfactory().getCurrentSession().getTransaction().commit();
//		} finally {
//			HibernateUtil.closeSessionFactory();
//		}
	}
	@Autowired
	private CustomerDAO customerDao;
	public CustomerService(CustomerDAO customerDao) {
		this.customerDao = customerDao;
	}
	public CustomerBean login(String username, String password) {
		CustomerBean bean = customerDao.findByPrimaryKey(username);
		if(bean!=null) {
			if(password!=null && password.length()!=0) {
				byte[] temp = password.getBytes();	//使用者輸入
				byte[] pass = bean.getPassword();	//資料庫抓出
				if(Arrays.equals(temp, pass)) {
					return bean;
				}
			}
		}
		return null;
	}
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		CustomerBean bean = this.login(username, oldPassword);
		if(bean!=null) {
			if (newPassword!=null && newPassword.length()!=0) {
				byte[] temp = newPassword.getBytes();
				return customerDao.update(
						temp, bean.getEmail(), bean.getBirth(), username);
			}
		}
		return false;
	}
}
