package com.team3256.database.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "MatchData Not Found")
public class MatchDataNotFoundException extends RuntimeException {
}