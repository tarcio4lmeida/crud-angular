package com.apicrudds.backend.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apicrudds.backend.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
