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
import java.util.ArrayList;
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
       // Page<SynnApply> page = iApplyService.findAllByUserid(user.getId(), getPageRequest());
        Page<SynnApply> page = iApplyService.findAllByUseridOrApproveuserid(user.getId().longValue(),user.getId().longValue(),getPageRequest());
        return page;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        List<User> list = iUserService.findAll();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
        User leader= iUserService.findById(user.getLeaderid());
        map.put("leader",leader);
        map.put("list", list);
        return "admin/apply/applyform";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        SynnApply resource =iApplyService.findByApplyid(id.longValue());
        map.put("resource", resource);
        List<User> list = iUserService.findAll();
        map.put("list", list);
        return "admin/apply/applyformedit";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(@PathVariable Integer id,ModelMap map) {
        try {
            iApplyService.deleteByApplyid(id.longValue());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @RequestMapping(value = {"/addedit"}, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addedit(SynnApply synnApply, ModelMap map) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
       // User touser = iUserService.findByEmail(user.getEmail());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Long hours = DateUtil.dateDiff(sdf.format(synnApply.getBegindate()), sdf.format(synnApply.getEnddate()),
                "yyyy-MM-dd hh:mm:ss", "h");
        try {
            synnApply.setApplystatus(0);//the status of apply is default 0,it express unconfirm
            synnApply.setApplytype(0);
            synnApply.setHours(hours.intValue());
            synnApply.setUserid(user.getId().longValue());
            synnApply.setLast_update_datetime(new Date());
            synnApply.setApplydatetime(new Date());
            synnApply.setApproveuserid(user.getLeaderid().longValue());

            SynnEmails synnEmails = new SynnEmails();
            synnEmails.setSendfrom(user.getEmail());
         //   synnEmails.setTouserid(touser.getId().longValue());
            synnEmails.setContent(synnApply.getApplyReason());
            synnEmails.setSendto(synnApply.getEmails());
            synnEmails.setSubject("申请加班");
            synnEmails.setSendtime(new Date());
            synnEmails.setUserid(user.getId().longValue());
            synnEmails.setMailtype(0); //默认0为申请加班邮件

            iApplyService.sendmailAndSaveinfo(synnEmails, synnApply);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }


    @RequestMapping(value = {"/addapprove"}, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addapprove(SynnApply synnApply, ModelMap map){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
        User userapply = iUserService.find(synnApply.getUserid().intValue());
        SynnApply synnApply_new  = iApplyService.findByApplyid(synnApply.getApplyid());
        synnApply_new.setApplystatus(synnApply.getApplystatus());
        synnApply_new.setApproveReason(synnApply.getApproveReason());
        synnApply_new.setHours(synnApply.getHours());
        synnApply_new.setLast_update_datetime(new Date());

        List<SynnEmails> synnEmailsList = new ArrayList<SynnEmails>();
        SynnEmails synnEmails_01 = new SynnEmails();
        synnEmails_01.setTouserid(user.getId().longValue());  //发送给审批人
        synnEmails_01.setContent(synnApply.getApproveReason());
        synnEmails_01.setMailtype(1); //1为审批加班邮件
        synnEmails_01.setSubject("加班审批");
        synnEmails_01.setSendtime(new Date());
        synnEmails_01.setSendto(user.getEmail());
        synnEmails_01.setUserid(synnApply.getUserid());

        SynnEmails synnEmails_02 = new SynnEmails();
        synnEmails_02.setTouserid(synnApply.getUserid());  //发送给申请人
        synnEmails_02.setContent(synnApply.getApproveReason());
        synnEmails_02.setMailtype(1); //1为审批加班邮件
        synnEmails_02.setSubject("加班审批");
        synnEmails_02.setSendtime(new Date());
        synnEmails_02.setSendto(userapply.getEmail());
        synnEmails_02.setUserid(synnApply.getUserid());

        synnEmailsList.add(synnEmails_01);
        synnEmailsList.add(synnEmails_02);

        iApplyService.sendmailAndApprove(synnEmailsList,synnApply_new);
        return JsonResult.success();
    }
}
