package ex3;

import com.shaazabedin.Application;
import com.shaazabedin.controller.CustomerMapperController;
import com.shaazabedin.exception.CustomerAlreadyExistsException;
import com.shaazabedin.model.Customer;
import com.shaazabedin.service.CustomerMappingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerMapperController.class)
@ContextConfiguration(classes={Application.class})
public class CustomerMapperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerMappingService customerMappingService;

    @Test
    public void shouldReturnCreatedCustomer() throws Exception {
        Customer customerMock = Mockito.mock(Customer.class);
        when(customerMappingService.createCustomer("CustomerId", "2021-11-06"))
                .thenReturn(customerMock);

        this.mockMvc.perform(post("/customerMapper/customer/CustomerId/createdAt/2021-11-06/create"))
                .andExpect(status().isOk())
                .andExpect(content().string("Created Customer - Customer: CustomerId, CreatedAt: 2021-11-06"));
    }

    @Test
    public void shouldReturnBadRequestIfCustomerAlreadyExists() throws Exception {
        when(customerMappingService.createCustomer("CustomerId", "2021-11-06"))
                .thenThrow(new CustomerAlreadyExistsException("CustomerId"));

        this.mockMvc.perform(post("/customerMapper/customer/CustomerId/createdAt/2021-11-06/create"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format("Customer: CustomerId already exists.")));
    }

    @Test
    public void shouldReturnExternalId() throws Exception {
        when(customerMappingService.getExternalId("CustomerId")).thenReturn("ExternalId");

        this.mockMvc.perform(get("/customerMapper/customer/CustomerId/externalId"))
                .andExpect(status().isOk())
                .andExpect(content().string("ExternalId"));
    }

    @Test
    public void shouldReturnNoContentIfCustomerIdNotCreated() throws Exception {
        when(customerMappingService.getExternalId("CustomerId")).thenReturn(null);

        this.mockMvc.perform(get("/customerMapper/customer/CustomerId/externalId"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
