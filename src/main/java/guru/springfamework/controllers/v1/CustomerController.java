package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/customers")
@Api(description = "This is my Customer Controller")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get the list of all customers", notes = "This is some notes about the API.")
    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers(){
        return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(customerService.getAllCustomers()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable long id){
        return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustromer(@RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<CustomerDTO>(customerService.createCustomer(customerDTO),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable long id, @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<CustomerDTO>(customerService.updateCustomer(id,customerDTO),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable long id, @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<CustomerDTO>(customerService.patchCustomer(id,customerDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
