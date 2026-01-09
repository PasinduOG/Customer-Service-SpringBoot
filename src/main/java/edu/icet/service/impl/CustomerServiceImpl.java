package edu.icet.service.impl;

import edu.icet.entity.CustomerEntity;
import edu.icet.repository.CustomerRepository;
import edu.icet.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    @Override
    public void saveCustomer(CustomerEntity customerEntity) {
        repository.save(customerEntity);
    }

    @Override
    public List<CustomerEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public boolean delete(Integer id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean isCustomerExists(Integer id) {
        return repository.existsById(id);
    }
}
