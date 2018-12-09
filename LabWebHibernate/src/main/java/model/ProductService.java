package model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.dao.ProductDAOHibernate;
import model.hibernate.HibernateUtil;
@Service
public class ProductService {
	@Autowired
	private ProductDAO productDao;
	public ProductService(ProductDAO productDao) {
		this.productDao = productDao;
	}
	public static void main(String[] args) {
		try {
			HibernateUtil.getSessionfactory().getCurrentSession().beginTransaction();

			ProductService productService = new ProductService(
					new ProductDAOHibernate(HibernateUtil.getSessionfactory()));
			List<ProductBean> beans = productService.select(null);
			System.out.println("beans="+beans);
			
			HibernateUtil.getSessionfactory().getCurrentSession().getTransaction().commit();
		} finally {
			HibernateUtil.closeSessionFactory();
		}
	}
	public List<ProductBean> select(ProductBean bean) {
		List<ProductBean> result = null;
		if(bean!=null && bean.getId()!=0) {
			ProductBean temp = productDao.findByPrimaryKey(bean.getId());
			if(temp!=null) {
				result = new ArrayList<ProductBean>();
				result.add(temp);
			}
		} else {
			result = productDao.findAll(); 
		}
		return result;
	}
	public ProductBean insert(ProductBean bean) {
		ProductBean result = null;
		if(bean!=null) {
			result = productDao.create(bean);
		}
		return result;
	}
	public ProductBean update(ProductBean bean) {
		ProductBean result = null;
		if(bean!=null) {
			result = productDao.update(bean.getName(), bean.getPrice(),
					bean.getMake(), bean.getExpire(), bean.getId());
		}
		return result;
	}
	public boolean delete(ProductBean bean) {
		boolean result = false;
		if(bean!=null) {
			result = productDao.remove(bean.getId());
		}
		return result;
	}
}
