package pl.jkap.sozzt.contractsecurity.exception;

public class UnauthorizedTerrainVisionEditException extends ContractSecurityException {
    public UnauthorizedTerrainVisionEditException() {
        super("Terrain vision edit not allowed");
    }
}
