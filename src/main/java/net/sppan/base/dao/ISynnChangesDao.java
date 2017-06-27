package net.sppan.base.dao;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnChangeHours;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by windsorl on 2017/6/26.
 */
public interface ISynnChangesDao extends IBaseDao<SynnChangeHours,Integer> {

    SynnChangeHours findByUserid(Long userId);

    @Modifying
    @Query("update SynnChangeHours U  set hours= ?1 where userid = ?2")
    int updateByUserId(Integer hours,Long userId);
}