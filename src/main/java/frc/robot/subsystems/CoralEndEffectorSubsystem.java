// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CoralEndEffectorSubsystem extends SubsystemBase {
  private final SparkMax leftCoralMotor = new SparkMax(53, MotorType.kBrushless);
  private final SparkMax rightCoralMotor = new SparkMax(52, MotorType.kBrushless);

  private final DigitalInput upperBeamBreak = new DigitalInput(9);
  private final DigitalInput lowerBeamBreak = new DigitalInput(8);

  /** Creates a new CoralEndEffectorSubsystem. */
  @SuppressWarnings("deprecation")
  public CoralEndEffectorSubsystem() {
    rightCoralMotor.setInverted(true);
  }

  public void setVolt(double volts) {
    leftCoralMotor.setVoltage(volts);
    rightCoralMotor.setVoltage(volts);

    SmartDashboard.putNumber("Coral End Effector Voltage", volts);
  }

  public void setDiffVolt(double volts) {
    leftCoralMotor.setVoltage(0.5 * volts);
    rightCoralMotor.setVoltage(volts);
  }

  public void stop() {
    setVolt(0);
  }

  public boolean isUpperBeamBroken() {
    return !upperBeamBreak.get();
  }

  public boolean isLowerBeamBroken() {
    return !lowerBeamBreak.get();
  }

  public boolean isObjectIn() {
    return !isUpperBeamBroken() && isLowerBeamBroken();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Coral Upper Beam Broken", isUpperBeamBroken());
    SmartDashboard.putBoolean("Coral Lower Beam Broken", isLowerBeamBroken());
    SmartDashboard.putData(this);
  }
}
