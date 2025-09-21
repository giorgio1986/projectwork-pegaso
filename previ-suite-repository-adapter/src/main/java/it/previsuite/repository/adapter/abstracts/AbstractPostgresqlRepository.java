package it.previsuite.repository.adapter.abstracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPostgresqlRepository<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractPostgresqlRepository.class);
    protected final ObjectMapper mapper = new ObjectMapper();
}