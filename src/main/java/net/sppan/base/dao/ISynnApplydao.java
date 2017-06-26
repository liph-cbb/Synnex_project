package net.sppan.base.dao;/**
 * Created by windsor on 2017/6/24.
 */

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @create 2017-06-24 16:24
 **/
@Repository
public interface ISynnApplydao extends IBaseDao<SynnApply, Integer>  {

    Page<SynnApply>  findByUserid(int id, Pageable pageable );

    Page<SynnApply> findAllByUserid(int searchText, Pageable pageable);

    Page<SynnApply> findAllByUseridOrApproveuserid(Long userid,Long touserid,Pageable pageable);
}
