package edu.icet.controller;

import edu.icet.dto.CustomerDto;
import edu.icet.entity.CustomerEntity;
import edu.icet.mapper.CustomerMapper;
import edu.icet.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "Customer Controller", description = "To Manage Customers")
public class CustomerController {
    private final CustomerService service;
    private final CustomerMapper customerMapper;
    private static final String STATUS = "status";

    @GetMapping("/get-all")
    List<CustomerDto> getAllCustomers() {
        return service.getAll()
                .stream()
                .map(customerMapper::toCustomerDto)
                .toList();
    }

    @PostMapping("/add")
    ResponseEntity<Map<String, String>> addCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Map<String, String> map = new HashMap<>();
        CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
        service.saveCustomer(customerEntity);
        map.put(STATUS, "CustomerEntity Created Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @PutMapping("/update")
    ResponseEntity<Map<String, String>> updateCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Map<String, String> map = new HashMap<>();
        if (customerDto.getId() == null || customerDto.getId() <= 0) {
            map.put(STATUS, "CustomerEntity ID cannot be empty or negative");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        if (!service.isCustomerExists(customerDto.getId())) {
            map.put(STATUS, "CustomerEntity not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
        CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
        service.saveCustomer(customerEntity);
        map.put(STATUS, "Update Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable("id") Integer id) {
        Map<String, String> map = new HashMap<>();
        if (id <= 0) {
            map.put(STATUS, "Please Enter CustomerEntity ID or Invalid CustomerEntity ID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        boolean isDeleted = service.delete(id);
        if (!isDeleted) {
            map.put(STATUS, "CustomerEntity not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
        map.put(STATUS, "Deleted Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}