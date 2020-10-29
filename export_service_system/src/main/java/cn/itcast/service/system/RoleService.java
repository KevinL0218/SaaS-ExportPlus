package cn.itcast.service.system;

import cn.itcast.domain.system.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    /**
     * 查询全部
     */
    List<Role> findAll(String companyId);

    /**
     * 分页查询
     */
    PageInfo<Role> findByPage(String companyId, int pageNum, int pageSize);

    /**
     * 根据id查询
     */
    Role findById(String id);

    /**
     * 添加
     * @param role
     */
    void save(Role role);

    /**
     * 修改
     * @param role
     */
    void update(Role role);

    /**
     * 删除部门
     */
    void delete(String id);

    /**
     * 查询用户已经拥有的角色
     * @param id
     * @return
     */
    List<Role> findUserRoleByUserId(String id);
}
