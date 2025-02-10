package pl.jkap.sozzt.remark.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.remark.dto.AddRemarkDto;
import pl.jkap.sozzt.remark.dto.EditRemarkDto;
import pl.jkap.sozzt.remark.dto.RemarkDto;
import pl.jkap.sozzt.remark.exception.RemarkNotFoundException;

import java.util.Collection;
import java.util.UUID;

@Slf4j
@Builder
public class RemarkFacade {
    RemarkRepository remarkRepository;
    InstantProvider instantProvider;

    public RemarkDto addRemark(AddRemarkDto addRemarkDto) {
        Remark remark = Remark.builder()
                .remarkId(addRemarkDto.getRemarkId().orElse(UUID.randomUUID()))
                .contractId(addRemarkDto.getContractId())
                .remarkType(addRemarkDto.getRemarkType())
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

    public Collection<RemarkDto> getRemarksForContract(UUID contractId, RemarkType remarkType) {
        Collection<Remark> remarks = remarkRepository.findByContractIdAndRemarkContractStep(contractId, remarkType);
        return remarks.stream().map(Remark::dto).toList();
    }

    public Collection<RemarkDto> getRemarksForContract(UUID contractId) {
        Collection<Remark> remarks = remarkRepository.findByContractId(contractId);
        return remarks.stream().map(Remark::dto).toList();
    }

    public RemarkDto markRemarkAsInProgress(UUID remarkId) {
        Remark remark = remarkRepository.findById(remarkId).orElseThrow(() -> new RemarkNotFoundException("Remark not found: " + remarkId));
        remark.markAsInProgress();
        remarkRepository.save(remark);
        return remark.dto();

    }

    public RemarkDto markRemarkAsDone(UUID remarkId) {
        Remark remark = remarkRepository.findById(remarkId).orElseThrow(() -> new RemarkNotFoundException("Remark not found: " + remarkId));
        remark.markAsDone();
        remarkRepository.save(remark);
        return remark.dto();
    }

    public RemarkDto cancelRemark(UUID remarkId) {
        Remark remark = remarkRepository.findById(remarkId).orElseThrow(() -> new RemarkNotFoundException("Remark not found: " + remarkId));
        remark.cancel();
        remarkRepository.save(remark);
        return remark.dto();
    }

    public RemarkDto editRemark(EditRemarkDto editRemarkDto) {
        Remark remark = remarkRepository.findById(editRemarkDto.getRemarkId()).orElseThrow(() -> new RemarkNotFoundException("Remark not found: " + editRemarkDto.getRemarkId()));
        remark.update(editRemarkDto);
        remarkRepository.save(remark);
        return remark.dto();
    }

    public boolean hasActiveRemarksForContract(UUID contractId) {
        Collection<Remark> remarks = remarkRepository.findByContractId(contractId);
        return remarks.stream()
                .anyMatch(remark -> remark.getRemarkStatus() != RemarkStatus.DONE 
                        && remark.getRemarkStatus() != RemarkStatus.CANCELLED);
    }
}
