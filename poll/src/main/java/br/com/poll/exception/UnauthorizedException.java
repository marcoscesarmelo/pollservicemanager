package br.com.poll.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
   
	private static final long serialVersionUID = -8006760629397176660L;
	private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public UnauthorizedException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s Not Authorized %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
