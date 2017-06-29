package net.sppan.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by windsor on 2017/6/25.
 */

@Entity
@Table(name = "tb_emails")
public class SynnEmails  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="emailId")
    private Long id;
    private Long userid;
    @Column(name="touserid")
    private Long touserid;
    private String sendfrom;
    private String sendto;
    private String subject;
    private String content;
    private Integer approvestatus;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date sendtime = new Date();

    private Integer mailtype;

    public Integer getApprovestatus() {
        return approvestatus;
    }

    public void setApprovestatus(Integer approvestatus) {
        this.approvestatus = approvestatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getTouserid() {
        return touserid;
    }

    public void setTouserid(Long touserid) {
        this.touserid = touserid;
    }

    public String getSendfrom() {
        return sendfrom;
    }

    public void setSendfrom(String sendfrom) {
        this.sendfrom = sendfrom;
    }

    public String getSendto() {
        return sendto;
    }

    public void setSendto(String sendto) {
        this.sendto = sendto;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getMailtype() {
        return mailtype;
    }

    public void setMailtype(Integer mailtype) {
        this.mailtype = mailtype;
    }
}