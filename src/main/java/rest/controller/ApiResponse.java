package rest.controller;

public class ApiResponse<T> {
    private final int code;
    private final String status;
    private final T result;

    ApiResponse(final int code, final String status, final T result) {
        this.code = code;
        this.status = status;
        this.result = result;
    }

    ApiResponse(final T result) {
        this(200, "OK", result);
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public T getResult() {
        return result;
    }
}
