package net.sppan.base.service.impl;/**
 * Created by windsor on 2017/6/25.
 */

import net.sppan.base.dao.IEmailDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.service.IEmailService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author 李杨洲
 * @create 2017-06-25 12:24
 **/
@Service
public class EmailServiceImpl extends BaseServiceImpl<SynnEmails, Integer> implements IEmailService {
   @Autowired
    private IEmailDao iEmailDao;


    @Override
    public void saveOrUpdate(SynnEmails synnEmails) {

    }

    @Override
    public Page<SynnEmails> findAllByUserid(int searchText, PageRequest pageRequest) {
        return iEmailDao.findByUserid(searchText,pageRequest);

    }

    @Override
    public IBaseDao<SynnEmails, Integer> getBaseDao() {
        return this.iEmailDao;
    }
}
