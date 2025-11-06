package az.customers.interceptor;

import az.customers.logger.DPLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final DPLogger logger = DPLogger.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        logger.info("Incoming Request: Method = {}," +
                " URI = {}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long start = (Long) request.getAttribute("startTime");
        Long duration = System.currentTimeMillis() - start;
        String ip = request.getRemoteAddr();
        logger.info("Incoming Request: Method = {}, URI = {}, Status code = {}, Duration = {} ms, IP = {}",
                request.getMethod(), request.getRequestURI(), response.getStatus(), duration, ip);

    }
}
