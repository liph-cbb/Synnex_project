package net.sppan.base.controller.synn;/**
 * Created by windsor on 2017/6/25.
 */

import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.entity.User;
import net.sppan.base.service.IEmailService;
import net.sppan.base.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李杨洲
 * @create 2017-06-25 23:15
 **/
@Controller
@RequestMapping("/email")
public class SynnEmailController extends BaseController {

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = {"/", "/index"})
    public String emaiidex(){
      return "/admin/email/emailindex";
      //  return "/admin/email/mailbox";
    }
    @RequestMapping(value = "/queryhour")
    @ResponseBody
    public JsonResult queryhour(){
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            User user = iUserService.find((Integer) session.getAttribute("userid"));
            List<SynnEmails> emailsList = new ArrayList<SynnEmails>();
            SynnEmails emails_user = new SynnEmails();
            emails_user.setSendfrom(user.getEmail());
            emails_user.setSendto("系统邮箱");
            emails_user.setSendtime(new Date());
            emails_user.setUserid(user.getId().longValue());
            emails_user.setSubject("系统查询");
            emails_user.setMailtype(3);
            emails_user.setContent("请查询本人加班及换休状况");
            emailsList.add(emails_user);


            SynnEmails emails_sysetm = new SynnEmails();
            emails_sysetm.setSendfrom("系统邮箱");
            emails_sysetm.setSendto(user.getEmail());
            emails_sysetm.setSendtime(new Date());
            emails_sysetm.setTouserid(user.getId().longValue());
            emails_sysetm.setSubject("系统查询");
            emails_sysetm.setMailtype(3);
           // emails_user.setContent("请协助查询本人加班及换休");
            emailsList.add(emails_user);
            emailsList.add(emails_sysetm);
            String result = iEmailService.sendQueryMessage(user.getId().longValue(),emailsList);
        }catch (Exception e){
            return JsonResult.failure("查询邮件失败，请联系管理员");
        }
         return JsonResult.success();

    }

    @RequestMapping("/list")
    @ResponseBody
    public Page<SynnEmails> list(
            @RequestParam(value = "searchText", required = false) String searchText
    ) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
        Page<SynnEmails> page = iEmailService.findAllByUseridOrTouserid(user.getId().longValue(),user.getId().longValue(),getPageRequest());
        return page;
    }
}
