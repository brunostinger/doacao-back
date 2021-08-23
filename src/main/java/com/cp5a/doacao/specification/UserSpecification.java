package com.cp5a.doacao.specification;
import com.cp5a.doacao.dto.organizationdto.OrgFilterDTO;
import com.cp5a.doacao.dto.userdto.UserFilterDTO;
import com.cp5a.doacao.model.OccupationArea;
import com.cp5a.doacao.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserSpecification {
    public Specification<User> getOrganizations(OrgFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("userType").get("id"),2));
            predicates.add(criteriaBuilder.equal(root.get("userStatus").get("id"),1));

            if (!StringUtils.isBlank(filter.getName())){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"));
            }

            if (!StringUtils.isBlank(filter.getCategory())){
                Expression<Collection<OccupationArea>> areas = root.get("occupationAreas");
                predicates.add(criteriaBuilder.isMember(new OccupationArea(Integer.parseInt(filter.getCategory())), areas));
            }

            if (!StringUtils.isBlank(filter.getCity())){
                predicates.add(criteriaBuilder.equal(root.get("city"),filter.getCity()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<User> getOrgsLocation(){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("userType").get("id"),2));
            predicates.add(criteriaBuilder.equal(root.get("userStatus").get("id"),1));
            query.groupBy(root.get("city"));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<User> getUsers(UserFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isBlank(filter.getName())){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                "%" + filter.getName().toLowerCase() + "%"));
            }

            if (!StringUtils.isBlank(filter.getStatus())){
                predicates.add(criteriaBuilder.equal(root.get("userStatus").get("id"),filter.getStatus()));
            }

            if (!StringUtils.isBlank(filter.getType())){
                predicates.add(criteriaBuilder.equal(root.get("userType").get("id"),filter.getType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
