package apap.tugasakhir.siretail.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {
        private int status;
        private String message;
        private T result;
}