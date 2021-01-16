package info.pesla.autoconfigure.header;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PeslaHeaderFilter implements Filter {

    private final String bestCompanyHeaderDefault;

    public PeslaHeaderFilter(String bestCompanyHeaderDefault) {
        this.bestCompanyHeaderDefault = bestCompanyHeaderDefault;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        prepareMyRequestHeaderForResponse((HttpServletRequest) servletRequest, response);
        response.addHeader("best-company", bestCompanyHeaderDefault);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void prepareMyRequestHeaderForResponse(HttpServletRequest servletRequest, HttpServletResponse response) {
        String requestHeaderValue = servletRequest.getHeader("my-request");
        if (requestHeaderValue != null && requestHeaderValue.equals("I need money")) {
            response.addHeader("pesla-response", "We can give it to you");
        }
    }
}
