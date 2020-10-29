package cn.itcast.dao.system;

import cn.itcast.domain.system.Dept;

import javax.xml.soap.Detail;
import java.util.List;

public interface DeptDao {
    /**
     * 查询全部
     */
    List<Dept> findAll(String companyId);

    /**
     * 根据id查询
     */
    Dept findById(String id);

    /**
     * 添加
     * @param dept
     */
    void save(Dept dept);

    /**
     * 修改
     * @param dept
     */
    void update(Dept dept);
    /**
     * 根据删除的部门id查询，查看是否有子部门
     */
    List<Dept> findByParent(String deptId);

    /**
     * 删除部门
     */
    void delete(String id);
}
