package com.example.hbsisters;

import com.example.hbsisters.models.Loan;
import com.example.hbsisters.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebMvc
class HbSistersApplicationTests {

	@Autowired
	LoanRepository loanRepository;

	@Test
	public void existLoans(){

		List<Loan> loans = loanRepository.findAll();
		assertThat(loans,is(not(empty())));

	}


	@Test
	public void existPersonalLoan(){
		List<Loan> loans = loanRepository.findAll();
		assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

	}

}
