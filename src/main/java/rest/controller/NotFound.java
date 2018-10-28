package rest.controller;

public class NotFound extends ApiResponse<Message> {

    NotFound(Message result) {
        super(404,"NOT_FOUND",result);
    }
}
