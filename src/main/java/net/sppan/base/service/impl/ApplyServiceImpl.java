package net.sppan.base.service.impl;/**
 * Created by windsor on 2017/6/24.
 */

import com.alibaba.fastjson.JSONObject;
import net.sppan.base.dao.IEmailDao;
import net.sppan.base.dao.ISynnApplydao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.entity.User;
import net.sppan.base.service.IApplyService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
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
        if(synnapp.getApplyid() == null) {
            JSONObject json = new JSONObject();
            json.put("from", synn_users.getEmail());
            json.put("password", "Liyangzhou115");
            json.put("to", synnEmail.getSendto().replace(",",";"));
            json.put("subject", synnEmail.getSubject());
            json.put("content", synnEmail.getContent());
            String s = restTemplate.postForObject(emailserviceurl, json, String.class);
            if (s.equals("success")) {//假如是多人收件
                if(synnEmail.getSendto().contains(",")){
                    String [] emails = synnEmail.getSendto().split(",");
                    for(int i=0;i<emails.length;i++){
                        SynnEmails synnEmails = new SynnEmails();
                        BeanUtils.copyProperties(synnEmail,synnEmails);
                        synnEmails.setSendto(emails[i]);
                        if(!"".equals(emails[i])) {
                            iEmailDao.save(synnEmails);
                        }
                    }
                }
                iSynnApplydao.save(synnapp);
            }
        }else{
            update(synnapp);
        }
    }

    @Override
    public IBaseDao<SynnApply, Integer> getBaseDao() {
        return  this.iSynnApplydao;
    }
}
