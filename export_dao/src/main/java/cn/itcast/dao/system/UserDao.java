package cn.itcast.dao.system;

import cn.itcast.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserDao {

	//根据企业id查询全部
	List<User> findAll(String companyId);

	//根据id查询
    User findById(String userId);

	//根据id删除
	void delete(String userId);

	//保存
	void save(User user);

	//更新
	void update(User user);

	//先根据用户id查询用户角色中间表
    Long findUserRoleByUserId(String id);

	//解除用户角色的关系
    void deleteUserRoleByUserId(String userId);

    //用户添加角色
	void saveUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

	//根据email查询
    User findByEmail(String email);

    //根据部门id查询管理者，用于购销合同审单人的下拉显示
    List<User> findUserByDeptId(@Param("deptId") String deptId, @Param("companyId") String companyId);
}