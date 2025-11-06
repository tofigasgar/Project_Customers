package az.customers.util.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountTransactionMapper {

    AccountsMapper INSTANCE = Mappers.getMapper(AccountsMapper.class);


}
