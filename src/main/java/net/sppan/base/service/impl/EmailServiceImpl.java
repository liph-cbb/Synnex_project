package net.sppan.base.service.impl;/**
 * Created by windsor on 2017/6/25.
 */

import com.alibaba.fastjson.JSONObject;
import net.sppan.base.dao.IEmailDao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnChangeHours;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.entity.User;
import net.sppan.base.service.IApplyService;
import net.sppan.base.service.IChangesService;
import net.sppan.base.service.IEmailService;
import net.sppan.base.service.IUserService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author 李杨洲
 * @create 2017-06-25 12:24
 **/
@Service
public class EmailServiceImpl extends BaseServiceImpl<SynnEmails, Integer> implements IEmailService {
   @Autowired
    private IEmailDao iEmailDao;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IApplyService iApplyService;
    @Autowired
    private IChangesService changesService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${email.service.query}")
    private String service_query_url;


    @Override
    public void saveOrUpdate(SynnEmails synnEmails) {
        iEmailDao.save(synnEmails);
    }

    @Override
    public String sendQueryMessage(Long userId) {
        User usersend = iUserService.findById(userId.intValue());
        //查询用户本身已经申请加班批准的时间数
        int overtimehour = iApplyService.findUsersCount(usersend.getId().longValue(),0);
        int askforleave = iApplyService.findUsersCount(usersend.getId().longValue(),1);
        SynnChangeHours changeHours = changesService.findByUserid(usersend.getId().longValue());
        int restHours = 0;
        if(changeHours == null){
            restHours =0;
        }else{
            restHours = changesService.findByUserid(usersend.getId().longValue()).getHours();
        }
        //默认给系统发送邮件
        JSONObject json = new JSONObject();
        json.put("from",usersend.getEmail());
        json.put("password",usersend.getPassword());
        json.put("to",usersend.getEmail());
        json.put("subject", "系统查询邮件");
        json.put("content", "请查询本人加班和换休时间");
        json.put("nickname",usersend.getNickName());
        json.put("overtimehour",overtimehour);
        json.put("askforleave",askforleave);
        json.put("restHours",restHours);
        String result = restTemplate.postForObject(service_query_url, json, String.class);
        if ("success".equals(result)) {
            //邮箱信息通过API拉去还是插入表中
        }
        return result;
    }

    @Override
    public Page<SynnEmails> findBySendfromOrSendto(String email, PageRequest pageRequest) {
        return iEmailDao.findBySendfromOrSendto(email,pageRequest);
    }

    @Override
    public Page<SynnEmails> findAllBySendfromOrSendto(String email, PageRequest pageRequest) {
        return iEmailDao.findAllBySendfromOrSendto(email,pageRequest);
    }

    @Override
    public Page<SynnEmails> findByUserid(Integer integer, PageRequest pageRequest) {
        return iEmailDao.findByUserid(integer,pageRequest);
    }

    @Override
    public Page<SynnEmails> findByToUserid(Integer integer, PageRequest pageRequest) {
        return iEmailDao.findByTouserid(integer,pageRequest);
    }

    @Override
    public Page<SynnEmails> findAllByUseridOrTouserid(Long userId,Long toUserId,PageRequest pageRequest){
        return iEmailDao.findAllByUseridOrTouserid(userId,toUserId,pageRequest);
    }

    @Override
    public IBaseDao<SynnEmails, Integer> getBaseDao() {
        return this.iEmailDao;
    }
}
