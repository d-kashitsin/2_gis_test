package rest.controller;

public class BadRequest extends ApiResponse<Message> {
    public BadRequest(Message result) {
        super(400,"BAD_REQUEST",result);
    }
}
