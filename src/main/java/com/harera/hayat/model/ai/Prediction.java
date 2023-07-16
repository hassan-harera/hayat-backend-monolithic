package com.harera.hayat.model.ai;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Prediction implements Serializable {

    private String label;
    private double certainty;
}
