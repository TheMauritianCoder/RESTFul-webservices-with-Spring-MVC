package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.api.v1.model.VendorListDto;
import guru.springfamework.services.VendorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public VendorListDto getAllVendors(){
        return new VendorListDto(vendorService.getAll());
    }

    @PostMapping
    public VendorDto createVendor(@RequestBody VendorDto vendorDto){
        return vendorService.create(vendorDto);
    }

    @DeleteMapping("/{id}")
    public void deleteVendor(@PathVariable long id){
        vendorService.delete(id);
    }

    @GetMapping("/{id}")
    public VendorDto getVendor(@PathVariable long id){
        return vendorService.getById(id);
    }


    @PatchMapping("/{id}")
    public VendorDto patchVendor(@PathVariable long id,@RequestBody VendorDto vendorDto){
        return vendorService.patch(id,vendorDto);
    }

    @PutMapping("/{id}")
    public VendorDto putVendor(@PathVariable long id,@RequestBody VendorDto vendorDto){
        return vendorService.put(id,vendorDto);
    }

}
