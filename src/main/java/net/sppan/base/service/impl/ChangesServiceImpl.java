package net.sppan.base.service.impl;

import net.sppan.base.dao.ISynnChangesDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnChange;
import net.sppan.base.service.IChangesService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by windsorl on 2017/6/26.
 */
public class ChangesServiceImpl extends BaseServiceImpl<SynnChange,Integer> implements IChangesService {

    @Autowired
    private ISynnChangesDao iSynnChangesDao;
    @Override
    public void saveOrUpdate(SynnChange synnChange) {
        iSynnChangesDao.saveAndFlush(synnChange);
    }

    @Override
    public SynnChange findByUserid(Integer integer) {
        return iSynnChangesDao.findByUserid(integer);
    }

    @Override
    public IBaseDao<SynnChange, Integer> getBaseDao() {
        return this.iSynnChangesDao;
    }
}
