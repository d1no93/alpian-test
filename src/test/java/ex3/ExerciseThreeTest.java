package ex3;

import com.shaazabedin.db.CustomerMapperRepository;
import com.shaazabedin.exception.CustomerAlreadyExistsException;
import com.shaazabedin.model.Customer;
import com.shaazabedin.service.CustomerMappingService;
import com.shaazabedin.service.CustomerMappingServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
        verify(customerMapperRepository).findById("CustomerId");
        verify(customerMapperRepository).save(createdCustomer);
        verifyNoMoreInteractions(customerMapperRepository);
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

    @Test
    public void shouldRetrieveExternalId() {

        Customer customer = new Customer();
        customer.setExternalId("ExternalId");
        customer.setCustomerId("CustomerId");
        customer.setCreatedAt(new Date(Date.from(Instant.now()).getTime()));

        Optional<Customer> optionalCustomer = Optional.of(customer);

        Mockito.when(this.customerMapperRepository.findById("CustomerId")).thenReturn(optionalCustomer);

        customerMappingService.getExternalId("CustomerId");
    }

    @Test
    public void shouldReturnNullIfIdDoesNotExist() {
        String externalId = customerMappingService.getExternalId("CustomerId");
        Assertions.assertThat(externalId).isNull();
    }

    @Test(expected=CustomerAlreadyExistsException.class)
    public void shouldThrowExceptionIfCustomerAlreadyExists() {

        Customer customer = new Customer();
        customer.setExternalId("ExternalId");
        customer.setCustomerId("CustomerId");
        customer.setCreatedAt(new Date(Date.from(Instant.now()).getTime()));

        Optional<Customer> optionalCustomer = Optional.of(customer);

        Mockito.when(this.customerMapperRepository.findById("CustomerId")).thenReturn(optionalCustomer);

        String todayDate = DateTimeFormatter.ISO_LOCAL_DATE
                .withZone(ZoneId.from(ZoneOffset.UTC))
                .format(Instant.now());

       customerMappingService.createCustomer("CustomerId", todayDate);
    }
}
