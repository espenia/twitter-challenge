package twitter.challenge.espenia.infra.gateway.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.MappingTarget;

public interface BaseMapper<E,D> {

    D toDomain(E entity);

    E toEntity(D domain);

    Iterable<E> toEntity(Iterable<D> domainList);

    Iterable<D> toDomain(Iterable<E> entityList);

//    @InheritConfiguration(name = "toDomain")
//    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
//    void updateEntity(D domain, @MappingTarget E entity);

}
