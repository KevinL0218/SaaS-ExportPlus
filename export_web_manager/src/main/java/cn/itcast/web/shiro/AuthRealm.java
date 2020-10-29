package cn.itcast.web.shiro;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * shiro的realm类，访问认证、授权数据
 */
public class AuthRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 转换
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        // 获取email
        String email = upToken.getUsername();

        // 调用service查询
        User user = userService.findByEmail(email);
        if (user == null){
            return null;  // 报错：UnknownAccountException
        }
        // 获取数据库中正确的密码
        String dbPwd = user.getPassword();

        // 参数1：身份对象; 通过subject.getPrincipal()获取的就是这里的第一个参数
        // 参数2：告诉shiro数据库中正确的密码（shiro已经有用户输入的密码，自动校验）
        // 参数3：realm的名称，可以随便写，唯一。this.getName()获取的是默认名称
        SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user,dbPwd,this.getName());
        return sai;
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /* 需求： 查询登陆用户的权限，返回给realm的授权方法 */
        // 获取用户id (获取的是认证方法返回的对象中封装的用户信息)
        User user = (User) principals.getPrimaryPrincipal();

        // 根据用户的id，查询用户的权限
        List<Module> list = moduleService.findModuleByUserId(user.getId());

        // 返回
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        if (list != null && list.size() > 0) {
            for (Module module : list) {
                sai.addStringPermission(module.getName());
            }
        }
        return sai;
    }
}
