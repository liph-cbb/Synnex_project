package net.sppan.base.entity;

import net.sppan.base.entity.support.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by windsorl on 2017/6/26.
 */
@Entity
@Table(name = "tb_change_hours")
public class SynnChange extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    /**
     *可换休时间
     **/
    private int hours;
    /**
     *用户编号
     **/
    private int userid;
    /**
     * 上次更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdatetime = new Date();

}
