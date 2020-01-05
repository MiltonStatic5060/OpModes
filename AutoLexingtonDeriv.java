package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous 1B", group = "Competition2019-2020")
public class AutoLexingtonDeriv extends AutoLexington {
    @Override
    public void loop(){
        // ONLY PICK ONE
        currentTeam = Team.NONE;
        foundation_function();
        // parking_function();
    }
}