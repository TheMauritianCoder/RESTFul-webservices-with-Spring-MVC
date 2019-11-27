package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDto;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {
        // given
        List<VendorDto> vendors = Arrays.asList(VendorDto.builder().build(),VendorDto.builder().build(),VendorDto.builder().build());
        when(vendorService.getAll()).thenReturn(vendors);

        // when / then
        mockMvc.perform(get("/api/v1/vendors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors",hasSize(3)));

    }

    @Test
    public void createVendor() throws Exception {
        // given
        VendorDto vendorDtoToCreate = VendorDto.builder().name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();
        when(vendorService.create(any(VendorDto.class))).thenReturn(vendorDtoToCreate);

        // when/then
        mockMvc.perform(
                post("/api/v1/vendors").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendorDtoToCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDtoToCreate.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorDtoToCreate.getVendorUrl())));
    }

    @Test
    public void deleteVendor() throws Exception {
        VendorDto vendorDto = VendorDto.builder().name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();
        mockMvc.perform(delete("/api/v1/vendors/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(vendorService,times(1)).delete(anyLong());
    }

    @Test
    public void getVendor() throws Exception {
        VendorDto vendorDto = VendorDto.builder().name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();

        when(vendorService.getById(anyLong())).thenReturn(vendorDto);

        mockMvc.perform(get("/api/v1/vendors/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(vendorDto.getName())))
                .andExpect(jsonPath("$.vendor_url",equalTo(vendorDto.getVendorUrl())));
    }

    @Test
    public void patchVendor() throws Exception {
        VendorDto vendorDto = VendorDto.builder().name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();
        when(vendorService.patch(anyLong(),any(VendorDto.class))).thenReturn(vendorDto);

        mockMvc.perform(
                patch("/api/v1/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(vendorDto.getName())))
                .andExpect(jsonPath("$.vendor_url",equalTo(vendorDto.getVendorUrl())));
    }

    @Test
    public void putVendor() throws Exception {
        VendorDto vendorDto = VendorDto.builder().name("Vendor 1").vendorUrl("/api/v1/vendors/1").build();
        when(vendorService.put(anyLong(),any(VendorDto.class))).thenReturn(vendorDto);

        mockMvc.perform(
                put("/api/v1/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(vendorDto.getName())))
                .andExpect(jsonPath("$.vendor_url",equalTo(vendorDto.getVendorUrl())));
    }
}