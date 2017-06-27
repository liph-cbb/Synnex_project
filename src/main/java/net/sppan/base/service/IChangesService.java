package net.sppan.base.service;


import net.sppan.base.entity.SynnChangeHours;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by windsorl on 2017/6/26.
 */

public interface IChangesService extends IBaseService<SynnChangeHours, Integer> {
    SynnChangeHours findByUserid(Long userId);

    @Query("update SynnChangeHours U  set hours= ?1 where userid = ?2")
    int updateByUserId(Integer hours,Long userId);
}
