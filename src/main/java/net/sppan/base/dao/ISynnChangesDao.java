package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnChange;

/**
 * Created by windsorl on 2017/6/26.
 */
public interface ISynnChangesDao extends IBaseDao<SynnChange,Integer> {
    SynnChange findByUserid(Integer integer);
}