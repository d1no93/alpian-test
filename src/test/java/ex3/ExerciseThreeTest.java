package ex3;

import com.shaazabedin.db.CustomerMapperRepository;
import com.shaazabedin.model.Customer;
import com.shaazabedin.service.CustomerMappingService;
import com.shaazabedin.service.CustomerMappingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseThreeTest {

    @Mock
    private CustomerMapperRepository customerMapperRepository;

    private CustomerMappingService customerMappingService;

    @Before
    public void setUp() {
        customerMappingService = new CustomerMappingServiceImpl(customerMapperRepository);
    }

    @Test
    public void shouldSaveCustomer() {
        Customer createdCustomer = customerMappingService.createCustomer("CustomerId", "2021-05-01");
        verify(customerMapperRepository, only()).save(createdCustomer);
    }
}
