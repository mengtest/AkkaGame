package utils;

import com.google.protobuf.InvalidProtocolBufferException;

import akka.http.javadsl.model.StatusCode;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import model.GameIsFullException;
import model.GameNotFoundException;
import model.UserAlreadyExistsException;
import model.UserAlreadyInGameException;
import model.UserNotFoundException;
import model.UserNotPlayingGameException;
import model.ErrorProtos.ErrorResponse;

public class ErrorUtils {

	public static Route errorRoute(AllDirectives dirs, Throwable th) {
		String message;
		StatusCode code = StatusCodes.BAD_REQUEST;
		if (th instanceof UserAlreadyInGameException) {
			message = "You are already in a game.";
		} else if (th instanceof UserAlreadyExistsException) {
			message = "User already exists.";
		} else if (th instanceof UserNotFoundException) {
			message = "User doesn't exist.";
		} else if (th instanceof UserNotPlayingGameException) {
			message = "You aren't playing this game.";
		} else if (th instanceof GameNotFoundException) {
			message = "Game doesn't exist.";
		} else if (th instanceof GameIsFullException) {
			message = "Game is already full.";
		} else if (th instanceof NumberFormatException) {
			message = "A number was expected as a parameter.";
		} else {
			message = "Something went wrong.";
			code = StatusCodes.INTERNAL_SERVER_ERROR;
		}
		ErrorResponse resp = ErrorResponse.newBuilder()
			.setCode(code.intValue())
			.setMessage(message)
			.build();
		try {
			return dirs.complete(code, ConversionUtils.toString(resp));
		} catch (InvalidProtocolBufferException e) {
			throw new IllegalArgumentException("Error response proto should be printable.");
		}
	}
}
