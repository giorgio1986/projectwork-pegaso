package it.previsuite.service.merger;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractMerger<A, B> {
	protected final ObjectMapper mapper = new ObjectMapper();
	public abstract A merge(A bean1, B bean2);
}