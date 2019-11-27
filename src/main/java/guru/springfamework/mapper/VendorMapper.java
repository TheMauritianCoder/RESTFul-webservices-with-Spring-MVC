package guru.springfamework.mapper;

import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    Vendor vendorDtoToVendor(VendorDto vendorDto);
    VendorDto vendorToVendorDto(Vendor vendor);
}
