// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.math.controller.PIDController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class AlgaeEndEffectorSubsystem extends SubsystemBase {
  private final SparkMax algaePivotMotor = new SparkMax(60, MotorType.kBrushless);
  private final SparkMax algaeMotor = new SparkMax(61, MotorType.kBrushless);

  private final DutyCycleEncoder algaeEncoder = new DutyCycleEncoder(7);

  private final PIDController PIDController = new PIDController(0, 0, 0);
  
  private final double zeroPosition = 0.53;

  /** Creates a new AlgaeEndEffectorSubsystem. */
  public AlgaeEndEffectorSubsystem() {}

  publid void algaeSetPoint(double setPoint) {
    // Adjust for zero position
    double currentPosition = getAlgaePosition() - zeroPosition;

    // Calculates the appropriate motor output using PID control
    double percentageOutput = PIDController.calculate(setPoint, currentPosition);

    // Bus (manages power) is voltage of the motor controller, usually the max voltage (12V)
    // Multiply percentage output by bus voltage to get actual voltage to apply
    // We need to invert because the encoder and the motor go spinny spin spin in opposite directions
    double voltageOutput = percentageOutput * algaePivotMotor.getBusVoltage() * -1;
    algaePivotMotor.setVoltage(voltageOutput);

    SmartDashboard.putNumber("Algae Pivot Voltage Output", voltageOutput);
  }

  public void setAlgaeVoltage(double voltage) {
    algaeMotor.setVoltage(voltage);
    SmartDashboard.putNumber("Algae End Effector Voltage", voltage);
  }

  public void stop() {
    setAlgaeVoltage(0);
  }

  @Override
  public void periodic() {
    // Put value of encoder on SmartDashboard
    SmartDashboard.putNumber("Algae Encoder Position", algaeEncoder.get());
    SmartDashboard.putData(this);

    // Update the set point from SmartDashboard from the chooser
    this.algaeSetPoint(algaeSetPointChooser.getSelected());
  }
}
