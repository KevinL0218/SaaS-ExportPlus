package cn.itcast.web.aspect;

import cn.itcast.domain.system.SysLog;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 自动记录日志的切面类，拦截controller的请求，记录日志到数据库库
 */
@Aspect     // 指定一个切面类
@Component  // 创建对象加入容器
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 环绕通知，环绕目标方法执行
     *
     * @param pjp
     */
    @Around("execution(* cn.itcast.web.controller..*.*(..)) && !bean(sysLogController)")
    public Object insertLog(ProceedingJoinPoint pjp) {
        // 获取当前执行的目标对象的类的全名
        String className = pjp.getTarget().getClass().getName();
        // 获取当前执行的方法
        String methodName = pjp.getSignature().getName();

        // 获取id
        String ip = request.getRemoteAddr();

        // 获取登陆用户信息
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        String companyId = "";
        String companyName = "";
        String userName = "";
        if (loginUser != null) {
            companyId = loginUser.getCompanyId();
            companyName = loginUser.getCompanyName();
            userName = loginUser.getUserName();
        }

        try {
            // 放行，执行控制器方法
            Object retValue = pjp.proceed();

            // 创建对象封装数据
            SysLog log = SysLog.builder()
                    .action(className)
                    .method(methodName)
                    .ip(ip)
                    .userName(userName)
                    .companyId(companyId)
                    .companyName(companyName)
                    .time(new Date()).build();

            // 记录日志
            sysLogService.save(log);
            return retValue;
        } catch (Throwable throwable) {
            // 注意这里最好抛出异常，这样出错就可以被全局异常处理
            throw new RuntimeException(throwable);
        }
    }
}
