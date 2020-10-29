package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.ExportProduct;
import cn.itcast.domain.cargo.ExportProductExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 报运单商品
 */
public interface ExportProductService {

    /**
     * 分页查询
     */
    PageInfo<ExportProduct> findByPage(
            ExportProductExample exportProductExample, int pageNum, int pageSize);

    /**
     * 查询所有
     */
    List<ExportProduct> findAll(ExportProductExample exportProductExample);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ExportProduct findById(String id);

    /**
     * 新增
     */
    void save(ExportProduct exportProduct);

    /**
     * 修改
     */
    void update(ExportProduct exportProduct);

    /**
     * 删除
     */
    void delete(String id);
}











