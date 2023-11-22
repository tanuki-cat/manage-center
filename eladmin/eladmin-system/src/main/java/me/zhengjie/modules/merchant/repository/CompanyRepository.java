package me.zhengjie.modules.merchant.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import me.zhengjie.modules.merchant.domain.Company;
import me.zhengjie.modules.merchant.domain.vo.CompanyQueryCriteria;
import me.zhengjie.modules.merchant.mapper.CompanyMapper;
import me.zhengjie.modules.merchant.service.CompanyService;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.PageUtil;
import org.springframework.stereotype.Service;

/**
 * 功能描述
 *
 * @author ChinaJoy
 * @date 2023/11/22 16:50
 */
@Service
@AllArgsConstructor
public class CompanyRepository {
    private final CompanyService companyService;


    /**
     * 查询数据分页
     * @param criteria 条件
     * @param page 分页参数
     * @return PageResult
     */
   public PageResult<Company> queryAll(CompanyQueryCriteria criteria, Page<Object> page){
       IPage<Company> page1 = new Page<>(page.getCurrent(),page.getSize());
       return PageUtil.toPage(companyService.page(page1));
    }




}
