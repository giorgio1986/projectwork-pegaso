package it.previsuite.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<E, B> {
	protected final ObjectMapper mapper = new ObjectMapper();

	public abstract B mapEntityToBean(E entity);
	public abstract E mapBeanToEntity(B bean);

	public List<B> mapEntitiesToBeans(Collection<E> entityCollection) {
		if(entityCollection == null) {
			entityCollection = Collections.emptyList();
		}

		return entityCollection
				.stream()
				.map(this::mapEntityToBean)
				.collect(Collectors.toList());
	}

	public List<E> mapBeansToEntities(Collection<B> beanCollection) {
		if(beanCollection == null) {
			beanCollection = Collections.emptyList();
		}

		return beanCollection
				.stream()
				.map(this::mapBeanToEntity)
				.collect(Collectors.toList());
	}
}