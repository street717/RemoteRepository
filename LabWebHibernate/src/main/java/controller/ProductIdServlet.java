package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.ProductBean;
import model.ProductService;
import model.dao.ProductDAOHibernate;
import model.hibernate.HibernateUtil;

@WebServlet(
		urlPatterns={"/pages/products.view"}
)
public class ProductIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
	@Override
	public void init() throws ServletException {
		productService = new ProductService(
				new ProductDAOHibernate(HibernateUtil.getSessionfactory()));
	}
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//接收資料	
		String temp = request.getParameter("id");
		System.out.println("ProductIdServlet:"+temp);
//轉換資料
		JsonArray array = new JsonArray();
		int id = 0;
		if(temp==null || temp.trim().length()==0) {
			JsonObject obj = new JsonObject();
			obj.addProperty("text", "ID是必要欄位");
			obj.addProperty("hasMoreData", false);
			array.add(obj);
		} else {
			try {
				id = Integer.parseInt(temp);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				JsonObject obj = new JsonObject();
				obj.addProperty("text", "ID必需是數字");
				obj.addProperty("hasMoreData", false);
				array.add(obj);
			}
		}
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(array!=null && array.size()!=0) {
			out.write(array.toString());
			out.close();
			return;
		}
		
//呼叫model
		ProductBean bean = new ProductBean();
		bean.setId(id);
		List<ProductBean> result = productService.select(bean);

//根據model執行結果
		if(result==null || result.isEmpty()) {
			JsonObject obj = new JsonObject();
			obj.addProperty("text", "ID不存在");
			obj.addProperty("hasMoreData", false);
			array.add(obj);
		} else {
			JsonObject obj = new JsonObject();
			obj.addProperty("text", "ID存在");
			obj.addProperty("hasMoreData", true);
			array.add(obj);
			
			ProductBean data = result.get(0);
			JsonObject product = new JsonObject();
			product.addProperty("id", data.getId());
			product.addProperty("name", data.getName());
			product.addProperty("price", data.getPrice());
			product.addProperty("make", data.getMake().toString());
			product.addProperty("expire", data.getExpire());
			
			array.add(product);
		}
		
		out.write(array.toString());
		out.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
