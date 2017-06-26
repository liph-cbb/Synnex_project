package net.sppan.base.controller.synn;/**
 * Created by windsor on 2017/6/24.
 */

import net.sppan.base.common.DateUtil;
import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.Resource;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.entity.User;
import net.sppan.base.service.IApplyService;
import net.sppan.base.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 李杨洲
 * @create 2017-06-24 17:14
 **/
@Controller
@RequestMapping("/apply")
public class SynnApplyController extends BaseController {
    @Autowired
    private IApplyService iApplyService;
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "admin/apply/applyindex";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Page<SynnApply> list(
            @RequestParam(value = "searchText", required = false) String searchText
    ) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
        Page<SynnApply> page = iApplyService.findAllByUserid(user.getId(), getPageRequest());

        return page;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        List<User> list = iUserService.findAll();
        map.put("list", list);
        return "admin/apply/applyform";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        SynnApply resource =iApplyService.find(id);
        map.put("resource", resource);
        List<User> list = iUserService.findAll();
        map.put("list", list);
        return "admin/apply/applyform";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(@PathVariable Integer id,ModelMap map) {
        try {
            iApplyService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult edit(SynnApply synnApply, ModelMap map) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Long hours = DateUtil.dateDiff(sdf.format(synnApply.getBegindate()), sdf.format(synnApply.getEnddate()),
                "yyyy-MM-dd hh:mm:ss", "h");
        try {
            synnApply.setApplystatus(0);//the status of apply is default 0,it express unconfirm
            synnApply.setApplytype(0);
            synnApply.setHours(hours);
            synnApply.setUserid(user.getId());
            synnApply.setLast_update_datetime(new Date());
            synnApply.setApplydatetime(new Date());

            SynnEmails synnEmails = new SynnEmails();
            synnEmails.setSendfrom(user.getEmail());
          //  synnEmails.setTouserid(user.getId());
            synnEmails.setContent(synnApply.getApplyReason());
            synnEmails.setSendto(synnApply.getEmails());
            synnEmails.setSubject("申请加班");
            synnEmails.setSendtime(new Date());
            synnEmails.setUserid(user.getId());
            synnEmails.setMailtype(0); //默认0为申请加班邮件
            iApplyService.sendmailAndSaveinfo(synnEmails, synnApply, user);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
}
