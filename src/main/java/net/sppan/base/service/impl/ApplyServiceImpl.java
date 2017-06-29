package net.sppan.base.service.impl;/**
 * Created by windsor on 2017/6/24.
 */

import com.alibaba.fastjson.JSONObject;
import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.dao.IEmailDao;
import net.sppan.base.dao.ISynnApplydao;
import net.sppan.base.dao.IUserDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnChangeHours;
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
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import sun.security.provider.MD5;

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

    /**
     * 员工申请加班或者调休，员工填写信息后，后台员工账号发送信息与审批人以及其他人
     * 邮箱发送成功之后，本地数据库更新相关信息
     * @param synnEmail
     * @param synnapp
     */
    @Override
    public void sendmailAndSaveinfo(SynnEmails synnEmail, SynnApply synnapp) {
        if(synnapp.getApplyid() == null) {
            User usersend = iUserDao.findById(synnapp.getUserid().intValue());
            //更新后台数据信息
            if(synnEmail.getSendto().contains(",")==true){
                String [] emails = synnEmail.getSendto().split(",");
                for(int i=0;i<emails.length;i++){
                    SynnEmails synnEmails = new SynnEmails();
                  //  User user = iUserDao.findById(synnapp.getUserid().intValue());
                    User user = iUserDao.findByEmail(emails[i]);
                    BeanUtils.copyProperties(synnEmail,synnEmails);
                    synnEmails.setSendto(emails[i]);
                    synnEmails.setTouserid(user.getId().longValue());
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

            //发送邮件
            JSONObject json = new JSONObject();
            json.put("from",usersend.getEmail());
            json.put("password", MD5Utils.convertMD5(usersend.getPassword()));
            json.put("to", synnEmail.getSendto().replace(",",";"));
            json.put("subject", synnEmail.getSubject());
            json.put("content", synnEmail.getContent());
            json.put("nickname",usersend.getNickName());
            json.put("hours",synnapp.getHours());
            String result = restTemplate.postForObject(emailserviceurl, json, String.class);
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

    /**
     * 经理审批员工申请，申请成功后
     * 邮箱发送成功之后，本地数据库更新相关信息
     * @param synnEmail
     * @param synnapp
     */
    @Override
    public void sendmailAndApprove(List<SynnEmails> synnEmail, SynnApply synnapp) {
        User usersend = iUserDao.findById(synnapp.getUserid().intValue());
        //更新申请状态，更新邮件列表，更新员工换休时间表
        update(synnapp);
        //获取员工换休加班信息
        int overtimehour = iSynnApplydao.findUsersCount(synnapp.getUserid(),0);
        int askforleave = iSynnApplydao.findUsersCount(synnapp.getUserid(),1);
        SynnChangeHours changeHours = iChangesService.findByUserid(synnapp.getUserid().longValue());
        int restHours = 0;
        if(changeHours == null){
            restHours =0;
        }else{
            restHours = iChangesService.findByUserid(usersend.getId().longValue()).getHours();
        }
        JSONObject json = new JSONObject();
        json.put("from", synnEmail.get(0).getSendfrom());
        json.put("password",MD5Utils.convertMD5(usersend.getPassword()));
        json.put("to",synnEmail.get(0).getSendto()); //默认只发送用户回复的邮件
        json.put("subject",synnEmail.get(0).getSubject());
        json.put("content",synnEmail.get(0).getContent());
        json.put("nickname",usersend.getNickName());
        json.put("hours",synnapp.getHours());
        json.put("overtimehour",overtimehour);
        json.put("askforleave",askforleave);
        json.put("restHours",restHours);
        json.put("system","system");//此标注为需要发送系统邮件

        if(synnapp.getApplystatus()==1 ){  //1。同意则修改员工change表，加班申请累加，调休申请减 2。发送审批邮件
            for(int i =0;i<synnEmail.size();i++){
             //   synnEmail.get(i).setSendfrom(usersend.getEmail());
                iEmailService.save(synnEmail.get(i));
            }
            iChangesService.updateByUserId(synnapp.getApplytype()==0?synnapp.getHours():-synnapp.getHours(),synnapp.getUserid().longValue());
            String result = restTemplate.postForObject(emailserviceurl, json, String.class);
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
