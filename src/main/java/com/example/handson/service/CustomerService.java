package com.example.handson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.handson.exception.CustomerNotFoundException;
import com.example.handson.model.Customer;
import com.example.handson.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	public Iterable<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Long createCustomer(Customer customer) {
		Customer newCustomer = customerRepository.save(customer);
		if (newCustomer != null)
			return newCustomer.getId();
		return null;
	}

	public void updateCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void deleteCustomer(Long customerId) {
		Customer customer = customerRepository.findOne(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException();
		} else {
			customerRepository.delete(customer);
		}
	}

	public Customer getCustomer(Long customerId) {
		Customer customer = customerRepository.findOne(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException();
		}

		return customer;
	}

	public Customer[] findCustomerByName(String name) {
		return customerRepository.findCustomerByName(name);
	}
}
