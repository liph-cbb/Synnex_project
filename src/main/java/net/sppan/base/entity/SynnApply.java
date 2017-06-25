package net.sppan.base.entity;/**
 * Created by windsor on 2017/6/24.
 */

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.xml.internal.rngom.parse.host.Base;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
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
    private Integer applyid;

    @Column(name="user_id")
    private Integer userid;

   @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applydatetime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date begindate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date enddate;

    private Long hours;

    @Column(name="apply_type")
    private int applytype;

    private int approve_user_id;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date last_update_datetime;

    private int applystatus;

    private String applyReason;

    @Transient
    private int emailusersid;
    private String emails;

    public int getEmailusersid() {
        return emailusersid;
    }

    public void setEmailusersid(int emailusersid) {
        this.emailusersid = emailusersid;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public int getApplytype() {
        return applytype;
    }

    public void setApplytype(int applytype) {
        this.applytype = applytype;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getApplyid() {
        return applyid;
    }

    public void setApplyid(Integer applyid) {
        this.applyid = applyid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
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

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public int getApprove_user_id() {
        return approve_user_id;
    }

    public void setApprove_user_id(int approve_user_id) {
        this.approve_user_id = approve_user_id;
    }

    public Date getLast_update_datetime() {
        return last_update_datetime;
    }

    public void setLast_update_datetime(Date last_update_datetime) {
        this.last_update_datetime = last_update_datetime;
    }

    public int getApplystatus() {
        return applystatus;
    }

    public void setApplystatus(int applystatus) {
        this.applystatus = applystatus;
    }
}
