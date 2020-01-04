
                ///////////////////////////////////////////////
                //THIS IS THE CODE FOR ROBOT: "ARCHITECT" 1.0//
                ///////////////////////////////////////////////

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "GregorTheInvalid", group = "Competition2019-2020")
public class TeleOpLexingtonGregorTheInvalid extends OpMode {
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
    }

    @Override
    public void loop(){
        //what happens during TeleOp phase

        //DRIVE CODE --> north/south/east/west movement
        motorFL.setPower(-gamepad1.left_stick_y-gamepad1.left_stick_x);
        motorBL.setPower(-gamepad1.left_stick_y+gamepad1.left_stick_x);
        motorFR.setPower(-gamepad1.left_stick_y+gamepad1.left_stick_x);
        motorBR.setPower(-gamepad1.left_stick_y-gamepad1.left_stick_x);

        //DRIVE CODE --> forward/backward/rotation movement
        motorFL.setPower(-gamepad1.right_stick_y+gamepad1.right_stick_x);
        motorBL.setPower(-gamepad1.right_stick_y+gamepad1.right_stick_x);
        motorFR.setPower(-gamepad1.right_stick_y-gamepad1.right_stick_x);
        motorBR.setPower(-gamepad1.right_stick_y-gamepad1.right_stick_x);

        //LIFT CODE --> up/down
        motorLift.setPower(gamepad1.left_trigger-gamepad1.right_trigger);

        //HAND CODE --> open/close
        if(gamepad1.right_bumper){
            servoHand.setPosition(0.5); // --> CLOSE
        } else if(gamepad1.left_bumper){
            servoHand.setPosition(0.0); // --> OPEN
        }
    }
}
