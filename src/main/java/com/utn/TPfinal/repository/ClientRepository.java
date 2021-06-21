package com.utn.TPfinal.repository;


import com.utn.TPfinal.domain.Client;
import com.utn.TPfinal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    /*se usara para determinar si el usuario existe. Para eso lo buscaos por el name y el password*/

    Client findByNameAndPassword(String name , String password);

    @Query(value = "SELECT c.* FROM clients AS c\n" +
            "\tINNER JOIN addresses AS a ON c.id = a.id_client\n" +
            "\tINNER JOIN meters AS m ON a.id_address = m.id_address\n" +
            "\tINNER JOIN measurings AS me ON m.serial_number = me.serial_number\n" +
            "\tWHERE DATE BETWEEN :beginDate AND :endDate \n" +
            "\tGROUP BY (c.id)\n" +
            "\tORDER BY SUM(me.value) DESC\n" +
            "\tLIMIT 10;", nativeQuery = true )
    List<Client> tenMoreConsumers(LocalDateTime beginDate, LocalDateTime endDate);
}
/*"SELECT info.name_client, SUM(consuption)\n" +
           "FROM (\n" +
           "SELECT    c.name AS name_client, MAX(me.value) AS consuption\n" +
           "FROM clients AS c\n" +
           "INNER JOIN addresses AS a\n" +
           "ON 1 = 1\n" +
           "INNER JOIN meters AS m\n" +
           "ON a.id_address = m.id_address\n" +
           "INNER JOIN measurings AS me\n" +
           "ON m.serial_number = me.serial_number\n" +
           "WHERE DATE BETWEEN :beginDate AND :endDate\n" +
           "GROUP BY m.serial_number , c.name) info\n" +
           "GROUP BY  info.name_client\n" +
           "ORDER BY SUM(consuption) DESC\n" +
           "LIMIT 10;"*/