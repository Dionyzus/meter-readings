package com.odak.meterreading.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odak.meterreading.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long>{

}
