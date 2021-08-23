package com.cp5a.doacao.repository;
import com.cp5a.doacao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

    public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{
    User findByEmail(String email);
    User findByUniqueId(String uniqueId);
    User findByIdAndUserTypeIdAndUserStatusId(Integer id, Integer userTypeId, Integer userStatusId);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    User findBalanceById(Integer id);
}
