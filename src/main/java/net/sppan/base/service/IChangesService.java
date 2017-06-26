package net.sppan.base.service;

import net.sppan.base.entity.Resource;
import net.sppan.base.entity.SynnChange;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.service.support.IBaseService;

/**
 * Created by windsorl on 2017/6/26.
 */
public interface IChangesService extends IBaseService<SynnChange, Integer> {
    /**
     * 修改或者新增
     * @param resource
     */
    void saveOrUpdate(SynnChange SynnChange);

    SynnChange findByUserid(Integer integer);


}
