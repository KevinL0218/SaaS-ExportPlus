package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;

import java.util.List;

/**
 * 工厂Dao
 */
public interface FactoryDao {
	
	// 根据id删除工厂
    int deleteByPrimaryKey(String id);

	// 保存工厂
    int insertSelective(Factory record);

	// 条件查询
    List<Factory> selectByExample(FactoryExample example);

	// 根据id查询工厂
    Factory selectByPrimaryKey(String id);

	// 更新工厂
    int updateByPrimaryKeySelective(Factory record);
}