package com.example.demo.user;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    EntityManager entityManager ;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    List<User> findUserByIdAndFirstNameAndLastNameAndAgeAndCompanyId(String id, String firstName, String lastName, String age){

        return generalUserSearch(id , firstName ,lastName , age , "", "", "");


    }
    List<User> generalUserSearch(String id, String firstName, String lastName, String age,
                                 String companyId , String companyName, String companyLocation){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> user = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if(!id.isEmpty())
            predicates.add(criteriaBuilder.equal(user.get("id"), id));

        if(!firstName.isEmpty())
            predicates.add(criteriaBuilder.like(user.get("firstName"), firstName));

        if(!lastName.isEmpty())
            predicates.add(criteriaBuilder.like(user.get("lastName"), lastName) );

        if(!age.isEmpty())
            predicates.add( criteriaBuilder.equal(user.get("age"), age) );

        final String company = "company";

        if(!companyId.isEmpty())
            predicates.add( criteriaBuilder.equal(user.join(company).get("id") , companyId));

        if(!companyLocation.isEmpty())
            predicates.add( criteriaBuilder.like(user.join(company).get("location") , companyLocation));

        if(!companyName.isEmpty())
            predicates.add( criteriaBuilder.like(user.join(company).get("name") , companyName));


        criteriaQuery.where(predicates.toArray(new Predicate[0]));


        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
