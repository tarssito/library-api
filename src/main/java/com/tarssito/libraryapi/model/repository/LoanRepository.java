package com.tarssito.libraryapi.model.repository;

import com.tarssito.libraryapi.model.entity.Book;
import com.tarssito.libraryapi.model.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("select case when (count(l.id) > 0) then true else false end " +
            " from Loan l where l.book = :book and (l.returned is null or l.returned is false)")
    boolean existsByBookNotReturned(@Param("book") Book book);

    Page<Loan> findByBookOrCustomer(String isbn, String costumer, Pageable pageable);
}
