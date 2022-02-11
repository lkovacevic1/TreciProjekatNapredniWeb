package com.example.TreciProjekatNapredniWeb.response;

import org.springframework.http.HttpStatus;

public class MachineResponse {

    private String response;
    private HttpStatus status;

    public MachineResponse(String response, HttpStatus status) {
        this.response = response;
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
