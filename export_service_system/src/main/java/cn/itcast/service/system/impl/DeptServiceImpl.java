package cn.itcast.service.system.impl;

import cn.itcast.dao.system.DeptDao;
import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }

    @Override
    public PageInfo<Dept> findByPage(String companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Dept> list = deptDao.findAll(companyId);
        return new PageInfo<>(list);
    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public void save(Dept dept) {
        dept.setId(UUID.randomUUID().toString());
        // 判断：如果父部门id是"", 设置为NULL。 （因为父部门id是外键，我们数据没有主键是空字符串）
        //if (dept.getParent() != null && "".equals(dept.getParent().getId())) {
        //    // 说明父部门id是"", 设置为NULL
        //    dept.getParent().setId(null);
        //}
        deptDao.save(dept);
    }

    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public boolean delete(String id) {
        //-- 删除部门 (告诉用户是否删除成功！)
        //-- 1. 根据删除的部门id查询，查看是否有子部门
        //SELECT * FROM pe_dept WHERE parent_id='100'
        List<Dept> list = deptDao.findByParent(id);

        //-- 2. 如果没有查询到结果，说明当前部门可以删除
        //DELETE FROM pe_dept WHERE dept_id='100'
        if (list == null || list.size() == 0) {
            // 说明当前部门没有子部门，可以删除
            deptDao.delete(id);
            return true;
        }
        return false;
    }
}
