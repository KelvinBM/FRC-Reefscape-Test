// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Limelight extends SubsystemBase {

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tx = table.getEntry("tx");
  private NetworkTableEntry ty = table.getEntry("ty"); // gives angle of center of camera to target
  private NetworkTableEntry ta = table.getEntry("ta"); // target area = 0 - 100% of image

  /** Creates a new Limelight. */
  public Limelight() {}

  // get values
  public double getLimelightY() {
    return ty.getDouble(0.0);
  }

  public double getLimelightX() {
    return tx.getDouble(0.0);
  }

  public double getLimelightArea() {
    return ta.getDouble(0.0);
  }

  public double getDistanceToGoal() {
    double targetOffsetAngle_Vertical = getLimelightY();
    
    double angleToGoalDegrees = Constants.Limelight.CAMERA_MOUNT_ANGLE_DEG + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180);
    double distanceFromLimelightToGoalInches = (Constants.Limelight.GOAL_HEIGHT_INCHES - Constants.Limelight.CAMERA_HEIGHT_INCHES) / Math.tan(angleToGoalRadians);

    double distanceToTarget = distanceFromLimelightToGoalInches - Constants.Limelight.CAMERA_TO_EDGE_OF_ROBOT_INCHES;

    return distanceToTarget;
  }

  public void uploadToSmartDashboard() {
    SmartDashboard.putNumber("LimelightX", getLimelightX());
    SmartDashboard.putNumber("LimelightY", getLimelightY());
    SmartDashboard.putNumber("LimelightArea", getLimelightArea());
    SmartDashboard.putNumber("Target Distance", getDistanceToGoal());
    SmartDashboard.updateValues(); 
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("LimelightX", getLimelightX());
    SmartDashboard.putNumber("LimelightY", getLimelightY());
    SmartDashboard.putNumber("LimelightArea", getLimelightArea());
    SmartDashboard.putNumber("Target Distance", getDistanceToGoal());
    SmartDashboard.updateValues();
    // This method will be called once per scheduler run
  }
}
