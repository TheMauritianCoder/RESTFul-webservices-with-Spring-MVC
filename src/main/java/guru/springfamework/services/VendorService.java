package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDto;

import java.util.List;

public interface VendorService {

    List<VendorDto> getAll();

    VendorDto create(VendorDto vendorDto);

    void delete(long id);

    VendorDto getById(long id);

    VendorDto patch(long id, VendorDto vendorDto);

    VendorDto put(long id, VendorDto vendorDto);

}
