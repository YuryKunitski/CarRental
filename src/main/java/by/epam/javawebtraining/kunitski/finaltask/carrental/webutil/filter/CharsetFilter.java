package by.epam.javawebtraining.kunitski.finaltask.carrental.webutil.filter;

import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

	private static final Logger LOG = LogManager.getLogger(ConnectionPool.class.getName());
	private static String LOG_MESSAGE = "CharsetFilter : {} encoding has been successfully setted";
	private static String CHAR_ENCODING = "character-encoding";
	private String encoding;

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) {
		encoding = filterConfig.getInitParameter(CHAR_ENCODING);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		LOG.debug(LOG_MESSAGE + " " + encoding);
		filterChain.doFilter(request, response);
	}
}
