package it.previsuite.service.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractManagerAdapter {
    protected final ObjectMapper mapper = new ObjectMapper();
}