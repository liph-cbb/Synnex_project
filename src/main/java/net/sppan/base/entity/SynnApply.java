package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;
import javax.persistence.*;
import java.util.Date;

/**
 * @author 李杨洲
 * @create 2017-06-24 16:10
 **/
@Entity
@Table(name = "tb_apply")
public class SynnApply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "apply_id")
    private Long applyid;

    @Column(name="user_id")
    private Long userid;

   @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applydatetime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date begindate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date enddate;

    private Integer hours;

    @Column(name="apply_type")
    private Integer applytype;

    @Column(name="approve_user_id")
    private Long approveuserid;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date last_update_datetime;

    private Integer applystatus;

    private String applyReason;

    private String approveReason;

    private String emails;

    public String getApproveReason() {
        return approveReason;
    }

    public void setApproveReason(String approveReason) {
        this.approveReason = approveReason;
    }

    public Long getApplyid() {
        return applyid;
    }

    public void setApplyid(Long applyid) {
        this.applyid = applyid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getApplydatetime() {
        return applydatetime;
    }

    public void setApplydatetime(Date applydatetime) {
        this.applydatetime = applydatetime;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getApplytype() {
        return applytype;
    }

    public void setApplytype(Integer applytype) {
        this.applytype = applytype;
    }

    public Long getApproveuserid() {
        return approveuserid;
    }

    public void setApproveuserid(Long approveuserid) {
        this.approveuserid = approveuserid;
    }

    public Date getLast_update_datetime() {
        return last_update_datetime;
    }

    public void setLast_update_datetime(Date last_update_datetime) {
        this.last_update_datetime = last_update_datetime;
    }

    public Integer getApplystatus() {
        return applystatus;
    }

    public void setApplystatus(Integer applystatus) {
        this.applystatus = applystatus;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}
