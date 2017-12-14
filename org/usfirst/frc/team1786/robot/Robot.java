package org.usfirst.frc.team1786.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import com.ctre.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	CANTalon leftTalonFront = new CANTalon(1);
	CANTalon leftTalonBack = new CANTalon(2);
	CANTalon rightTalonFront = new CANTalon(3);
	CANTalon rightTalonBack = new CANTalon(4);
	RobotDrive myRobot = new RobotDrive(leftTalonFront, leftTalonBack, rightTalonFront, rightTalonBack);

	Joystick leftStick = new Joystick(0);
	
	float deadZone = 0.10f;
	double inputValue;
	double moveValue;
	double inputMagnitude;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		// This is the magnitude of the vector (x , y)
		//inputMagnitude = Math.sqrt((Math.pow(leftStick.getX(), 2)) + (Math.pow(leftStick.getY(), 2)));
		
		//if (inputMagnitude < deadZone) {
		//	moveValue = 0.0;
		//} 
		//else {
		//	moveValue = ((inputMagnitude - deadZone) / (1 - deadZone));
		//}
		double rawInput = -leftStick.getY();
		
		// when inputMultiplier = 1, small inputs have lots of control,
		// when inputMultiplier = 0, inputs are directly mapped
		// the throttle has been remapped to 0->1, and controls the multiplier
		double mappedThrottle = ((leftStick.getThrottle() +1 / 2));
		double inputMultiplier = mappedThrottle;
		
		// apply an exponential curve to the driving input
		// ax^3 + (1-a)x
		moveValue = inputMultiplier * Math.pow(rawInput, 5) + (1 - inputMultiplier) * rawInput;
		
		double twistValue = 1 * Math.pow(-leftStick.getZ(), 3) + (1 - inputMultiplier) * -leftStick.getZ();
		
		myRobot.arcadeDrive(moveValue, twistValue);
		
		SmartDashboard.putNumber("raw z", leftStick.getZ());
		SmartDashboard.putNumber("raw x", leftStick.getX());
		SmartDashboard.putNumber("raw y", leftStick.getY());
		SmartDashboard.putNumber("throttle", leftStick.getThrottle());
		SmartDashboard.putNumber("scaled movement", moveValue);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

