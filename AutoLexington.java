package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous 1", group = "Competition2019-2020")
public class AutoLexington extends OpMode {
    // Set the team 
    Team currentTeam = Team.NONE; // NONE, RED, or BLUE
    int phase_counter = 0; // This counter determines which movement the robot is currently performing
    private ElapsedTime runtime = new ElapsedTime();

    //variables --> these names are used within the code...
    DcMotor motorFL = null;
    DcMotor motorBL = null;
    DcMotor motorFR = null;
    DcMotor motorBR = null;

    DcMotor motorLift = null;
    Servo servoHand;

    @Override
    public void init(){
        //what happens on INITIALIZE

        //drawing the motors from the hardware map on the robot control station
        telemetry.addData("OpMode Status","Initializing hardware");
        motorFL = hardwareMap.get(DcMotor.class, "motFL");
        motorBL = hardwareMap.get(DcMotor.class, "motBL");
        motorFR = hardwareMap.get(DcMotor.class, "motFR");
        motorBR = hardwareMap.get(DcMotor.class, "motBR");

        motorLift = hardwareMap.get(DcMotor.class, "motLift");
        servoHand = hardwareMap.get(Servo.class, "serHand");

        //setting the initial direction of the motors
        //  --> this makes the position the motors are
        //      in when the code is initialized to be
        //      considered "forward".
        telemetry.addData("OpMode Status","Initializing motor directions");
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBL.setDirection(DcMotor.Direction.FORWARD);
        motorFR.setDirection(DcMotor.Direction.FORWARD);
        motorBR.setDirection(DcMotor.Direction.FORWARD);

        motorLift.setDirection(DcMotor.Direction.FORWARD);
        telemetry.addData("OpMode Status","Initializing servo direction");
        servoHand.setDirection(Servo.Direction.FORWARD);
    }

    @Override
    public void start(){
        //what happens on START
        runtime.restart(); // Start the runtime elapsed timer
        phase_counter = 0;
        currentTeam = Team.NONE; // you don't really need to change this value
    }
    
    @Override
    public void loop(){
        // ONLY PICK ONE
        // foundation_function();
        // parking_function();
    }
    

    // Fix these if there are driving issues.  
    // Don't forget that you must tell the motors to stop eventually
    public void drive_cardinal(double x, double y){
        //DRIVE CODE --> north/south/east/west movement
        y = -y; // the analog stick is actually -1.0 for forward, and 1.0 for backward
        motorFL.setPower(-y-x);
        motorBL.setPower(-y+x);
        motorFR.setPower(-y+x);
        motorBR.setPower(-y-x);
    }
    public void drive_rotater(double x, double y){
        //DRIVE CODE --> north/south/east/west movement
        motorFL.setPower(-y+x);
        motorBL.setPower(-y+x);
        motorFR.setPower(-y-x);
        motorBR.setPower(-y-x);
    }
    public double distanceToFoundation(){
        return 999;
    }


    public void foundation_function(){
        switch(phase_counter){
            case 0:
                runtime.restart();
                phase_counter+=1;
                break;
            case 1:
                // Lift the arm
                double power = 0.8;
                float time = 1.0;
                
                motorLift.setPower(power); // The action to lift arm

                telemetry.addData("Status", "Phase %d: %2.5f S Elapsed", phase_counter, runtime.seconds());
                if (runtime.seconds() >= time){
                    motorLift.setPower(0); // STOP ARM
                    phase_counter += 1;
                    runtime.restart();    
                }
                break;
            case 2:
                // Drive forward to the foundation
                double power = 1;
                float time = 1.0;
                double dist = 10; // desired distance as next to the foundation

                drive_cardinal(power,0);

                telemetry.addData("Status", "Phase %d: %2.5f S Elapsed", phase_counter, runtime.seconds());
                if (runtime.seconds() >= time || distanceToFoundation()<=dist ){
                    drive_cardinal(0,0); //STOP THE ROBOT

                    phase_counter += 1;
                    runtime.restart();    
                }
                break;
            case 3:
                // Drop the arm
                double power = -0.8;
                float time = 1.0;
                
                motorLift.setPower(power); // The action to drop arm

                telemetry.addData("Status", "Phase %d: %2.5f S Elapsed", phase_counter, runtime.seconds());
                if (runtime.seconds() >= time){
                    motorLift.setPower(0); // STOP ARM

                    phase_counter += 1;
                    runtime.restart();    
                }
                break;
            case 4:
                // Drive backward with the foundation
                double power = -0.8;
                float time = 1.0;

                drive_cardinal(power,0);

                telemetry.addData("Status", "Phase %d: %2.5f S Elapsed", phase_counter, runtime.seconds());
                if (runtime.seconds() >= time){

                    drive_cardinal(0,0); //STOP THE ROBOT
                    phase_counter += 1;
                    runtime.restart();    
                }
                break;
            default:
                // STOP EVERYTHING
                drive_cardinal(0,0);
                motorLift.setPower(0);
                telemetry.addData("Status", "Trip completed");
        }
        telemetry.update();
    }
    public void parking_function(){
        switch(phase_counter){
            case 0:
                runtime.restart();
                phase_counter+=1;
                break;
            case 1:
                // Drive sideways to the left or right
                double power = 1;
                float time = 1.0;

                switch(currentTeam){
                    case RED: drive_cardinal(0,power); break; // RIGHT
                    case BLUE: drive_cardinal(0,-power); break; // LEFT
                    case NONE: phase_counter+=1; runtime.restart(); break; // Quit this for the next part
                }

                telemetry.addData("Status", "Phase %d: %2.5f S Elapsed", phase_counter, runtime.seconds());
                if (runtime.seconds() >= time){
                    phase_counter += 1;
                    runtime.restart();    
                }
                break;
            case 2:
                // Drive forwards
                double power = 1;
                float time = 1.0;
                
                drive_cardinal(power,0);

                telemetry.addData("Status", "Phase %d: %2.5f S Elapsed", phase_counter, runtime.seconds());
                if (runtime.seconds() >= time){
                    phase_counter += 1;
                    runtime.restart();    
                }
                break;
            default:
                // STOP EVERYTHING
                drive_cardinal(0,0);
                telemetry.addData("Status", "Trip completed");
        }
        telemetry.update();
    }
    public enum Team {
        RED, BLUE, NONE
    }
}

