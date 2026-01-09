// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Subsystem definitions
  private final CoralEndEffectorSubsystem coralEndEffectorSubsystem = new CoralEndEffectorSubsystem();
  private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
  private final AlgaeEndEffectorSubsystem algaeEndEffectorSubsystem = new algaeEndEffectorSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  // Sendable chooser to allow to choose distance for algae arm to move, the range should be from 0 to 1 at 0.1 unit intervals, and should update the position of the algae arm on SmartDashboard
  private final SendableChooser<Double> algaeSetPointChooser = new SendableChooser<>();
  algaeSetPointChooser.setDefaultOption("0.0", 0.0);
  algaeSetPointChooser.addOption("0.1", 0.1);
  algaeSetPointChooser.addOption("0.2", 0.2);
  algaeSetPointChooser.addOption("0.3", 0.3);
  algaeSetPointChooser.addOption("0.4", 0.4);
  algaeSetPointChooser.addOption("0.5", 0.5);
  algaeSetPointChooser.addOption("0.6", 0.6);
  algaeSetPointChooser.addOption("0.7", 0.7);
  algaeSetPointChooser.addOption("0.8", 0.8);
  algaeSetPointChooser.addOption("0.9", 0.9);
  algaeSetPointChooser.addOption("1.0", 1.0);
  SmartDashboard.putData("Algae Set Point Chooser", algaeSetPointChooser);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  // Placeholder level supplier
  private final IntSupplier level = () -> 1;
  private void configureBindings() {

    // ===== KEYBINDINGS FOR THE CONTROLLER =====

    // On pressing the left bumper, eject the coral at 7 volts
    m_driverController.leftBumper()
        .onTrue(Commands.run(() -> coralEndEffectorSubsystem.setVolt(7), coralEndEffectorSubsystem)
            .until(() -> !coralEndEffectorSubsystem.isLowerBeamBroken())
            .andThen(Commands.runOnce(() -> coralEndEffectorSubsystem.stop(), coralEndEffectorSubsystem)));

    // On pressing the right bumper, intake the coral at 7 volts
    m_driverController.rightBumper()
        .onTrue(Commands.run(() -> coralEndEffectorSubsystem.setVolt(7), coralEndEffectorSubsystem)
            .until(() -> coralEndEffectorSubsystem.isObjectIn())
            .andThen(Commands.runOnce(() -> coralEndEffectorSubsystem.stop(), coralEndEffectorSubsystem)));

    // On pressing the a button, eject the coral in a way that makes it turn more to one side to another (at 5.5 volts on the left side), allowing it to rest parallel to the trough (level 1)
    m_driverController.a().onTrue(Commands.run(() -> coralEndEffectorSubsystem.setVoltDiff(5.5), coralEndEffectorSubsystem)
        .until(() -> coralEndEffectorSubsystem.isLowerBeamBroken() && level.getAsInt() == 1)
        .andThen(Commands.runOnce(() -> coralEndEffectorSubsystem.stop(), coralEndEffectorSubsystem)));

    // While pressing the b button, run the elevator at 11 volts
    m_driverController.b().whileTrue(Commands.run(() -> elevatorSubsystem.setVolt(11), elevatorSubsystem)
        .andThen(Commands.runOnce(() -> elevatorSubsystem.stop(), elevatorSubsystem)));

    // While pressing the x button, intake algae at 5 volts
    m_driverController.x().whileTrue(Commands.run(() -> algaeEndEffectorSubsystem.setIntakeVolt(5), algaeEndEffectorSubsystem)
        .andThen(Commands.runOnce(() -> algaeEndEffectorSubsystem.algaeIntakeStop(), algaeEndEffectorSubsystem)));

    // While pressing the y button, run algae pivot motor at 7 volts
    m_driverController.y().whileTrue(Commands.run(() -> algaeEndEffectorSubsystem.setPivotVolt(7), algaeEndEffectorSubsystem)
        .andThen(Commands.runOnce(() -> algaeEndEffectorSubsystem.algaePivotStop(), algaeEndEffectorSubsystem)));

    // Putting the binded controls on SmartDashboard so the driver knows what to press
    SmartDashboard.putString("Left Bumper", "Eject Coral");
    SmartDashboard.putString("Right Bumper", "Intake Coral");
    SmartDashboard.putString("A Button", "Eject Coral with Differential Voltage");
    SmartDashboard.putString("B Button", "Run Elevator");
    SmartDashboard.putString("X Button", "Intake Algae");
    SmartDashboard.putString("Y Button", "Pivot Algae End Effector");

    

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
