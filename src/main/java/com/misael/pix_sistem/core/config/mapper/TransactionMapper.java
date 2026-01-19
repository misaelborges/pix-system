package com.misael.pix_sistem.core.config.mapper;

import com.misael.pix_sistem.api.dto.response.PaymentResponseDTO;
import com.misael.pix_sistem.domain.model.Transactions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "sender", source = "senderId.name")
    @Mapping(target = "receiver", source = "receiverId.name")
    @Mapping(target = "transferDate", source = "createdAt")
    PaymentResponseDTO toDTO(Transactions transactions);

    List<PaymentResponseDTO> toListDTO(List<Transactions> Transactions);
}
