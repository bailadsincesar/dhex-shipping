package com.dhex.shipping.services;

import com.dhex.shipping.exceptions.InvalidArgumentDhexException;
import com.dhex.shipping.exceptions.NotValidShippingStatusException;
import com.dhex.shipping.model.ShippingRequest;
import com.dhex.shipping.model.ShippingStatus;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.matchers.Not;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class ShippingServiceTest {
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnAnIdAndDateWhenRegistering() {
        ShippingRequest shippingRequest = new ShippingService()
                .registerRequest(new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 133, null));

        assertThat(shippingRequest.getId(), is(notNullValue()));
        assertThat(shippingRequest.getRegistrationMoment(), is(notNullValue()));
    }

    @Test
    public void shouldReturnAndIdAndDateWhenRegisteringStatus() throws Exception {
        ShippingService shippingService = new ShippingService();

        ShippingRequest shippingRequest = shippingService
                .registerRequest(new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 133, null));

        ShippingStatus shippingStatus = shippingService
                .registerStatus(shippingRequest.getId(), "Lima", "Internal", null);

        assertThat(shippingStatus.getId(), is(notNullValue()));
        assertThat(shippingStatus.getMoment(), is(notNullValue()));
    }

    @Test
    public void shouldKeepTheParametersFromConstructor() {
        ShippingService shippingService = new ShippingService();

        ShippingRequest shippingRequest = shippingService
                .registerRequest(new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 133, null));

        String requestId = shippingRequest.getId();
        String location = "Lima";
        String status = "Internal";
        String observations = "Everything is OK";

        ShippingStatus shippingStatus = shippingService
                .registerStatus(requestId, location, status, observations);

        assertThat(shippingStatus.getLocation(), is(location));
        assertThat(shippingStatus.getStatus(), is(status));
        assertThat(shippingStatus.getObservations(), is(observations));
    }

    @Test
    public void shouldThrowNotValidShippinhStatusExceptionIfStatusIsInvalid() {
        ShippingService shippingService = new ShippingService();

        ShippingRequest shippingRequest = shippingService
                .registerRequest(new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 133, null));

        String status = "Tired";

        expectedException.expect(NotValidShippingStatusException.class);
        ShippingStatus shippingStatus = shippingService
                .registerStatus(shippingRequest.getId(), "Lima", status, null);
    }

    @Test
    public void shouldThrowInvalidArgumentDhexExceptionIfRequestIdIsMissing() {
        ShippingService shippingService = new ShippingService();

        expectedException.expect(InvalidArgumentDhexException.class);
        ShippingStatus shippingStatus = shippingService
                .registerStatus("", "Lima", "on hold", null);
    }

    @Test
    public void shouldThrowInvalidArgumentDhexExceptionIfLocationIsMissing() {
        ShippingService shippingService = new ShippingService();

        ShippingRequest shippingRequest = shippingService
                .registerRequest(new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 133, null));

        expectedException.expect(InvalidArgumentDhexException.class);
        ShippingStatus shippingStatus = shippingService
                .registerStatus(shippingRequest.getId(), "", "on hold", null);
    }

    @Test
    public void shouldThrowInvalidArgumentDhexExceptionIfStatusIsMissing() {
        ShippingService shippingService = new ShippingService();

        ShippingRequest shippingRequest = shippingService
                .registerRequest(new SendingRequestParameterList("Juan Perez", "Jorge Quispe", "Calle Los Negocios 444", 133, null));

        expectedException.expect(InvalidArgumentDhexException.class);
        ShippingStatus shippingStatus = shippingService
                .registerStatus(shippingRequest.getId(), "Lima", "", null);
    }
}