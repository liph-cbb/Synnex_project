package net.sppan.base.dao;/**
 * Created by windsor on 2017/6/25.
 */

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.entity.SynnEmails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 李杨洲
 * @create 2017-06-25 12:20
 **/
@Repository
public interface IEmailDao extends IBaseDao<SynnEmails, Integer> {
    Page<SynnEmails>  findBySendfromOrSendto(String email, Pageable pageable );

    Page<SynnEmails> findAllBySendfromOrSendto(String email,Pageable pageable);

    Page<SynnEmails> findByUserid(Integer integer,Pageable pageable);
    Page<SynnEmails> findByTouserid(Integer integer,Pageable pageable);
}
