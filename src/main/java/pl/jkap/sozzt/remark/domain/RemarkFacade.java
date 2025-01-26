package pl.jkap.sozzt.remark.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.remark.dto.AddRemarkDto;
import pl.jkap.sozzt.remark.dto.EditRemarkDto;
import pl.jkap.sozzt.remark.dto.RemarkDto;
import pl.jkap.sozzt.remark.exception.RemarkNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@Builder
public class RemarkFacade {
    RemarkRepository remarkRepository;
    InstantProvider instantProvider;

    RemarkDto addRemark(AddRemarkDto addRemarkDto) {
        Remark remark = Remark.builder()
                .remarkId(addRemarkDto.getRemarkId())
                .contractId(addRemarkDto.getContractId())
                .remarkContractStep(addRemarkDto.getRemarkContractStep())
                .title(addRemarkDto.getTitle())
                .description(addRemarkDto.getDescription())
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdAt(instantProvider.now())
                .assignedTo(addRemarkDto.getAssignedTo())
                .deadline(addRemarkDto.getDeadline())
                .remarkStatus(RemarkStatus.NEW)
                .build();
        remarkRepository.save(remark);
        log.info("Remark added: {}", remark);
        return remark.dto();
    }

    Collection<RemarkDto> getRemarksForContract(UUID contractId, RemarkContractStep remarkContractStep) {
        Collection<Remark> remarks = remarkRepository.findByContractIdAndRemarkContractStep(contractId, remarkContractStep);
        return remarks.stream().map(Remark::dto).toList();
    }

    Collection<RemarkDto> getRemarksForContract(UUID contractId) {
        Collection<Remark> remarks = remarkRepository.findByContractId(contractId);
        return remarks.stream().map(Remark::dto).toList();
    }

    RemarkDto markRemarkAsInProgress(UUID remarkId) {
        Remark remark = remarkRepository.findById(remarkId).orElseThrow(() -> new RemarkNotFoundException("Remark not found: " + remarkId));
        remark.markAsInProgress();
        remarkRepository.save(remark);
        return remark.dto();

    }

    RemarkDto markRemarkAsDone(UUID remarkId) {
        Remark remark = remarkRepository.findById(remarkId).orElseThrow(() -> new RemarkNotFoundException("Remark not found: " + remarkId));
        remark.markAsDone();
        remarkRepository.save(remark);
        return remark.dto();
    }

    RemarkDto cancelRemark(UUID remarkId) {
        Remark remark = remarkRepository.findById(remarkId).orElseThrow(() -> new RemarkNotFoundException("Remark not found: " + remarkId));
        remark.cancel();
        remarkRepository.save(remark);
        return remark.dto();
    }

    RemarkDto editRemark(EditRemarkDto editRemarkDto) {
        Remark remark = remarkRepository.findById(editRemarkDto.getRemarkId()).orElseThrow(() -> new RemarkNotFoundException("Remark not found: " + editRemarkDto.getRemarkId()));
        remark.update(editRemarkDto);
        remarkRepository.save(remark);
        return remark.dto();
    }
}
