package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 货物装箱接口
 */
public interface PackingService {

    /**
     * 分页查询
     */
    PageInfo<Packing> findByPage(
            PackingExample packingExample, int pageNum, int pageSize);

    /**
     * 根据id删除
     * @param packingListId
     * @return
     */
    boolean deleteByPrimaryKey(String packingListId);

    /**
     * 添加
     * @param packing
     * @return
     */
    int insertSelective(Packing packing);

    /**
     * 查询所有
     * @param example
     * @return
     */
    List<Packing> selectByExample(PackingExample example);

    /**
     * 根据id查询
     * @param packingListId
     * @return
     */
    Packing selectByPrimaryKey(String packingListId);

    /**
     * 修改
     * @param packing
     */
    void update(Packing packing);
}
