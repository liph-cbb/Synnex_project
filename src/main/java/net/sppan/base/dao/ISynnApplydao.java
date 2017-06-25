package net.sppan.base.dao;/**
 * Created by windsor on 2017/6/24.
 */

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.Resource;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.SynnApply;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author
 * @create 2017-06-24 16:24
 **/
public interface ISynnApplydao extends IBaseDao<SynnApply, Integer>  {

    Page<SynnApply>  findByUserid(int id, Pageable pageable );

    Page<SynnApply> findAllByUserid(int searchText, Pageable pageable);
}
