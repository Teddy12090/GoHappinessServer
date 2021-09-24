package idv.teddy.controller;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ValidationErrorResponse {
    List<Violation> violations = new ArrayList<>();
}
