package edu.icet.service;

import edu.icet.entity.CustomerEntity;
import java.util.List;

public interface CustomerService {
    void saveCustomer(CustomerEntity customerEntity);
    List<CustomerEntity> getAll();
    boolean delete(Integer id);
    boolean isCustomerExists(Integer id);
}
