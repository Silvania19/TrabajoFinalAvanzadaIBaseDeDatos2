package com.utn.TPfinal.repository;


import com.utn.TPfinal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /*se usara para determinar si el usuario existe. Para eso lo buscaos por el name y el password*/

    User findByNameAndPassword(String name , String password);

}
