package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(long id);
    CustomerDTO createCustomer(CustomerDTO customer);
    CustomerDTO updateCustomer(long id, CustomerDTO customerDto);
    CustomerDTO patchCustomer(long id, CustomerDTO customerDto);
    void deleteCustomerById(long id);
}
