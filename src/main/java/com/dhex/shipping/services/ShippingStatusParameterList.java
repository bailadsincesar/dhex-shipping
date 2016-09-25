package com.dhex.shipping.services;

public class ShippingStatusParameterList {
    private final String requestId;
    private final String location;
    private final String status;
    private final String observations;

    public ShippingStatusParameterList(String requestId, String location, String status, String observations) {
        this.requestId = requestId;
        this.location = location;
        this.status = status;
        this.observations = observations;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getObservations() {
        return observations;
    }
}
