package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.vo.ExportResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 报运单模块
 */
public interface ExportService {

    /**
     * 分页查询
     */
    PageInfo<Export> findByPage(
            ExportExample exportExample, int pageNum, int pageSize);

    /**
     * 查询所有
     */
    List<Export> findAll(ExportExample exportExample);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Export findById(String id);

    /**
     * 新增
     */
    void save(Export export);

    /**
     * 修改
     */
    void update(Export export);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据电子报运的结果修改数据库：报运状态备注、商品交税金额
     * @param exportResult
     */
    void updatExport(ExportResult exportResult);
}