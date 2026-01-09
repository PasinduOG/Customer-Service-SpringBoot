package edu.icet.mapper;

import edu.icet.dto.CustomerDto;
import edu.icet.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public CustomerEntity toEntity(CustomerDto customerDto){
        return modelMapper.map(customerDto, CustomerEntity.class);
    }

    public CustomerDto toCustomerDto(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity, CustomerDto.class);
    }
}
