package net.sppan.base.controller.synn;/**
 * Created by windsor on 2017/6/25.
 */

import net.sppan.base.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 李杨洲
 * @create 2017-06-25 23:15
 **/
@Controller
@RequestMapping("/email")
public class SynnEmailController {

    @Autowired
    private IEmailService iEmailService;

    @RequestMapping(value = {"/", "/index"})
    public String emaiidex(){
        return "/admin/email/emailindex";
    }

}
