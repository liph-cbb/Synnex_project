package net.sppan.base.controller.synn;/**
 * Created by windsor on 2017/6/25.
 */

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
