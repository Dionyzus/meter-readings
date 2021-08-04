package com.odak.meterreading.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odak.meterreading.entity.ClientEntity;

/**
 * Client repository available for additional persistence methods.
 * Provides basic fetching methods and enables defining custom search methods.
 *
 * @author ivano
 *
 */
public interface ClientRepository extends JpaRepository<ClientEntity, Long>{

}
