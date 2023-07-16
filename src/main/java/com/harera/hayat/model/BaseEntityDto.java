package com.harera.hayat.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityDto implements Serializable {

    private Long id;
    private Boolean active;
}
