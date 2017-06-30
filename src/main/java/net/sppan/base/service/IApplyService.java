package net.sppan.base.service;/**
 * Created by windsor on 2017/6/24.
 */

import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IApplyService extends IBaseService<SynnApply, Integer>{

    Page<SynnApply>  findByUseridOrApplyid(int id, Pageable pageable );
    @Override
    Page<SynnApply> findAll(Pageable pageable);

    Page<SynnApply> findAllByUserid(int searchText, PageRequest pageRequest);

    void saveOrUpdate(SynnApply synnApply);

    void sendmailAndSaveinfo(SynnEmails synnEmail, SynnApply synnapp);
    Page<SynnApply> findAllByUseridOrApproveuserid(Long userid,Long touserid,PageRequest pageRequest);
    SynnApply findByApplyid(Long applyId);

     void sendmailAndApprove(List<SynnEmails> synnEmail, SynnApply synnapp);

    int deleteByApplyid(Long applyId);

    int findUsersCount(Long userId, Integer applytype);
    int countByUseridAndApplytypeAndAndApplystatus(Long userId, Integer applytype, Integer approveStatus);
}


