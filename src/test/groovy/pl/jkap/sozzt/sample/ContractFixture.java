package pl.jkap.sozzt.sample;

public class ContractFixture {
    boolean isZudRequired = true;
    boolean isMapRequired = true;

    public ContractFixture withZudRequired(boolean isZudRequired) {
        this.isZudRequired = isZudRequired;
        return this;
    }

    public ContractFixture withMapRequired(boolean isMapRequired) {
        this.isMapRequired = isMapRequired;
        return this;
    }
}
