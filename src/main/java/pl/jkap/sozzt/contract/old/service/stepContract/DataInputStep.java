package pl.jkap.sozzt.contract.old.service.stepContract;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.jkap.sozzt.contract.old.model.ContractStep;
import pl.jkap.sozzt.fileContract.domain.FileContract;
import pl.jkap.sozzt.contract.old.model.FileType;
import pl.jkap.sozzt.contract.old.repository.FileContractRepository;

import java.util.Optional;

public class DataInputStep implements Step {

    private final long id;

    private final FileContractRepository fileContractRepository;
    public final static String INFO_ABOUT_NONE_FILE = "Brakuje wgranego pliku zlecenia";


    public DataInputStep(long id, FileContractRepository fileContractRepository) {
        this.id = id;
        this.fileContractRepository = fileContractRepository;
    }

    public Step validateStep() {
        Optional<FileContract> fileContract = Optional.ofNullable(fileContractRepository.findAnyByIdContractAndFileType(id, FileType.CONTRACT_FROM_TAURON)); //todo osobne pole w ktore potwierdzi ze ten plik wgrany
        if (fileContract.isPresent()) {
            return new WaitingToPreliminaryStep();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, INFO_ABOUT_NONE_FILE);
        }
    }

    @Override
    public ContractStep getContractStep() {
        return ContractStep.DATA_INPUT;
    }
}
