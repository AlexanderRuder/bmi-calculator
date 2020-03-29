import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BMI_Calculator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        CheckBox maleCheckbox = new CheckBox("Male");
        maleCheckbox.setSelected(true);
        CheckBox femaleCheckbox = new CheckBox("Female");

        Label massLabel = new Label("Your weight in kg:");
        massLabel.setStyle("-fx-text-fill: blue");
        Label heightLabel = new Label("Your body size in cm:");
        Label msg = new Label();

        TextField massField = new TextField();
        TextField heightField = new TextField();

        Button calculateButton = new Button("Calculate");
        Button exitButton = new Button("Exit");

        VBox root = new VBox();
        root.setSpacing(10);

        HBox buttonBox1 = new HBox();
        buttonBox1.setSpacing(5);
        buttonBox1.getChildren().addAll(maleCheckbox, femaleCheckbox);

        HBox buttonBox2 = new HBox();
        buttonBox2.setSpacing(5);
        buttonBox2.getChildren().addAll(calculateButton, exitButton);

        root.getChildren().addAll(buttonBox1, massLabel, massField, heightLabel, heightField, buttonBox2, msg);

        // Event handler
        maleCheckbox.setOnAction(e -> {
            maleCheckbox.setSelected(true);
            femaleCheckbox.setSelected(false);
        });

        femaleCheckbox.setOnAction(e -> {
            femaleCheckbox.setSelected(true);
            maleCheckbox.setSelected(false);
        });

        exitButton.setOnAction(e -> Platform.exit());

        calculateButton.setOnAction(e -> {
            boolean isMen = maleCheckbox.isSelected();
            String mass = massField.getText();
            String height = heightField.getText();
            if (mass.trim().length() == 0 || height.trim().length() == 0){
                msg.setText("Input is missing");
            }else{
                double massValue = 0.0;
                double heightValue = 0.0;
                double bmi = 0.0;
                try{
                    massValue = Math.abs(Double.parseDouble(mass));
                    heightValue = Math.abs(Double.parseDouble(height));
                    bmi = calculateBMI(massValue, heightValue);
                    msg.setText(String.format("%s, your BMI: %.2f",getStatus(bmi, isMen), bmi));
                }catch (NumberFormatException | ArithmeticException ex ){
                    ex.printStackTrace();
                    msg.setText(String.format("Error, bad input"));
                }
            }
        });

        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Body Mass Index");
        stage.show();
    }

    private static double calculateBMI(double mass, double height) throws ArithmeticException{
        if (Double.compare(height, 0) == 0){
            throw new ArithmeticException("Devide by 0");
        }
        double bmi;
        bmi = mass / Math.pow(height/100, 2);
        return bmi;
    }

    private static String getStatus(double bmi, boolean isMen){
        String status;
        if (isMen) {
            if (bmi < 20.0) {
                status = "Underweight";
            } else if (bmi <= 25.0) {
                status = "Normal weight";
            } else if (bmi <= 30.0) {
                status = "Overweight";
            } else if (bmi <= 40.0) {
                status = "Obesity";
            } else {
                status = "Strong obesity";
            }
        }else{
            if (bmi < 19.0) {
                status = "Underweight";
            } else if (bmi <= 24.0) {
                status = "Normal weight";
            } else if (bmi <= 30.0) {
                status = "Overweight";
            } else if (bmi <= 40.0) {
                status = "Obesity";
            } else {
                status = "Strong Obesity";
            }
        }
        return status;
    }
}
