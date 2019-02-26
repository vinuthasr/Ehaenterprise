package com.elephant.dao.invoicedetails;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.invoicedetails.InvoiceDetailsDomain;

public interface InvoiceDetailsDaoRepository extends JpaRepository<InvoiceDetailsDomain, Long> {

}
