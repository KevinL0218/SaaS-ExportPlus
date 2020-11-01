package cn.itcast.web.controller;

import cn.itcast.domain.system.Email;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.EmailService;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private EmailService emailService;

    /* 普通的登陆认证
    @RequestMapping("/login")
    public String login(String email, String password) {
        // 非空判断
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return "forward:/login.jsp";
        }

        // 根据email查询
        User user = userService.findByEmail(email);

        // 判断
        if (user != null && user.getPassword().equals(password)) {
            // 登陆成功. 保存用户到会话域
            session.setAttribute("loginUser",user);
            // 动态菜单：查询登陆用户的权限
            List<Module> moduleList = moduleService.findModuleByUserId(user.getId());
            session.setAttribute("moduleList",moduleList);
            return "home/main";
        }

        // 登陆失败
        request.setAttribute("error","用户名或密码错误！");
        return "forward:/login.jsp";

    }
    */

    /* shiro 登陆认证 */
    @RequestMapping("/login")
    public String login(String email, String password) {
        // 判断
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return "forward:/login.jsp";
        }

        try {
            // 获取subject对象, 表示当前用户
            Subject subject = SecurityUtils.getSubject();
            // 创建封装账号密码的token
            AuthenticationToken token = new UsernamePasswordToken(email,password);
            // shiro登陆认证: 自动执行realm类中的认证方法，在认证方法中访问数据库数据
            subject.login(token);

            // 认证成功，获取shiro认证后的身份对象。
            // subject.getPrincipal() 获取的是realm的认证方法返回的对象的构造函数的第一个参数
            User user = (User) subject.getPrincipal();

            // 登陆成功. 保存用户到会话域
            session.setAttribute("loginUser",user);
            // 动态菜单：查询登陆用户的权限
            List<Module> moduleList = moduleService.findModuleByUserId(user.getId());
            session.setAttribute("moduleList",moduleList);
            //查询用户的邮件
            List<Email> emailList = emailService.findByUserId(user.getId());
            session.setAttribute("emailList", emailList);
            return "home/main";

        } catch (AuthenticationException e) {
            e.printStackTrace();
            // 登陆失败
            request.setAttribute("error","用户名或密码错误！");
            return "forward:/login.jsp";

        }

    }

    @RequestMapping("/home")
    public String home() {
        // 默认登陆成功，跳转到主页：
        return "home/home";
    }

    // 注销
    @RequestMapping("/logout")
    public String logout() {
        // 释放shiro中的认证信息
        SecurityUtils.getSubject().logout();
        // 释放资源
        session.removeAttribute("loginUser");
        session.removeAttribute("emailList");
        // 销毁session
        session.invalidate();
        // 注销后跳转到登陆
        return "forward:/login.jsp";
    }
}
