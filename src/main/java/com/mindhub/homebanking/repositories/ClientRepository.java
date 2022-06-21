package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//Las interfaces solo tienen metodos, no tienen propiedades
@RepositoryRestResource //crea los controladores para el repositorio (post, get, put, patch, delete, etc) que van a funcionar con rest
public interface ClientRepository extends JpaRepository<Client, Long> {//hereda los metodos de JpaRepository para trabajar especificamente con Client y Long
    Client findByEmail(String email);
}