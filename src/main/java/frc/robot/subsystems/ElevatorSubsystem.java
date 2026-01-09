// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
  private final SparkMax leftElevatorMotor = new SparkMax(39, MotorType.kBrushless);
  private final SparkMax rightElevatorMotor = new SparkMax(38, MotorType.kBrushless);

  /** Creates a new ElevatorSubsystem. */
  public ElevatorSubsystem() {
    rightElevatorMotor.setInverted(true);
  }

  public void setVolt(double volts) {
    leftElevatorMotor.setVoltage(volts);
    rightElevatorMotor.setVoltage(volts);

    SmartDashboard.putNumber("Elevator Voltage", volts);
  }

  public void stop() {
    setVolt(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putData(this);
  }
}
