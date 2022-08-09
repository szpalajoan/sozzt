package pl.jkap.sozzt.contract.exception;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class MyNotYetImplementedException extends NotYetImplementedException {

    public MyNotYetImplementedException() {
        super();
    }
}
