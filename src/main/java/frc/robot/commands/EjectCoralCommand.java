// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralEndEffectorSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class EjectCoralCommand extends Command {
  private final CoralEndEffectorSubsystem coralEndEffectorSubsystem;

  /** Creates a new EjectCoralCommand. */
  public EjectCoralCommand(CoralEndEffectorSubsystem c) {
    // Use addRequirements() here to declare subsystem dependencies.
    coralEndEffectorSubsystem = c;
    addRequirements(c);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    coralEndEffectorSubsystem.setVolt(7);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    coralEndEffectorSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !coralEndEffectorSubsystem.isLowerBeamBroken();
  }
}
