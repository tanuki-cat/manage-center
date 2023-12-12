package me.zhengjie.modules.merchant.service;

import me.zhengjie.modules.merchant.domain.dto.CompanyInfoExtDto;

/**
 * @author wangzy
 * @date 2023/12/12 21:07
 * @describe 公司扩展信息服务类
 * @since version 1.0
 */
public interface CompanyInfoExtService {

    /**
     * Creates a new CompanyInfoExtDto.
     *
     * @param resources the CompanyInfoExtDto object to create
     */
    void create(CompanyInfoExtDto resources);

    /**
     * Updates the CompanyInfoExtDto object.
     *
     * @param resources the CompanyInfoExtDto object to update
     */
    void update(CompanyInfoExtDto resources);
}
