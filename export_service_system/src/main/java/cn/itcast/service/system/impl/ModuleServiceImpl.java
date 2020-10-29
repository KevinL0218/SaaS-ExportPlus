package cn.itcast.service.system.impl;

import cn.itcast.dao.system.ModuleDao;
import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        return moduleDao.findAll();
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
