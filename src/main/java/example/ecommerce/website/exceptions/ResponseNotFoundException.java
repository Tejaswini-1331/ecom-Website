package example.ecommerce.website.exceptions;

public class ResponseNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;


    public ResponseNotFoundException (String field, String resourceName, String fieldName) {
        super(String.format("%s not found with %s %s",resourceName,field,fieldName));
        this.field = field;
        this.resourceName = resourceName;
        this.fieldName = fieldName;

    }

    public ResponseNotFoundException (String field,String resourceName,Long fieldId) {
        super(String.format("%s not found with %s %s",resourceName,field,fieldId));
        this.field = field;
        this.resourceName = resourceName;
        this.fieldId = fieldId;

    }

}
