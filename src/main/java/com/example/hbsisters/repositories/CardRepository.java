package com.example.hbsisters.repositories;

import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Card;
import com.example.hbsisters.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long> {
    List<Card> findByHolder(Client client);
}
