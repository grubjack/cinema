package com.grubjack.cinema.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * {@code EncodingFilter} custom http filter
 * <p>
 * Set UTF-8 character for all requests
 */
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * Set UTF-8 character for all requests
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}