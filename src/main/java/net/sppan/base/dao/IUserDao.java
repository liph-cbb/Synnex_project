package net.sppan.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.sppan.base.dao.support.IBaseDao;
import net.sppan.base.entity.User;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface IUserDao extends IBaseDao<User, Integer> {

	User findByUserName(String username);

	Page<User> findAllByNickNameContaining(String searchText, Pageable pageable);

	User findByEmail(String email);

	@Query(value = "select t.* from tb_user t where t.leaderid=?1",nativeQuery = true)
	User findLeaders(Integer id);

	User findById(Integer id);
}
