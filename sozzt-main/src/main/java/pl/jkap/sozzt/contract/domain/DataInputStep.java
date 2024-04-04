package pl.jkap.sozzt.contract.domain;


import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirming;

class DataInputStep implements ContractStep {

    private final Contract contract;

    void setScanFromTauronUpload() {
        isScanFromTauronUpload = true;
    }

    boolean getIsScanFromTauronUpload() {
        return isScanFromTauronUpload;
    }

    private boolean isScanFromTauronUpload;

    DataInputStep(Contract contract) {
        this.contract = contract;
        isScanFromTauronUpload = false;
    }

    @Override
    public void confirmStep() {
        if (contract.checkIsScanFromTauronUploaded()) {
            this.contract.changeStep(new WaitingToPreliminaryMapStep(this.contract));
        } else {
            throw new NoScanFileOnConfirming("There is no uploaded skan file.");
        }
    }
}


