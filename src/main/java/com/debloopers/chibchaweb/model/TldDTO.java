package com.debloopers.chibchaweb.model;

import jakarta.validation.constraints.Size;


public class TldDTO {

    @Size(max = 63)
    @TldTldValid
    private String tld;

    public String getTld() {
        return tld;
    }

    public void setTld(final String tld) {
        this.tld = tld;
    }

}
