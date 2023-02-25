// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.SpearConstants;
import frc.robot.commands.Auto1;
import frc.robot.commands.ExtendClaw;
import frc.robot.commands.JoystickControl;
import frc.robot.commands.RetractClaw;
import frc.robot.commands.SetSpearPos;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.SpearPID;
import frc.robot.subsystems.The_Pinch_n_Twist;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */

 //Godspeed lads, o7

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain driveTrain = new DriveTrain();
  private final The_Pinch_n_Twist the_Pinch_n_Twist = new The_Pinch_n_Twist();
  private final SpearPID spearPID = new SpearPID();

  private final CommandXboxController drive1Controller = new CommandXboxController(Constants.driveController_ID);
  private final CommandXboxController mechanism1Controller = new CommandXboxController(Constants.mechanismController_ID);

  private final Command auto1 = new Auto1(driveTrain, null, null);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

//Drive command defaults to Differential Drive using joystick inputs, limelight default should be different (I think)
    driveTrain.setDefaultCommand(new JoystickControl(driveTrain,
     () -> drive1Controller.getRawAxis(Constants.kRight_X),
     () -> drive1Controller.getRawAxis(Constants.kLeft_Y)));

    configureButtonBindings();
  }


  private void configureButtonBindings() {

//Two button pinch (claw) control
    mechanism1Controller.a().onTrue(new RetractClaw(the_Pinch_n_Twist));
    mechanism1Controller.b().onTrue(new ExtendClaw(the_Pinch_n_Twist));

//Exp. toggle pinch (claw) control
    mechanism1Controller.x().toggleOnTrue(Commands.startEnd(
      the_Pinch_n_Twist::pinchExtend,
       the_Pinch_n_Twist::pinchRetract,
        the_Pinch_n_Twist));

//Forward crawl for testing purposes, remove for final build
    drive1Controller.leftBumper().onTrue(new JoystickControl(driveTrain, 
    () -> .05, () -> 0));

//Spear manual control 
    mechanism1Controller.leftBumper().onTrue(new SetSpearPos(
      SpearConstants.kSpearRetract, spearPID));

    mechanism1Controller.rightBumper().onTrue(new SetSpearPos(
      SpearConstants.kSpearExtend, spearPID));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    //Currently blank command, replace or fill in auto1 for autonomous period
    return auto1;
  }
}