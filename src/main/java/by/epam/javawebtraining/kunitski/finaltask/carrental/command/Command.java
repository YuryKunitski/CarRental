package by.epam.javawebtraining.kunitski.finaltask.carrental.command;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	public String execute(HttpServletRequest request) throws CommandException;
}
