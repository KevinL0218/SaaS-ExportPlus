package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.PackingDao;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import cn.itcast.service.cargo.PackingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 装箱实现类
 */
@Service(timeout = 200000)
public class PackingServiceImpl implements PackingService {

    @Autowired
    private PackingDao packingDao;

    /**
     * 分页查询
     * @param packingExample
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageInfo<Packing> findByPage(PackingExample packingExample, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Packing> list = packingDao.selectByExample(packingExample);
        return new PageInfo<>(list);
    }

    /**
     * 根据id删除
     * @param packingListId
     * @return
     */
    @Override
    public boolean deleteByPrimaryKey(String packingListId) {
        Packing packing = packingDao.selectByPrimaryKey(packingListId);
        if (packing.getState() == 1 ){
            return false;
        }
        packingDao.deleteByPrimaryKey(packingListId);
        return true;
    }

    /**
     * 添加
     * @param packing
     * @return
     */
    @Override
    public int insertSelective(Packing packing) {
        // 主键
        packing.setPackingListId(UUID.randomUUID().toString());
        // 默认草稿状态
        packing.setState(0);
        packing.setCreateTime(new Date());
        return packingDao.insertSelective(packing);
    }

    /**
     * 查询所有
     * @param example
     * @return
     */
    @Override
    public List<Packing> selectByExample(PackingExample example) {
        return packingDao.selectByExample(example);
    }

    /**
     * 根据id查询
     * @param packingListId
     * @return
     */
    @Override
    public Packing selectByPrimaryKey(String packingListId) {
        return packingDao.selectByPrimaryKey(packingListId);
    }
}
