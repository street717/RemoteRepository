package misc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class EncodingFilter implements Filter {
	private FilterConfig filterConfig;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	@Override
	public void doFilter(ServletRequest req,
			ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		System.out.println("EncodingFilter pre-processing");
//		if(不滿意pre-processing的結果) {
//			request.getRequestDispatcher("").forward(request, response);
//			return;
//		}

		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
		
		System.out.println("EncodingFilter post-processing");
//		if(不滿意post-processing的結果) {
//			request.getRequestDispatcher("").forward(request, response);
//			return;
//		}
	}
	@Override
	public void destroy() {

	}
}
