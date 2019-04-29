package by.epam.javawebtraining.kunitski.finaltask.carrental.command;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl.RegisterUserCommand;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();
	private static final Logger LOG = LogManager.getLogger(CommandProvider.class.getName());
	private final static CommandProvider instance = new CommandProvider();
	private final static String INIT_MSG = "CommandProvider : init";

	private CommandProvider() {
		LOG.debug(INIT_MSG);

		commands.put(CommandName.REGISTER, new RegisterUserCommand());

	}

	public Command getCommand(String commandName) {
		Command command = null;
		CommandName key = null;

		commandName = commandName.replace('-', '_').toUpperCase();
		key = CommandName.valueOf(commandName);
		command = commands.get(key);

		return command;
	}

	public static CommandProvider getInstance() {
		return instance;
	}
}
