// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class JoystickControl extends CommandBase {

private final DriveTrain driveTrain;
private final DoubleSupplier moveSpeed;
private final DoubleSupplier rotateSpeed;



  public JoystickControl(DriveTrain driveTrain, DoubleSupplier moveSpeed, DoubleSupplier rotateSpeed) {
    this.moveSpeed = moveSpeed;
    this.rotateSpeed = rotateSpeed;
    this.driveTrain = driveTrain;

    addRequirements(driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double realMoveSpeed = moveSpeed.getAsDouble();
    double realRotateSpeed = rotateSpeed.getAsDouble();

    driveTrain.setArcadeSpeed(realMoveSpeed, realRotateSpeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.setArcadeSpeed(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}