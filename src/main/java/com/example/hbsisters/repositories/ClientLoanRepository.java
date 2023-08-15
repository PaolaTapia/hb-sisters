package com.example.hbsisters.repositories;

import com.example.hbsisters.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository  extends JpaRepository<ClientLoan,Long> {
}
