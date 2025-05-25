package twitter.challenge.espenia.infra.gateway.mapper;

public interface BaseMapper<E,D> {

    D toDomain(E entity);

    E toEntity(D domain);

    Iterable<E> toEntity(Iterable<D> domainList);

    Iterable<D> toDomain(Iterable<E> entityList);

//    @InheritConfiguration(name = "toDomain")
//    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
//    void updateEntity(D domain, @MappingTarget E entity);

}
