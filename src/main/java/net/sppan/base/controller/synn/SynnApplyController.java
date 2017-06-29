package net.sppan.base.controller.synn;

import net.sppan.base.common.DateUtil;
import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnChangeHours;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.entity.User;
import net.sppan.base.service.IApplyService;
import net.sppan.base.service.IChangesService;
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
    @Autowired
    private IChangesService iChangesService;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "admin/apply/applyindex";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Page<SynnApply> list(
            @RequestParam(value = "searchText", required = false) String searchText,ModelMap map
    ) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
        map.put("userid",user.getId());
        // Page<SynnApply> page = iApplyService.findAllByUserid(user.getId(), getPageRequest());
        Page<SynnApply> page = iApplyService.findAllByUseridOrApproveuserid(user.getId().longValue(), user.getId().longValue(), getPageRequest());
        return page;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        List<User> list = iUserService.findAll();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
        User leader = iUserService.findById(user.getLeaderid());
        map.put("leader", leader);
        map.put("list", list);
        return "admin/apply/applyform";
    }

    @RequestMapping(value = "/addchanges", method = RequestMethod.GET)
    public String addchanges(ModelMap map) {
        List<User> list = iUserService.findAll();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = iUserService.find((Integer) session.getAttribute("userid"));
        User leader = iUserService.findById(user.getLeaderid());
        map.put("leader", leader);
        map.put("list", list);
        return "admin/apply/applyChangeform";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap map) {
        SynnApply resource = iApplyService.findByApplyid(id.longValue());
        map.put("resource", resource);
        List<User> list = iUserService.findAll();
        map.put("list", list);
        if (resource.getApplytype() == 0) {
            return "admin/apply/applyformedit";
        }
        return "admin/apply/applychangeedit";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(@PathVariable Integer id, ModelMap map) {
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        Long hours = DateUtil.dateDiff(sdf.format(synnApply.getBegindate()), sdf.format(synnApply.getEnddate()),
                "yyyy-MM-dd hh:mm:ss", "h");

        try {
                    /*验证工时*/
            if (synnApply.getApplytype() == 1) {
                SynnChangeHours changeHours = iChangesService.findByUserid(user.getId().longValue());
                if (hours > changeHours.getHours()) {
                    return JsonResult.failure("超过可调休工时,请重新填写");
                }
            }
            synnApply.setApplystatus(0);//the status of apply is default 0,it express unconfirm
            //  synnApply.setApplytype(synnApply.getApplytype()); //前段取默认值
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

            String subjects = (synnApply.getApplytype() == 0 ? "申请加班" : "申请调休");
            synnEmails.setSubject(subjects);
            synnEmails.setSendtime(new Date());
            synnEmails.setUserid(user.getId().longValue());
            synnEmails.setMailtype(synnApply.getApplytype()); //前段取默认值

            iApplyService.sendmailAndSaveinfo(synnEmails, synnApply);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    /**
     * 审批controller方法
     * 调休需验证员工的拥有可换休日
     * 审批状态：0=未审批 1=已审批 2 拒绝
     *
     * @param synnApply
     * @param map
     *
     * @return
     */
    @RequestMapping(value = {"/addapprove"}, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addapprove(SynnApply synnApply, ModelMap map) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            User user = iUserService.find((Integer) session.getAttribute("userid")); //审批的领导对象
            User userapply = iUserService.find(synnApply.getUserid().intValue());//申请人对象

            /*验证是否已经审批过*/
            SynnApply synnApply_new = iApplyService.findByApplyid(synnApply.getApplyid());
            if (synnApply_new.getApplystatus() != 0) {
                return JsonResult.failure("此申请已经审批过，不能进行修改");
            }

            /*验证工时*/
            if (synnApply.getApplytype() == 1) {
                SynnChangeHours changeHours = iChangesService.findByUserid(userapply.getId().longValue());
                if (synnApply.getHours() > changeHours.getHours()) {
                    return JsonResult.failure("超过可调休工时,请重新填写");
                }
            }

            synnApply_new.setApplystatus(synnApply.getApplystatus());
            synnApply_new.setApproveReason(synnApply.getApproveReason());
            synnApply_new.setHours(synnApply.getHours());
            synnApply_new.setLast_update_datetime(new Date());
            String subjects = (synnApply.getApplytype() == 0 ? "申请加班审批结果" : "申请调休审批结果");

            /*邮件对象列表*/
            List<SynnEmails> synnEmailsList = new ArrayList<SynnEmails>();
            //审批人回复邮件
            SynnEmails synnEmails_03 = new SynnEmails();
            synnEmails_03.setSendfrom(user.getEmail());
            synnEmails_03.setTouserid(user.getId().longValue());  //发送给审批人
            synnEmails_03.setContent(synnApply.getApproveReason());
            synnEmails_03.setMailtype(synnApply.getApplytype());
            synnEmails_03.setSubject(subjects);
            synnEmails_03.setSendtime(new Date());
            synnEmails_03.setSendto(userapply.getEmail());
            synnEmails_03.setUserid(synnApply.getUserid());
            synnEmails_03.setApprovestatus(synnApply.getApplystatus());

            //系统邮件
            SynnEmails synnEmails_01 = new SynnEmails();
            synnEmails_01.setSendfrom("系统邮箱");
            synnEmails_01.setTouserid(user.getId().longValue());  //发送给审批人
            synnEmails_01.setContent(synnApply.getApproveReason());
            synnEmails_01.setMailtype(synnApply.getApplytype());
            synnEmails_01.setSubject("系统邮件");
            synnEmails_01.setSendtime(new Date());
            synnEmails_01.setSendto(user.getEmail());
            synnEmails_01.setUserid(synnApply.getUserid());
            synnEmails_01.setApprovestatus(synnApply.getApplystatus());

            SynnEmails synnEmails_02 = new SynnEmails();
            synnEmails_02.setSendfrom("系统邮箱");
            synnEmails_02.setTouserid(synnApply.getUserid());  //发送给申请人
            synnEmails_02.setContent(synnApply.getApproveReason());
            synnEmails_02.setMailtype(synnApply.getApplytype()); //1为审批加班邮件
            synnEmails_02.setSubject("系统邮件");
            synnEmails_02.setSendtime(new Date());
            synnEmails_02.setSendto(userapply.getEmail());
            synnEmails_02.setUserid(synnApply.getUserid());
            synnEmails_02.setApprovestatus(synnApply.getApplystatus());

            synnEmailsList.add(synnEmails_03);
            if(synnApply.getApplystatus()== 1) {  //审批完成后才发送通知邮件
                synnEmailsList.add(synnEmails_01);
                synnEmailsList.add(synnEmails_02);
            }


            iApplyService.sendmailAndApprove(synnEmailsList, synnApply_new);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
}
