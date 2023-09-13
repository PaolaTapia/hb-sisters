package com.example.hbsisters.services;

import com.example.hbsisters.dtos.LoanDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();
    LoanDTO getLoanDTO( Long id);
}
