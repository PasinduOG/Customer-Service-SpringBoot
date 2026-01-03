package edu.icet.controller;

import edu.icet.dto.CustomerDto;
import edu.icet.entity.Customer;
import edu.icet.service.CustomerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;
    private final ModelMapper modelMapper;
    private static final String STATUS = "status";

    @Autowired
    CustomerController(CustomerService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get-all")
    List<CustomerDto> getAllCustomers() {
        return service.getAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .toList();
    }

    @PostMapping("/add")
    ResponseEntity<Map<String, String>> addCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Map<String, String> map = new HashMap<>();
        Customer customer = modelMapper.map(customerDto, Customer.class);
        service.saveCustomer(customer);
        map.put(STATUS, "Customer Created Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @PutMapping("/update")
    ResponseEntity<Map<String, String>> updateCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Map<String, String> map = new HashMap<>();
        if (customerDto.getId() == null || customerDto.getId() <= 0) {
            map.put(STATUS, "Customer ID cannot be empty or negative");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        if (!service.isCustomerExists(customerDto.getId())) {
            map.put(STATUS, "Customer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
        Customer customer = modelMapper.map(customerDto, Customer.class);
        service.saveCustomer(customer);
        map.put(STATUS, "Update Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable("id") Integer id) {
        Map<String, String> map = new HashMap<>();
        if (id <= 0) {
            map.put(STATUS, "Please Enter Customer ID or Invalid Customer ID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        boolean isDeleted = service.delete(id);
        if (!isDeleted) {
            map.put(STATUS, "Customer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
        map.put(STATUS, "Deleted Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}