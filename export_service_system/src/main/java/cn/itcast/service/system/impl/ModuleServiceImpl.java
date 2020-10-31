package cn.itcast.service.system.impl;

import cn.itcast.dao.system.ModuleDao;
import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Module> findAll() {
        //1.创建配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置参数：最大连接数
        config.setMaxTotal(20);
        //设置最长等待时间
        config.setMaxWaitMillis(2000);
        //2.创建连接池对象
        JedisPool pool = new JedisPool(config, "localhost", 6379);
        //3.从连接池中获取连接
        Jedis jedis = pool.getResource();

        //定义集合保存权限
        List<Module> moduleList = null;
        //创建转换对象
        ObjectMapper mapper = new ObjectMapper();
        //获取权限json字符串
        String modules = jedis.get("modules");
        //判断是否为空
        if (modules == null) {
            //从MySql中查询权限
            moduleList = moduleDao.findAll();
            System.out.println("从MySql中查询权限");
            try {
                //将集合转为json字符串
                String json = mapper.writeValueAsString(moduleList);
                //保存到redis
                jedis.set("modules",json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else {
            //将权限json字符串转为List
            try {
                moduleList = mapper.readValue(modules, List.class);
                System.out.println("从Redis中查询权限");

                //定义集合保存权限，ObjectMapper封装的集合元素类型不是Module，所以定义新的集合
                List<Module> list = new ArrayList<>();
                //定义权限对象
                Module module = null;
                /*
                遍历使用ObjectMapper封装的集合，该集合的每个元素是LinkedHashMap，不能直接遍历
                需要转为json字符串，再转为对象，这里不要用增强型for循环
                */
                for (int i = 0; i < moduleList.size(); i++) {
                    try {
                        //将LinkedHashMap转为json
                        String json = mapper.writeValueAsString(moduleList.get(i));
                        //将json转为Module对象
                        module = mapper.readValue(json, Module.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //添加进集合
                    list.add(module);
                }
                moduleList = list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //关闭连接
        jedis.close();
        //返回结果
        return moduleList;
    }

    @Override
    public PageInfo<Module> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Module> list = moduleDao.findAll();
        return new PageInfo<>(list);
    }

    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    @Override
    public void save(Module module) {
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    @Override
    public List<Module> findModuleByRoleId(String roleId) {
        return moduleDao.findModuleByRoleId(roleId);
    }

    @Override
    public void updateRoleModule(String roleId, String moduleIds) {
        //-- 需求：角色分配权限
        //-- 1. 解除角色权限关系
        //DELETE FROM pe_role_module WHERE role_id=?
        moduleDao.deleteRoleModuleByRoleId(roleId);

        //-- 2. 给角色添加权限
        //INSERT INTO pe_role_module(role_id,module_id)VALUES(?,?)
        if (!StringUtils.isEmpty(moduleIds)) {
            String[] array = moduleIds.split(",");
            if (array != null && array.length>0){
                for (String moduleId : array) {
                    moduleDao.saveRoleModule(roleId,moduleId);
                }
            }
        }
    }

    @Override
    public List<Module> findModuleByUserId(String userId) {
        //1. 根据用户id查询用户
        User user = userDao.findById(userId);
        //1.1 获取用户等级
        Integer degree = user.getDegree();
        //1.2 判断
        if (degree == 0) {
            //登陆用户的degree=0,saas管理员，只显示saas模块
            //SELECT * FROM ss_module WHERE belong=0
            return moduleDao.findByBelong(0);
        }
        else if (degree == 1) {
            //登陆用户的degree=1,企业管理员，可以查看除了saas之外的其他模块
            //SELECT * FROM ss_module WHERE belong=1
            return moduleDao.findByBelong(1);
        }
        else {
            //登陆用户的degree是其他，就根据用户id查询权限
            return moduleDao.findModuleByUserId(userId);
        }
    }
}
