package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.mapper.CustomerMapper;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void getAllCustomers() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(2, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {

        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        assertEquals("Michale", customerDTO.getFirstname());
    }

    @Test
    public void save(){
        // given
        CustomerDTO customerDTO = CustomerDTO.builder().id(1L).firstname("Ravi").lastname("Kowlessur").build();
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstname("Ravi");
        savedCustomer.setLastname("Kowlessur");

        // when
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // then
        CustomerDTO savedDTO = customerService.createCustomer(customerDTO);

        assertEquals(savedDTO.getFirstname(),savedCustomer.getFirstname());
        assertEquals(savedDTO.getLastname(),savedCustomer.getLastname());
        assertEquals("/api/v1/customer/1",savedDTO.getCustomerUrl());
    }

    @Test
    public void update(){
        // given
        CustomerDTO customerDTO = CustomerDTO.builder().id(1L).firstname("Ravi").lastname("Kowlessur").build();
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstname("Ravi");
        savedCustomer.setLastname("Kowlessur");

        // when
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // then
        CustomerDTO savedDTO = customerService.updateCustomer(1L , customerDTO);

        assertEquals(savedDTO.getFirstname(),savedCustomer.getFirstname());
        assertEquals(savedDTO.getLastname(),savedCustomer.getLastname());
        assertEquals("/api/v1/customer/1",savedDTO.getCustomerUrl());

    }

    @Test
    public void delete(){
        long id = 1;
        customerService.deleteCustomerById(id);
        verify(customerRepository,times(1)).deleteById(anyLong());
    }

}