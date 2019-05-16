package by.epam.javawebtraining.kunitski.finaltask.carrental.command;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl.*;
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
		commands.put(CommandName.TO_REGISTRATION, new ToRegPageCommand());
		commands.put(CommandName.LOGIN_USER, new LoginUserCommand());
		commands.put(CommandName.CHANGE_LOCALE, new ChangeLocalCommand());
		commands.put(CommandName.REGISTER, new RegisterUserCommand());
		commands.put(CommandName.VIEW_CAR, new ViewCarCommand());
		commands.put(CommandName.VIEW_ALL_CARS, new ViewAllCarsCommand());
		commands.put(CommandName.TO_PRIV_OFFICE_USER, new ToPrivateOfficeUserCommand());
		commands.put(CommandName.TO_HOME_PAGE, new ToHomePageCommand());
		commands.put(CommandName.VIEW_CLASS, new ViewClassCommand());
		commands.put(CommandName.VIEW_CLASS_UNUSED, new ViewUnusedClassCars());
		commands.put(CommandName.TO_ABOUT, new ToAboutCommand());
	}

	public static CommandProvider getInstance() {
		return instance;
	}

	public Command getCommand(String commandName) {
		Command command = null;
		CommandName key = null;

		commandName = commandName.replace('-', '_').toUpperCase();
		key = CommandName.valueOf(commandName);
		command = commands.get(key);

		return command;
	}

}
