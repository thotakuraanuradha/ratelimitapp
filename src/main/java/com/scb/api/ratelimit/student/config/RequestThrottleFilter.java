package com.scb.api.ratelimit.student.config;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@PropertySource("application.properties")
public class RequestThrottleFilter extends GenericFilter {

    private Environment environment;

    @Autowired
    RequestThrottleFilter(Environment environment){
        this.environment=environment;
    }

    private int MAX_REQUESTS_PER_SECOND = Integer.parseInt(environment.getProperty("maxRequestsPerSecond"));

    private LoadingCache<String, Integer> requestCountsPerIpAddress;

    int requests = 0;

    public RequestThrottleFilter() {
        super();
        requestCountsPerIpAddress = CacheBuilder.newBuilder().
                expireAfterWrite(1, TimeUnit.SECONDS).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });

    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String clientIpAddress = getClientIP((HttpServletRequest) servletRequest);

        System.out.println("The client Ip:\t" + clientIpAddress);
        if (isMaximumRequestsPerSecondExceeded(clientIpAddress)) {

            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpServletResponse.getWriter().write("Too many requests");

            return;

           /* HashMap<String, Integer> requestMap = new HashMap<>();

            requestMap.put(((HttpServletRequest) servletRequest).getMethod(), requests++);
            System.out.println("requestMap:\t"+requestMap.toString());
            Object postKey = requestMap.get("POST");
            Object getKey = requestMap.get("GET");

            if (postKey != null && (Integer) postKey > 2) {
                System.out.println("Inside Post Key");
                httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                httpServletResponse.getWriter().write("Too many requests");

                return;
            }
            if (getKey != null && (Integer) getKey > 2) {
                System.out.println("Inside GET Key");
                httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                httpServletResponse.getWriter().write("Too many requests");
                return;

            }
*/
        }
        System.out.println("context path :\t" + ((HttpServletRequest) servletRequest).getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isMaximumRequestsPerSecondExceeded(String clientIpAddress) {
        int requests = 0;
        try {
            requests = requestCountsPerIpAddress.get(clientIpAddress);
            if (requests > MAX_REQUESTS_PER_SECOND) {
                requestCountsPerIpAddress.put(clientIpAddress, requests);
                return true;
            }
        } catch (ExecutionException e) {
            requests = 0;
        }
        requests++;
        requestCountsPerIpAddress.put(clientIpAddress, requests);
        System.out.println("requests:\t" + requests);
        return false;
    }

    public String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        System.out.println("xfHeader:\t" + xfHeader);
        return xfHeader.split(",")[0];
    }

    @Override
    public void destroy() {

    }
}
