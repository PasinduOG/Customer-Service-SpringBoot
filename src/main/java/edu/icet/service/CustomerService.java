package edu.icet.service;

import edu.icet.entity.Customer;
import edu.icet.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    @Autowired
    CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void saveCustomer(Customer customer) {
        repository.save(customer);
    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public boolean delete(Integer id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    public boolean isCustomerExists(Integer id){
        return repository.existsById(id);
    }
}
