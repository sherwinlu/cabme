package com.cabme.geocode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Holds the lat & long of the start/end destination of the ride
 */
public class Geocode implements Serializable {

    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public Geocode(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
