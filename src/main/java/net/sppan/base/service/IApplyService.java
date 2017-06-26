package net.sppan.base.service;/**
 * Created by windsor on 2017/6/24.
 */

import net.sppan.base.entity.*;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public interface IApplyService extends IBaseService<SynnApply, Integer>{

    Page<SynnApply>  findByUseridOrApplyid(int id, Pageable pageable );
    @Override
    Page<SynnApply> findAll(Pageable pageable);

    Page<SynnApply> findAllByUserid(int searchText, PageRequest pageRequest);

    void saveOrUpdate(SynnApply synnApply);

    public void sendmailAndSaveinfo(SynnEmails synnEmail, SynnApply synnapp, User synn_users);
    Page<SynnApply> findAllByUseridOrApproveuserid(Long userid,Long touserid,PageRequest pageRequest);
}


