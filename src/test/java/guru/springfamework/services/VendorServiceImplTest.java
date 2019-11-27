package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.domain.Vendor;
import guru.springfamework.mapper.VendorMapper;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void getAll() {
        // given
        List<Vendor> vendors = Arrays.asList(Vendor.builder().build(),Vendor.builder().build());

        when(vendorRepository.findAll()).thenReturn(vendors);

        // when
        List<VendorDto> vendorDtos = vendorService.getAll();

        // then
        assertEquals(vendorDtos.size(),2);
    }

    @Test
    public void create() {
        // given
        VendorDto toCreate = VendorDto.builder().name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();
        Vendor dbVendor = Vendor.builder().id(1L).name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(dbVendor);

        // when
        VendorDto createdVendor = vendorService.create(toCreate);

        // then
        assertNotNull(createdVendor);
        assertEquals(createdVendor.getName(),dbVendor.getName());
        assertEquals(createdVendor.getVendorUrl(),dbVendor.getVendorUrl());
    }

    @Test
    public void delete() {
        // given
        vendorService.delete(1L);

        // when / then
        verify(vendorRepository,times(1)).deleteById(anyLong());
    }

    @Test
    public void getById() {
        // given
        Vendor foundVendor = Vendor.builder().name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();

        when(vendorRepository.getOne(anyLong())).thenReturn(foundVendor);

        // when
        VendorDto vendorDto = vendorService.getById(1L);

        // then
        assertNotNull(vendorDto);
        assertEquals(vendorDto.getName(),"Vendor 1");
        assertEquals(vendorDto.getVendorUrl(),"/api/v1/vendors/1");
    }

    @Test
    public void patch() {
        // given
        String updatedName = "Vendor 2";
        String updateVendorUrl = "/api/v1/vendors/2";

        Vendor originalVendor = Vendor.builder().id(1L).name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();
        Vendor updatedVendor = Vendor.builder().id(1L).name("Vendor 2").vendorUrl("/api/v1/vendors/2").build();

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(originalVendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(updatedVendor);

        VendorDto toUpdatedVendorDto = VendorDto.builder().name(updatedName).vendorUrl(updateVendorUrl).build();

        // when
        VendorDto updatedVendorDto = vendorService.patch(1L,toUpdatedVendorDto);

        // then
        assertNotNull(updatedVendorDto);
        assertEquals(updatedName,updatedVendorDto.getName());
        assertEquals(updateVendorUrl,updatedVendorDto.getVendorUrl());

    }

    @Test
    public void put() {
        // given
        VendorDto vendorDtoToUpdate = VendorDto.builder().name("Vendor 2").vendorUrl("/api/v1/vendors/2").build();
        Vendor updatedVendor = Vendor.builder().name("Vendor 2").vendorUrl("/api/v1/vendors/2").build();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(updatedVendor);

        // when
        VendorDto updatedVendorDTO = vendorService.put(1l,vendorDtoToUpdate);

        // then
        assertNotNull(updatedVendorDTO);
        assertEquals(updatedVendorDTO.getName(),vendorDtoToUpdate.getName());
        assertEquals(updatedVendorDTO.getVendorUrl(),vendorDtoToUpdate.getVendorUrl());

    }
}