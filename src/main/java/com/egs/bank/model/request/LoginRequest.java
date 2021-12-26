package com.egs.bank.model.request;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank
    @Column(length = 16)
    String cardNo;

    String fingerprint;

}
