package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException;

class DataInputStep implements ContractStep {

    private final Contract contract;
    private boolean isScanFromTauronUpload;

    DataInputStep(Contract contract, boolean isScanFromTauronUpload) {
        this.contract = contract;
        this.isScanFromTauronUpload = isScanFromTauronUpload;
    }

    void setScanFromTauronUpload() {
        isScanFromTauronUpload = true;
    }

    boolean getIsScanFromTauronUpload() {
        return isScanFromTauronUpload;
    }

    @Override
    public void confirmStep() {
        if (contract.checkIsScanFromTauronUploaded()) {
            this.contract.setContractStep(new WaitingToPreliminaryMapStep(this.contract));
        } else {
            throw new NoScanFileOnConfirmingException("There is no uploaded scan file.");
        }
    }
}


