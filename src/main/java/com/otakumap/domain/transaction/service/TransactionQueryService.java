package com.otakumap.domain.transaction.service;

import com.otakumap.domain.transaction.dto.TransactionResponseDTO;
import com.otakumap.domain.user.entity.User;

public interface TransactionQueryService {
    TransactionResponseDTO.TransactionListDTO getUsageTransactions(User user, Integer page, Integer size);
    TransactionResponseDTO.TransactionListDTO getEarningTransactions(User user, Integer page, Integer size);
}
