package br.com.spring.authauthorizationserver.repository;


import br.com.spring.authauthorizationserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);























//    @PersistenceContext
//    EntityManager manager;
//
//    public List<UserEntity> findAll(){
//        TypedQuery<UserEntity> appUserTypedQuery = this.manager.createNamedQuery("user.findAll", UserEntity.class);
//        return appUserTypedQuery.getResultList();
//    }
//
//    public void save(){
//        this.manager.persist(new UserEntity());
//    }
}
