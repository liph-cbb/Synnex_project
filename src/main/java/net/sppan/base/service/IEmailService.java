package net.sppan.base.service;/**
 * Created by windsor on 2017/6/25.
 */

import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnEmails;
import net.sppan.base.entity.User;
import net.sppan.base.service.support.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author
 * @create 2017-06-25 12:22
 **/
public interface IEmailService  extends IBaseService<SynnEmails, Integer> {

    void saveOrUpdate(SynnEmails synnEmails);
    Page<SynnEmails> findAll(Pageable pageable);
    Page<SynnEmails> findAllByUserid(int searchText, PageRequest pageRequest);
}
