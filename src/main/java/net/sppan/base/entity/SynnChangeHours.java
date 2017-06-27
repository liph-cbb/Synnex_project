package net.sppan.base.entity;

import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by windsorl on 2017/6/26.
 */
@Entity
@Table(name = "tb_change_hours")
public class SynnChangeHours extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    /**
     *可换休时间
     **/
    private Integer hours;
    /**
     *用户编号
     **/
    private Long userid;
    /**
     * 上次更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatetime = new Date();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }
}
