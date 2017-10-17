package com.example.handson.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.handson.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	@Query("from app_customer cust where lower(cust.firstName) like CONCAT('%', lower(:name), '%') or lower(cust.lastName) like CONCAT('%', lower(:name), '%')  ")
	public Customer[] findCustomerByName(@Param("name") String name);
}
