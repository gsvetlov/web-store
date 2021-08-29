package ru.svetlov.webstore.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ru.svetlov.webstore.exception.BadRequestException;

import java.util.stream.Collectors;

public class ControllerUtil {
    public static void throwIfNotValid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors()
                    .stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", ")));
        }
    }
}
