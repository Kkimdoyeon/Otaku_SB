package com.otakumap.domain.transaction.service;

import com.otakumap.domain.transaction.converter.TransactionConverter;
import com.otakumap.domain.transaction.dto.TransactionResponseDTO;
import com.otakumap.domain.transaction.enums.TransactionType;
import com.otakumap.domain.transaction.repository.TransactionRepository;
import com.otakumap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionQueryServiceImpl implements TransactionQueryService {
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionResponseDTO.TransactionListDTO getUsageTransactions(User user, Integer page, Integer size) {
        List<TransactionResponseDTO.TransactionDTO> transactions = transactionRepository.findAllByPoint_UserAndType(user, TransactionType.USAGE, PageRequest.of(page, size)).stream()
                .map(TransactionConverter::toTransactionDTO)
                .toList();

        return TransactionConverter.toTransactionListDTO(new PageImpl<>(transactions, PageRequest.of(page, size), transactions.size()));
    }

    @Override
    public TransactionResponseDTO.TransactionListDTO getEarningTransactions(User user, Integer page, Integer size) {
        List<TransactionResponseDTO.TransactionDTO> transactions = transactionRepository.findAllByPoint_UserAndType(user, TransactionType.EARNING, PageRequest.of(page, size)).stream()
                .map(TransactionConverter::toTransactionDTO)
                .toList();

        return TransactionConverter.toTransactionListDTO(new PageImpl<>(transactions, PageRequest.of(page, size), transactions.size()));
    }
}
