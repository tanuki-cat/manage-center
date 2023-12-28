package me.zhengjie.modules.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.merchant.domain.Company;
import me.zhengjie.modules.merchant.domain.CompanyExt;
import me.zhengjie.modules.merchant.mapper.CompanyExtMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author wangzy
 * @date 2023/12/28 10:45
 * @describe
 * @since version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyExtService extends ServiceImpl<CompanyExtMapper, CompanyExt> implements IService<CompanyExt> {
    private final CompanyService companyService;
    @Transactional(rollbackFor = Exception.class)
    public void add(CompanyExt companyExt){
        this.saveOrUpdate(companyExt);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(CompanyExt companyExt){
        if (companyExt.getId() == null){
            return;
        }
        this.updateById(companyExt);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids){
        this.removeByIds(ids);
    }

    public CompanyExt getExtInfoByCompanyId(Long companyId){
        if (companyId == null){
            return null;
        }
        return this.lambdaQuery().eq(CompanyExt::getCompanyId,companyId).one();
    }
}
