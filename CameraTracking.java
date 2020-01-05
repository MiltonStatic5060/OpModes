package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous(name = "CameraTracking 1", group = "Competition2019-2020")
public class CameraTracking extends AutoLexington {
    // Heavily depends on AutoLexington to be correct
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    // With the email mhsrobotclub@gmail.com
    private static final String VUFORIA_KEY = "AdTYAU7/////AAABmWUPy4GFPUlKrPDF0yyzktJ8W5wiGhdU+5/USNH4tZCiPVv8ZUQp0f04SZV2cXItjDIbXYzWKeuuX5zHguQzDFVZiZRNg9lk8ksxa6WLPHWk6Hf1UncmlD9WTyTH+vSCsuHxZ4KZkAs/dmwzjUnJruzr0FjP1WCVZbZhUYmOhogH+Cs5Nt4LBJIMrdAtWotOk4rvzyejLA+S29jvcjhhSKy24h2NxHxq7oHZk3XQfEd4cuE/dS7IS4DPw4b95zjhqprNCplTA1pw7m+eRVhBQAOZ12DPV528SYzV+bYshfMx8ssMbpdSdiZYF5MLYHQpUyf2XWUCXTOafutSaQ0DKsDUYk6TQKw7U1vCQZSUfYXQ";
    
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    List<Recognition> updatedRecognitions;

    @Override
    public void init(){
        super.init();
        // Initialize Vuforia Camera detection
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
    }
    @Override
    public void start(){
        //what happens on START
        runtime.restart(); // Start the runtime elapsed timer
        phase_counter = 0;
        currentTeam = Team.NONE; // you don't really need to change this value
        updatedRecognitions = null;
    }

    

    @Override
    public void loop(){
        // Update Vuforia, print data
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            updatedRecognitions = tfod.getUpdatedRecognitions();
        }
        loopVuforia();
        telemetry.addData("Elapsed Time","%2.5f S have passed",runtime.seconds());

        telemetry.update();
    }
    private void loopVuforia(){
        if (tfod != null) {
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                        recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                        recognition.getRight(), recognition.getBottom());
                }
            }
        }
    }
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}