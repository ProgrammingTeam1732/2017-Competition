package org.usfirst.frc.team1732.robot.commands.helpercommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CommandSwitcher extends Command {

	private final BooleanSupplier	optionSupplier;
	private final Command			ifTrue;
	private final Command			ifFalse;
	private Command					chosen;

	public CommandSwitcher(BooleanSupplier optionSupplier, Command ifTrue, Command ifFalse) {
		this.optionSupplier = optionSupplier;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (optionSupplier.getAsBoolean()) {
			chosen = ifTrue;
			chosen.start();
		} else {
			chosen = ifFalse;
			chosen.start();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return chosen.isRunning();
	}

}
