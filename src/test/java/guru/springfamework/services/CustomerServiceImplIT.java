package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.mapper.CustomerMapper;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setup() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println("Customers Found --> "+customerRepository.findAll().size());

        // Setup Data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository,vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository,CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstname() throws Exception{
        String updatedName = "updatedName";
        long id = getCustomerIdvalue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        // save original firstname
        String originalFirstname = originalCustomer.getFirstname();
        String originalLastname = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id,customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName,updatedCustomer.getFirstname());
        assertNotEquals(originalFirstname,updatedCustomer.getFirstname());
        assertEquals(originalLastname, updatedCustomer.getLastname());

    }


    @Test
    public void patchCustomerUpdateLastname() throws Exception{
        String updatedName = "updatedName";
        long id = getCustomerIdvalue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        // save original firstname
        String originalFirstname = originalCustomer.getFirstname();
        String originalLastname = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(id,customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName,updatedCustomer.getLastname());
        assertNotEquals(originalLastname, updatedCustomer.getLastname());
        assertEquals(originalFirstname,updatedCustomer.getFirstname());

    }

    private Long getCustomerIdvalue(){
        List<Customer> customers = customerRepository.findAll();
        System.out.println("Customers Found: " +customers.size());
        return customers.get(0).getId();
    }

}
