package com.example.handson.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.handson.model.Customer;
import com.example.handson.model.CustomerImage;
import com.example.handson.service.CustomerService;
import com.example.handson.service.FileArchiveService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	CustomerService customerService;

	@Autowired
	private FileArchiveService fileArchiveService;

	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public List<Customer> getAllCustomers() {
		logger.debug("------> getAllCustomers() method got invoked.");
		return IteratorUtils.toList(customerService.getAllCustomers().iterator());
	}

	@RequestMapping(value = "/customer", method = RequestMethod.POST, consumes = "multipart/form-data")
	public Long createCustomer(@RequestPart("customer") Customer customer,
			@RequestPart("imageFile") MultipartFile imageFile) {
		logger.debug("------> createCustomer() method invoked. imageFile name: " + imageFile.getOriginalFilename()
				+ " , size: " + imageFile.getSize());

		CustomerImage customerImage = fileArchiveService.saveFileToS3(imageFile);
		customer.setCustomerImage(customerImage);

		return customerService.createCustomer(customer);
	}

	@RequestMapping(value = "/customer", method = RequestMethod.PUT)
	public void updateCustomer(@RequestBody Customer customer) {
		logger.debug("------> updateCustomer() method invoked. ");
		customerService.updateCustomer(customer);
	}

	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable("customerId") Long customerId) {
		logger.debug("------> getCustomer() method invoked with customerId: " + customerId);
		return customerService.getCustomer(customerId);
	}

	@RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable("customerId") Long customerId) {
		logger.debug("------> deleteCustomer() method invoked with customerId: " + customerId);
		customerService.deleteCustomer(customerId);
	}

	@RequestMapping(value = "/customer/search", method = RequestMethod.GET)
	public Customer[] findCustomQueryCustomers(@RequestParam String name) {
		logger.debug("------> findCustomQueryCustomers() : name:- " + name);
		return customerService.findCustomerByName(name);
	}
}
