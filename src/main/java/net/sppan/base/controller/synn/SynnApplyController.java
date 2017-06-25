package net.sppan.base.controller.synn;/**
 * Created by windsor on 2017/6/24.
 */

import net.sppan.base.common.DateUtil;
import net.sppan.base.common.JsonResult;
import net.sppan.base.controller.BaseController;
import net.sppan.base.entity.SynnApply;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
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
//		SimpleSpecificationBuilder<Resource> builder = new SimpleSpecificationBuilder<Resource>();
//		String searchText = request.getParameter("searchText");
//		if(StringUtils.isNotBlank(searchText)){
//			builder.add("name", Operator.likeAll.name(), searchText);
//		}
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
            synnApply.setApplyid(user.getId());
            synnApply.setApplystatus(0);//the status of apply is default 0,it express unconfirm
            synnApply.setApplytype(0);
            synnApply.setHours(hours);
            synnApply.setUserid(user.getId());
            iApplyService.saveOrUpdate(synnApply);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }
}
