package cn.itcast.service.system.impl;

import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    @Override
    public PageInfo<User> findByPage(String companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userDao.findAll(companyId);
        return new PageInfo<>(list);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public boolean delete(String id) {
        //-- 用户删除
        //-- 1. 先根据用户id查询用户角色中间表
        //SELECT COUNT(*) FROM pe_role_user WHERE user_id=''
        Long count = userDao.findUserRoleByUserId(id);

        //-- 2. 如果查询结果是0，可以删除
        //DELETE FROM pe_user WHERE user_id=''
        if (count == 0) {
            userDao.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public void changeRole(String userId, String[] roleIds) {
        //-- 角色分配权限
        // -- 1. 解除用户角色的关系
        //DELETE FROM pe_role_user WHERE user_id=''
        userDao.deleteUserRoleByUserId(userId);
        //-- 2.用户添加角色
        //INSERT INTO pe_role_user(user_id,role_id)VALUES('','')
        if (roleIds != null && roleIds.length > 0) {
            for (String roleId : roleIds) {
                userDao.saveUserRole(userId,roleId);
            }
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }


    /**
     * 根据部门id查询管理者，用于购销合同审单人的下拉显示
     *
     * @param deptId
     * @paramCompanyId
     * @return
     */
    @Override
    public List<User> findUserByDeptId(String deptId, String companyId) {
        return userDao.findUserByDeptId(deptId,companyId);
    }
}
