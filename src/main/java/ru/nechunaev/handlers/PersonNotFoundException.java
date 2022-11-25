package ru.nechunaev.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Person")
public class PersonNotFoundException extends RuntimeException{
}
