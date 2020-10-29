package cn.itcast.web.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC全局异常，当控制器方法出现异常自动执行这里
 */
@Component
public class CustomException implements HandlerExceptionResolver{
    @Override
    public ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {
        // 打印异常
        ex.printStackTrace();
        // 返回
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        return mv;
    }
}
