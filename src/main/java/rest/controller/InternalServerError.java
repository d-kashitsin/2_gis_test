package rest.controller;

public class InternalServerError extends ApiResponse<Message> {
    public InternalServerError(Message result) {
        super(500, "INTERNAL_ERROR", result);
    }
}
