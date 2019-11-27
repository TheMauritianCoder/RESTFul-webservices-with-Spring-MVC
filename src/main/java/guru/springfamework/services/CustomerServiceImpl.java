package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.mapper.CustomerMapper;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(
                customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customer/"+customer.getId());
                    return customerDTO;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(long id) {
        return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDto) {
        Customer customer = customerRepository.save(customerMapper.customerDTOToCustomer(customerDto));
        return saveAndReturnCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(long id, CustomerDTO customerDto) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDto);
        customer.setId(id);
        return saveAndReturnCustomer(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO patchCustomer(long id, CustomerDTO customerDto) {
        return customerRepository.findById(id).map(customer -> {
            if(customerDto.getFirstname() != null){
                customer.setFirstname(customerDto.getFirstname());
            }

            if(customerDto.getLastname() != null){
                customer.setLastname(customerDto.getLastname());
            }

            CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            customerDTO.setCustomerUrl("/api/v1/customer/"+customer.getId());
            return customerDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO saveAndReturnCustomer(Customer customer) {
        CustomerDTO savedCustomer = customerMapper.customerToCustomerDTO(customer);
        savedCustomer.setCustomerUrl("/api/v1/customer/"+customer.getId());
        return savedCustomer;
    }
}
