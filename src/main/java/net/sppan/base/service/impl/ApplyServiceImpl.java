package net.sppan.base.service.impl;/**
 * Created by windsor on 2017/6/24.
 */

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.rngom.parse.host.Base;
import net.sppan.base.dao.IEmailDao;
import net.sppan.base.dao.ISynnApplydao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.entity.User;
import net.sppan.base.service.IApplyService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * @author 李杨洲
 * @create 2017-06-24 16:33
 **/
@Service
@Transactional
public class ApplyServiceImpl extends BaseServiceImpl<SynnApply,Integer> implements IApplyService{

    @Autowired
    private ISynnApplydao iSynnApplydao;
    @Autowired
    private IEmailDao iEmailDao;
    @Autowired
    RestTemplate restTemplate;

    @Value("${email.service}")
    private String emailserviceurl;


    @Override
    public Page<SynnApply> findByUseridOrApplyid(int id, Pageable pageable) {
        return iSynnApplydao.findAllByUserid(id,pageable);
    }

    @Override
    public Page<SynnApply> findAllByUserid(int searchText, PageRequest pageRequest) {
        return iSynnApplydao.findAllByUserid(searchText,pageRequest);
    }

    @Override
    public void saveOrUpdate(SynnApply synnApply) {
         update(synnApply);
    }

    @Override
    public void sendmailAndSaveinfo(SynnEmails synnEmail, SynnApply synnapp, User synn_users) {
        JSONObject json = new JSONObject();
        json.put("from",synn_users.getEmail());
        json.put("password",synn_users.getPassword());
        json.put("to",synnEmail.getSendto());
        json.put("subject",synnEmail.getSubject());
        json.put("content",synnEmail.getContent());
        String  s =restTemplate.postForObject(emailserviceurl,json,String.class);
        System.out.println(s);
        if(s.equals("success")){
            iEmailDao.save(synnEmail);
            iSynnApplydao.save(synnapp);
        }
    }

    @Override
    public IBaseDao<SynnApply, Integer> getBaseDao() {
        return  this.iSynnApplydao;
    }
}
