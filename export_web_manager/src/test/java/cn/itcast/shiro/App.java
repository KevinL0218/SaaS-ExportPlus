package cn.itcast.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

import java.util.UUID;

public class App {

    // 加密
    //明文        密文  【彩虹表  撞库】
    // 1       c4ca4238a0b923820dcc509a6f75849b
    @Test
    public void testMd5() {
        // 参数：要加密的数据
        Md5Hash hash = new Md5Hash("itcast");
        System.out.println("hash = " + hash);
    }

    // 加密、加盐
    @Test
    public void testMd5Salt() {
        String salt = "lw@export.com";
        // 参数1：要加密的数据
        // 参数2：盐（打死都不能告诉别人）
        // 参数3：迭代次数
        Md5Hash hash = new Md5Hash("1",salt);
        // e1087d424b213621545713b872420c7b
        System.out.println("hash = " + hash);
    }


    // 加密、加随机盐
    @Test
    public void testMd5RandomSalt() {
        // 随机盐：需要保存到数据库
        String salt = UUID.randomUUID().toString();
        Md5Hash hash = new Md5Hash("1",salt);
        System.out.println("hash = " + hash);
    }

    @Test
    public void test() {
        Sha256Hash hash = new Sha256Hash("1");
        System.out.println(hash.toString().length());
        System.out.println(hash.toString());
    }
}
