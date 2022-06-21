package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Loan findByName(String name);
}
//    Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'loanRepository'
//        defined in com.mindhub.homebanking.repositories.LoanRepository defined in @EnableJpaRepositories declared on
//        JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Invocation of init method failed; nested
//        exception is java.lang.IllegalArgumentException: Not a managed type: class com.mindhub.homebanking.models.Loan
