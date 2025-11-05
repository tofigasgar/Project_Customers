package az.customers.util.mapping;

import az.customers.model.dto.AccountsDto;
import az.customers.model.entity.Accounts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountsMapper {

    AccountsMapper INSTANCE = Mappers.getMapper(AccountsMapper.class);

    Accounts toEntity(AccountsDto.Request dto);

    @Mapping(source = "customer.fin", target = "customerFin")
    AccountsDto.Response toDto(Accounts entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateAccountFromRequest(AccountsDto.Update request, @MappingTarget Accounts entity);
}
