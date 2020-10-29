package cn.itcast.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 自定义凭证匹配器,对用户输入的密码按照指定的算法加密
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    /**
     * 密码比对的方法： 校验用户输入的密码、数据库的密码
     * @param token 封装用户输入的token信息：账号、密码
     * @param info  封装用户的身份，可以获取数据库中正确的密码
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //1. 获取用户输入的email (作为盐)
        String email = (String) token.getPrincipal();

        //2. 获取用户输入的密码
        String password = new String((char[]) token.getCredentials());

        //3. 对用户输入的密码加密、加盐
        String encodePwd = new Md5Hash(password,email).toString();

        //4. 获取数据库中正确的密码
        String dbPwd = (String) info.getCredentials();

        //5. 判断:用户输入的密码  VS   数据库中正确的密码
        return encodePwd.equals(dbPwd);
    }
}
