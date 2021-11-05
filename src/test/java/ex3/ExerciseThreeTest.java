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

import java.time.*;
import java.time.format.DateTimeFormatter;

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
    public void shouldCreateCustomer() {
        Customer createdCustomer = customerMappingService.createCustomer("CustomerId", "2021-05-01");
        verify(customerMapperRepository, only()).save(createdCustomer);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldValidateDateFormat() {
        customerMappingService.createCustomer("CustomerId", "20210501");
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldValidateDateIsNotTheFuture() {

        String tomorrowDate = DateTimeFormatter.ISO_LOCAL_DATE_TIME
                .withZone(ZoneId.from(ZoneOffset.UTC))
                .format(Instant.now().plus(Period.ofDays(1)));

        customerMappingService.createCustomer("CustomerId", tomorrowDate);
    }
}
