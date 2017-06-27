package net.sppan.base.service.impl;

import net.sppan.base.dao.ISynnChangesDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnChangeHours;
import net.sppan.base.service.IChangesService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by windsorl on 2017/6/26.
 */
@Service
public class ChangesServiceImpl extends BaseServiceImpl<SynnChangeHours,Integer> implements IChangesService {

    @Autowired
    private ISynnChangesDao iSynnChangesDao;

    @Override
    public SynnChangeHours findByUserid(Long userId) {
        return iSynnChangesDao.findByUserid(userId);
    }

    @Override
    public int updateByUserId(Integer hours, Long userId) {
        SynnChangeHours synnChangeHours = iSynnChangesDao.findByUserid(userId);
        if(synnChangeHours == null){
            SynnChangeHours synnChangeHourss = new SynnChangeHours();
            synnChangeHourss.setUserid(userId);
            synnChangeHourss.setHours(hours);
            synnChangeHourss.setLastupdatetime(new Date());
            iSynnChangesDao.save(synnChangeHourss);
            return 1;
        }
        int  addHours = hours+synnChangeHours.getHours();
        return  iSynnChangesDao.updateByUserId(addHours,userId);
    }

    @Override
    public IBaseDao<SynnChangeHours, Integer> getBaseDao() {
        return this.iSynnChangesDao;
    }
}
