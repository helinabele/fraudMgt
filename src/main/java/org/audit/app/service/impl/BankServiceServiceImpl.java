package org.audit.app.service.impl;

import java.util.Optional;
import org.audit.app.domain.BankService;
import org.audit.app.repository.BankServiceRepository;
import org.audit.app.service.BankServiceService;
import org.audit.app.service.dto.BankServiceDTO;
import org.audit.app.service.mapper.BankServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link BankService}.
 */
@Service
public class BankServiceServiceImpl implements BankServiceService {

    private final Logger log = LoggerFactory.getLogger(BankServiceServiceImpl.class);

    private final BankServiceRepository bankServiceRepository;

    private final BankServiceMapper bankServiceMapper;

    public BankServiceServiceImpl(BankServiceRepository bankServiceRepository, BankServiceMapper bankServiceMapper) {
        this.bankServiceRepository = bankServiceRepository;
        this.bankServiceMapper = bankServiceMapper;
    }

    @Override
    public BankServiceDTO save(BankServiceDTO bankServiceDTO) {
        log.debug("Request to save BankService : {}", bankServiceDTO);
        BankService bankService = bankServiceMapper.toEntity(bankServiceDTO);
        bankService = bankServiceRepository.save(bankService);
        return bankServiceMapper.toDto(bankService);
    }

    @Override
    public BankServiceDTO update(BankServiceDTO bankServiceDTO) {
        log.debug("Request to update BankService : {}", bankServiceDTO);
        BankService bankService = bankServiceMapper.toEntity(bankServiceDTO);
        bankService = bankServiceRepository.save(bankService);
        return bankServiceMapper.toDto(bankService);
    }

    @Override
    public Optional<BankServiceDTO> partialUpdate(BankServiceDTO bankServiceDTO) {
        log.debug("Request to partially update BankService : {}", bankServiceDTO);

        return bankServiceRepository
            .findById(bankServiceDTO.getId())
            .map(existingBankService -> {
                bankServiceMapper.partialUpdate(existingBankService, bankServiceDTO);

                return existingBankService;
            })
            .map(bankServiceRepository::save)
            .map(bankServiceMapper::toDto);
    }

    @Override
    public Page<BankServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankServices");
        return bankServiceRepository.findAll(pageable).map(bankServiceMapper::toDto);
    }

    @Override
    public Optional<BankServiceDTO> findOne(String id) {
        log.debug("Request to get BankService : {}", id);
        return bankServiceRepository.findById(id).map(bankServiceMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete BankService : {}", id);
        bankServiceRepository.deleteById(id);
    }
}
