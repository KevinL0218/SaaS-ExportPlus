package cn.itcast.service.system;

import cn.itcast.domain.system.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {
    /**
     * 查询全部
     */
    List<Dept> findAll(String companyId);

    /**
     * 分页查询
     */
    PageInfo<Dept> findByPage(String companyId, int pageNum, int pageSize);

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
     * 删除部门
     * @param id 要删除的部门id
     * @return 删除成功返回true
     */
    boolean delete(String id);
}
