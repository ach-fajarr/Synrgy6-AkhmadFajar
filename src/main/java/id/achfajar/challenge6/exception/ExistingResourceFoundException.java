package id.achfajar.challenge6.exception;

import lombok.Getter;

import java.util.UUID;

public class ExistingResourceFoundException extends RuntimeException {

    @Getter
    private Object data;

    @Getter
    private String message;
    public ExistingResourceFoundException(UUID id, Object data) {
        super("Data dengan Id: " + id + " sudah ada");
        this.data = data;
    }
    public ExistingResourceFoundException(String name, Object data) {
        this.message= "Data dengan Nama: " + name + " sudah ada";
        this.data = data;
    }
    public ExistingResourceFoundException(String message){
        this.message= message;
    }
}
