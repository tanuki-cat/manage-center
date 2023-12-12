package me.zhengjie.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.merchant.domain.CompanyBaseInfoExt;
import me.zhengjie.modules.merchant.domain.CompanyBusinessInfoExt;
import me.zhengjie.modules.merchant.domain.dto.CompanyInfoExtDto;
import me.zhengjie.modules.merchant.mapper.CompanyBaseInfoExtMapper;
import me.zhengjie.modules.merchant.mapper.CompanyBusinessInfoExtMapper;
import me.zhengjie.modules.merchant.service.CompanyInfoExtService;
import org.springframework.stereotype.Service;

/**
 * @author wangzy
 * @date 2023/12/12 21:15
 * @describe
 * @since version 1.0
 */
@Service
@RequiredArgsConstructor
public class CompanyInfoExtServiceImpl implements CompanyInfoExtService {

    private final CompanyBusinessInfoExtMapper businessInfoExtMapper;
    private final CompanyBaseInfoExtMapper baseInfoExtMapper;
    /**
     * Creates a new CompanyInfoExtDto.
     *
     * @param resources the CompanyInfoExtDto object to create
     */
    @Override
    public void create(CompanyInfoExtDto resources) {
        Long companyId = resources.getId();
        CompanyBaseInfoExt baseInfoExt = baseInfoExtMapper.selectOne(new LambdaQueryWrapper<CompanyBaseInfoExt>()
                .eq(CompanyBaseInfoExt::getCompanyId, companyId));
        CompanyBusinessInfoExt businessInfoExt = businessInfoExtMapper.selectOne(new LambdaQueryWrapper<CompanyBusinessInfoExt>()
                .eq(CompanyBusinessInfoExt::getCompanyId, companyId));
        if (baseInfoExt != null) {
            resources.getCompanyBaseInfoExt().setId(baseInfoExt.getId());
            baseInfoExtMapper.updateById(resources.getCompanyBaseInfoExt());
        } else {
            resources.getCompanyBaseInfoExt().setCompanyId(companyId);
            baseInfoExtMapper.insert(resources.getCompanyBaseInfoExt());
        }
        if (businessInfoExt != null) {
            resources.getCompanyBusinessInfoExt().setId(businessInfoExt.getId());
            businessInfoExtMapper.updateById(businessInfoExt);
        } else {
            resources.getCompanyBusinessInfoExt().setCompanyId(companyId);
            businessInfoExtMapper.insert(resources.getCompanyBusinessInfoExt());
        }

    }

    /**
     * Updates the CompanyInfoExtDto object.
     *
     * @param resources the CompanyInfoExtDto object to update
     */
    @Override
    public void update(CompanyInfoExtDto resources) {
        CompanyBaseInfoExt baseInfoExt = baseInfoExtMapper.selectById(resources.getCompanyBaseInfoExt().getId());
        CompanyBusinessInfoExt businessInfoExt = businessInfoExtMapper.selectById(resources.getCompanyBusinessInfoExt().getId());
        if (!baseInfoExt.equals(resources.getCompanyBaseInfoExt())) {
            baseInfoExtMapper.updateById(resources.getCompanyBaseInfoExt());
        }
        if (!businessInfoExt.equals(resources.getCompanyBusinessInfoExt())) {
            businessInfoExtMapper.updateById(resources.getCompanyBusinessInfoExt());
        }
    }
}
