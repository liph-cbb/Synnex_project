package net.sppan.base.service.impl;/**
 * Created by windsor on 2017/6/24.
 */

import com.alibaba.fastjson.JSONObject;
import net.sppan.base.dao.IEmailDao;
import net.sppan.base.dao.ISynnApplydao;
import net.sppan.base.dao.IUserDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.entity.User;
import net.sppan.base.service.IApplyService;
import net.sppan.base.service.IChangesService;
import net.sppan.base.service.IEmailService;
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

import java.util.List;

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
    private IEmailService iEmailService;
    @Autowired
    private IChangesService iChangesService;
    @Autowired
    private IUserDao iUserDao;

    @Autowired
    RestTemplate restTemplate;

    @Value("${email.service}")
    private String emailserviceurl;
    @Value("${system.email.account}")
    private String emailserviceacc;
    @Value("${system.email.passwd}")
    private String emailservicepwd;

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
    public void sendmailAndSaveinfo(SynnEmails synnEmail, SynnApply synnapp) {
        if(synnapp.getApplyid() == null) {
            User usersend = iUserDao.findById(synnapp.getUserid().intValue());
            JSONObject json = new JSONObject();
            json.put("from",emailserviceacc);
            json.put("password",emailservicepwd);
            json.put("to", synnEmail.getSendto().replace(",",";"));
            json.put("subject", synnEmail.getSubject());
            json.put("content", synnEmail.getContent());
            json.put("nickname",usersend.getNickName());
            json.put("hours",synnapp.getHours());
            String result = restTemplate.postForObject(emailserviceurl, json, String.class);
            if ("success".equals(result)) {//假如是多人收件
                if(synnEmail.getSendto().contains(",")==true){
                    String [] emails = synnEmail.getSendto().split(",");
                    for(int i=0;i<emails.length;i++){
                        SynnEmails synnEmails = new SynnEmails();
                        User user = iUserDao.findById(synnapp.getUserid().intValue());
                        BeanUtils.copyProperties(synnEmail,synnEmails);
                        synnEmails.setSendto(emails[i]);
                        synnEmails.setTouserid(user.getLeaderid().longValue());
                        if(!"".equals(emails[i])) {
                            iEmailService.saveOrUpdate(synnEmails);
                        }
                    }
                }else{
                    User user=iUserDao.findById(synnapp.getUserid().intValue());
                    synnEmail.setTouserid(user.getLeaderid().longValue());
                    iEmailService.save(synnEmail);
                }
                iSynnApplydao.save(synnapp);
            }
        }else{
            update(synnapp);
        }
    }

    @Override
    public Page<SynnApply> findAllByUseridOrApproveuserid(Long userid, Long touserid, PageRequest pageRequest) {
        return iSynnApplydao.findAllByUseridOrApproveuserid(userid,touserid,pageRequest);
    }

    @Override
    public SynnApply findByApplyid(Long applyId) {
        return iSynnApplydao.findByApplyid(applyId);
    }

    @Override
    public void sendmailAndApprove(List<SynnEmails> synnEmail, SynnApply synnapp) {
        StringBuffer emails = new StringBuffer("");
        for(int i =0;i<synnEmail.size();i++){
            emails.append(synnEmail.get(i).getSendto());
            if(i!=synnEmail.size()-1){
                emails.append(";");
            }
        }
        User usersend = iUserDao.findById(synnapp.getUserid().intValue());
        JSONObject json = new JSONObject();
        json.put("from", emailserviceacc);
        json.put("password", emailservicepwd);
        json.put("to",emails.toString());
        json.put("subject",synnEmail.get(0).getSubject());
        json.put("content",synnEmail.get(0).getContent());
        json.put("nickname",usersend.getNickName());
        json.put("hours",synnapp.getHours());

        String result = restTemplate.postForObject(emailserviceurl, json, String.class);
        if("success".equals(result)){
            update(synnapp);
            for(int i =0;i<synnEmail.size();i++){
                synnEmail.get(i).setSendfrom(emailserviceacc);
                iEmailService.save(synnEmail.get(i));
            }
            if(synnapp.getApplystatus()==1){  //同意则修改员工change表，加班申请累加，调休申请减
                iChangesService.updateByUserId(synnapp.getApplytype()==0?synnapp.getHours():-synnapp.getHours(),synnapp.getUserid().longValue());
            }
        }
    }
    public int deleteByApplyid(Long applyId){
        return iSynnApplydao.deleteByApplyid(applyId);
    }

    @Override
    public int findUsersCount(Long userId, Integer applytype) {
        return iSynnApplydao.findUsersCount(userId,applytype);
    }



    @Override
    public IBaseDao<SynnApply, Integer> getBaseDao() {
        return  this.iSynnApplydao;
    }
}
