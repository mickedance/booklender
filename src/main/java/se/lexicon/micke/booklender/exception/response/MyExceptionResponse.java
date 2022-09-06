package se.lexicon.micke.booklender.exception.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class MyExceptionResponse{
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final Integer statusCode;

    private final String message;

    public MyExceptionResponse( Integer statusCode, String message) {
        this.statusCode = statusCode;

        this.message = message;
    }
}
