package pl.jkap.sozzt.remark.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.remark.domain.RemarkType;
import pl.jkap.sozzt.remark.domain.RemarkFacade;
import pl.jkap.sozzt.remark.dto.AddRemarkDto;
import pl.jkap.sozzt.remark.dto.EditRemarkDto;
import pl.jkap.sozzt.remark.dto.RemarkDto;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/remarks/")
@SuppressWarnings("unused")
public class RemarkController {

    private final RemarkFacade remarkFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RemarkDto addRemark(@RequestBody AddRemarkDto addRemarkDto) {
        return remarkFacade.addRemark(addRemarkDto);
    }

    @GetMapping("{idContract}/{remarkType}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<RemarkDto> getRemarksForContract(@PathVariable UUID idContract, @PathVariable RemarkType remarkType) {
        return remarkFacade.getRemarksForContract(idContract, remarkType);
    }

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<RemarkDto> getRemarksForContract(@PathVariable UUID idContract) {
        return remarkFacade.getRemarksForContract(idContract);
    }

    @PutMapping("{remarkId}/in-progress")
    @ResponseStatus(HttpStatus.OK)
    public RemarkDto markRemarkAsInProgress(@PathVariable UUID remarkId) {
        return remarkFacade.markRemarkAsInProgress(remarkId);
    }

    @PutMapping("{remarkId}/done")
    @ResponseStatus(HttpStatus.OK)
    public RemarkDto markRemarkAsDone( @PathVariable UUID remarkId) {
        return remarkFacade.markRemarkAsDone(remarkId);
    }

    @PutMapping("{remarkId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public RemarkDto markRemarkAsCancelled(@PathVariable UUID remarkId) {
        return remarkFacade.cancelRemark(remarkId);
    }

    @PutMapping("edit")
    @ResponseStatus(HttpStatus.OK)
    public RemarkDto editRemark(@RequestBody EditRemarkDto editRemarkDto) {
        return remarkFacade.editRemark(editRemarkDto);
    }
}
