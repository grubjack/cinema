package com.grubjack.cinema.web.filter;

import com.grubjack.cinema.util.LocalePropertyManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Urban Aleksandr
 */
public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LocalePropertyManager.setFor((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
