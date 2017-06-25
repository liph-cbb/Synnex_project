package net.sppan.base.service.impl;/**
 * Created by windsor on 2017/6/24.
 */

import com.sun.xml.internal.rngom.parse.host.Base;
import net.sppan.base.dao.ISynnApplydao;
import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.SynnApply;
import net.sppan.base.service.IApplyService;
import net.sppan.base.service.support.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author 李杨洲
 * @create 2017-06-24 16:33
 **/
@Service
public class ApplyServiceImpl extends BaseServiceImpl<SynnApply,Integer> implements IApplyService{

   @Autowired
    private ISynnApplydao iSynnApplydao;


    @Override
    public Page<SynnApply> findByUseridOrApplyid(int id, Pageable pageable) {
        return iSynnApplydao.findAllByUserid(id,pageable);
    }

    @Override
    public Page<SynnApply> findAllByUserid(int searchText, PageRequest pageRequest) {
        return iSynnApplydao.findAllByUserid(searchText,pageRequest);
    }

    @Override
    public void saveOrUpdate(SynnApply synnApply) {
         update(synnApply);
    }

    @Override
    public IBaseDao<SynnApply, Integer> getBaseDao() {
        return  this.iSynnApplydao;
    }
}
