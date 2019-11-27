package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.mapper.VendorMapper;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    VendorRepository vendorRepository;
    VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDto> getAll() {
        return vendorRepository.findAll().stream().map(vendorMapper::vendorToVendorDto).collect(Collectors.toList());
    }

    @Override
    public VendorDto create(VendorDto vendorDto) {
        Vendor vendor = vendorRepository.save(vendorMapper.vendorDtoToVendor(vendorDto));
        return saveAndReturn(vendor);
    }

    private VendorDto saveAndReturn(Vendor vendor) {
        VendorDto savedVendor = vendorMapper.vendorToVendorDto(vendor);
        savedVendor.setVendorUrl("/api/v1/vendors/"+vendor.getId());
        return savedVendor;
    }

    @Override
    public void delete(long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public VendorDto getById(long id) {
        return vendorMapper.vendorToVendorDto(vendorRepository.getOne(id));
    }

    @Override
    public VendorDto patch(long id, VendorDto vendorDto) {
        return vendorRepository.findById(id).map(vendor -> {
            if(vendorDto.getName() != null){
                vendor.setName(vendorDto.getName());
            }

            if(vendorDto.getVendorUrl() != null){
                vendor.setVendorUrl(vendorDto.getVendorUrl());
            }

            return vendorMapper.vendorToVendorDto(vendorRepository.save(vendor));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDto put(long id, VendorDto vendorDto) {
        Vendor vendorToUpdate = vendorMapper.vendorDtoToVendor(vendorDto);
        vendorToUpdate.setId(id);
        return vendorMapper.vendorToVendorDto(vendorRepository.save(vendorToUpdate));
    }
}
