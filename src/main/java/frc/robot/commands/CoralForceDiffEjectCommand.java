// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


// Intergrates the package called frc.robot.commands; A package is a classes and interfaces (basically functions).
package frc.robot.commands;

// Inports the Command class from the edu.wpi.first.wpilibj2.command package.
import edu.wpi.first.wpilibj2.command.Command;

// Imports the subsystem (which is also a class) CoralEndEffectorSubsystem from the the CoralEndEffectorSubsystem.java file.
import frc.robot.subsystems.CoralEndEffectorSubsystem;

// Imports the IntSupplier class from the java.util.function package.
import java.util.function.IntSupplier;

// Public means that this class can be used outside of this class.
// Class means it's a class.
// CoralForceDiffEjectCommand is the name of the class.
// Extends Command means that this class takes the Command class and adds more to it.
public class CoralForceDiffEjectCommand extends Command {

  // Private means that this variable can only be used in this class.
  // Final means that this variable cannot be changed after it's been assigned a value.
  // CoralEndEffectorSubsystem is the data type of the variable.
  // --> Data types are the type of data that a variable can hold. In this case, it holds a CoralEndEffectorSubsystem object.
  // --> An object is an instance of a class. An instance being a specific example of a class (creating a copy of CoralEndEffectorSUbsystem), which we hold in the following variable.
  // coralEndEffectorSubsystem is the name of the variable.
  private final CoralEndEffectorSubsystem coralEndEffectorSubsystem;

  // Private means that this variable can only be used in this class.
  // IntSupplier is a functional interface that represents a supplier of int-valued results. It has a single method getAsInt() that returns an int.
  // A supplier is something that supplies a value when called.
  // This is better than just declaring an int because it allows us to pass in a function that returns an int, rather than just a static int value.
  // If I just used an int, then the value would be fixed when the command is created and couldn't change.
  // But because I'm using an IntSupplier, I can pass in a function that returns the current level, which can change over time. Basically, it gives the level, not just at one snapshot in time, but rather whenever we need it.
  // level is the name of the variable.
  private IntSupplier level;

  // Public means that this constructor can be used outside of this class.
  // --> A constructor is a special method that is called when an object of this class is created. It is used to initialize the object.
  // --> Initialize means to set the initial values of the object's variables.
  // CoralForceDiffEjectCommand is the name of the constructor. It must be the same as the class name.
  // (CoralEndEffectorSubsystem c, IntSupplier l) are the parameters (also called arguments) of the constructor. They are the values that must be passed in when creating an object of this class.
  // --> Parameters are like variables that are only used in the constructor.
  // --> They are used to pass in values from outside the class to be used inside the class.
  // --> c and l are the names of the parameters. They can be any valid variable name.
  public CoralForceDiffEjectCommand(CoralEndEffectorSubsystem c, IntSupplier l) {
    // The keyword called "this" refers to the current object of the class, basically the class talking about itself.
    // this.coralEndEffectorSubsystem refers to the coralEndEffectorSubsystem variable in the class.
    // c refers to the parameter passed into the constructor, look up at the parameters of the constructor.
    this.coralEndEffectorSubsystem = c;

    // this.level refers to the level variable in the class.
    // l refers to the parameter passed into the constructor, look up at the parameters of the constructor.
    this.level = l;

    // addRequirements() is a method from the Command class that specifies which subsystems are required by this command.
    // --> This is important for the command scheduler to know which commands can run at the same time and which cannot.
    // --> The command scheduler is a part of the WPILib command-based framework that manages the execution of commands.
    // --> Basically, it makes sure that two commands that require the same subsystem don't run at the same time, which would cause conflicts.
    // --> E.g. if we want to eject and intake at the same time, both commands would try to control the same motors, which would be bad.
    // --> It would be like trying to drive a car with two people, one person trying to go forward and the other trying to go backward.
    // --> It would be pretty hard, if not impossible to do.
    // c is the CoralEndEffectorSubsystem object passed into the constructor.
    addRequirements(c);
  }

  // The following methods are overrides of the Command class methods.
  // Basically, we say that we don't want the Command class's version of these methods, we want our own version.
  @Override

  // Public means that this method can be used outside of this class.
  // Void means that this method does not return any value.
  // This method is called when the command is initially scheduled, AKA when it is initialized, or first started.
  public void initialize() {}

  // See above for explainations of override, public, and void.
  // Execute means to run or carry out, in this case, it means to run the code inside this method until this command is stopped.
  @Override
  public void execute() {
    // This calls the setDiffVolt method from the CoralEndEffectorSubsystem class, here from the object coralEndEffectorSubsystem.
    // This method sets the voltage of the motors to a differential voltage, meaning one motor gets more voltage than the other.
    coralEndEffectorSubsystem.setDiffVolt(5.5);
  }

  // See above for explainations of override, public, and void.
  // The end command automatically runs when the command ends, either because it finished or was interrupted by another command.
  @Override
  public void end(boolean interrupted) {
    // This calls the stop method from the CoralEndEffectorSubsystem class, here from the object coralEndEffectorSubsystem.
    // This method stops the motors by setting their voltage to 0.
    coralEndEffectorSubsystem.stop();
  }

  // See above for explainations of override and public.
  // This method returns a boolean value (true or false) that indicates whether the command has finished.
  // isFinished means that the command has completed its task and can be stopped, this command is run repeatedly until this method returns true.
  // When it returns true, the command ends and the end() method is called.
  @Override
  public boolean isFinished() {
    // This returns true if the lower beam break is not broken (meaning there is no object in the lower part of the end effector) and the level is not equal to 1.
    // The level is obtained by calling the getAsInt() method of the IntSupplier level.
    // --> The getAsInt() method returns the current int value supplied by the IntSupplier.
    // --> Basically, it gives us the current level.
    return !coralEndEffectorSubsystem.isLowerBeamBroken() && level.getAsInt() != 1;
  }
}
