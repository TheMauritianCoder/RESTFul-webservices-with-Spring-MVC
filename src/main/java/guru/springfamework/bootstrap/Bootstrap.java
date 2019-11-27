package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");


        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic= new Category();
        exotic.setName("Exotic");

        Category nuts= new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Category Loaded: "+categoryRepository.count());
    }


    private void loadCustomers() {
        customerRepository.save(Customer.builder().id(1L).firstname("Michale").lastname("Weston").build());
        customerRepository.save(Customer.builder().id(1L).firstname("Sam").lastname("Axe").build());
        System.out.println("Customer Loaded: "+customerRepository.count());
    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder().name("Vendor 1").vendorUrl("/api/v1/vendors/1").build());
        vendorRepository.save(Vendor.builder().name("Vendor 2").vendorUrl("/api/v1/vendors/2").build());
    }

}
