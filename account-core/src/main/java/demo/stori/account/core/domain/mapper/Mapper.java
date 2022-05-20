package demo.stori.account.core.domain.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<D, E> {

    E fromDto(D dto);

    D fromEntity(E entity);

    default List<D> fromEntities(final Collection<E> entities) {
        return entities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    default List<E> fromDtos(final Collection<D> dtos) {
        return dtos.stream()
                .map(this::fromDto)
                .collect(Collectors.toList());
    }

}
