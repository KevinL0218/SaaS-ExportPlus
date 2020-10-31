package cn.itcast.service.system;

import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    /**
     * 查询全部
     */
    List<User> findAll(String companyId);

    /**
     * 分页查询
     */
    PageInfo<User> findByPage(String companyId, int pageNum, int pageSize);

    /**
     * 根据id查询
     */
    User findById(String id);

    /**
     * 添加
     * @param user
     */
    void save(User user);

    /**
     * 修改
     * @param user
     */
    void update(User user);

    /**
     * 删除部门
     */
    boolean delete(String id);

    /**
     * 角色分配权限
     * @param userId
     * @param roleIds
     */
    void changeRole(String userId, String[] roleIds);

    /**
     * 根据email查询
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 根据部门id查询管理者，用于购销合同审单人的下拉显示
     * @param deptId
     * @param companyId
     * @return
     */
    List<User> findUserByDeptId(String deptId, String companyId);
}
