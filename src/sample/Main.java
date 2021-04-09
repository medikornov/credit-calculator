/**
 * @author Vasaris Kaveckas PS I group 3
 */

package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Main extends Application {

    private TextField textField1;
    private TextField annualInterest;
    private ComboBox monthBox;
    private ComboBox yearBox;
    private RadioButton annuityButton;
    private RadioButton linearButton;
    private Button listReport;
    private Button graphic;
    private Label wrongInput;

    private double amount;
    private double interest;
    private boolean annuet;
    private boolean linear;
    private int months;

    private UserInput userInput;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("loan calculator");
        VBox vbox = new VBox();
        vbox.setSpacing(5);

        wrongInput = new Label();
        wrongInput.setVisible(false);

        monthBox = new ComboBox();
        monthBox.getItems().addAll(
                1, 2, 3, 6, 9, 12
        );
        yearBox = new ComboBox();
        yearBox.getItems().addAll(
                0, 1, 2, 3, 5, 7, 10, 15, 20
        );

        Group root = new Group();

        Label name = new Label("Būsto paskolos skaičiuoklė");
        name.setStyle("-fx-font-size: 21");
        name.setPadding(new Insets(10, 5, 5, 5));

        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(10);
        grid.setPadding(new Insets(30, 5, 5, 5));
        grid.add(new Label("Įveskite paskolos sumą: "), 0, 0);
        textField1 = new TextField();
        textField1.setAlignment(Pos.CENTER_RIGHT);
        grid.add(textField1, 1, 0);
        grid.add(new Label("€"), 2, 0);
        grid.setStyle("-fx-font-size: 15");

        GridPane grid2 = new GridPane();
        grid2.setVgap(4);
        grid2.setHgap(10);
        grid2.setPadding(new Insets(0, 5, 5, 5));
        grid2.setStyle("-fx-font-size: 15");
        grid2.add(new Label("Paskolos terminas, metais ir mėnesiais: "), 0, 0);
        grid2.add(yearBox, 1 ,0);
        grid2.add(new Label("metai"), 2, 0);
        grid2.add(monthBox, 3, 0);
        grid2.add(new Label("mėnesiai"), 4, 0);

        ToggleGroup groupButton = new ToggleGroup();
        annuityButton = new RadioButton("Anuetas");
        annuityButton.setToggleGroup(groupButton);
        annuityButton.setSelected(true);
        linearButton = new RadioButton("Linijinis");
        linearButton.setToggleGroup(groupButton);
        GridPane radioButtonGrid = new GridPane();
        radioButtonGrid.setHgap(10);
        radioButtonGrid.setPadding(new Insets(5, 5, 5, 5));
        radioButtonGrid.add(annuityButton, 0, 0);
        radioButtonGrid.add(linearButton, 1, 0);

        GridPane percentGrid = new GridPane();
        percentGrid.add(new Label("Metinis palūkanų procentas "), 0, 0);
        annualInterest = new TextField();
        annualInterest.setMaxWidth(60);
        annualInterest.setAlignment(Pos.CENTER_RIGHT);
        percentGrid.add(annualInterest, 1, 0);
        percentGrid.add(new Label(" %"), 2, 0);
        percentGrid.setPadding(new Insets(5, 5, 5, 5));
        percentGrid.setStyle("-fx-font-size: 15");


        GridPane buttons = new GridPane();
        listReport = new Button("Bendra ataskaita");
        graphic = new Button("Grafikas");
        buttons.add(listReport, 0, 0);
        buttons.add(graphic, 1, 0);
        buttons.add(new Label("          "), 2, 0);
        buttons.setPadding(new Insets(100, 5, 5, 5));
        buttons.setHgap(10);
        wrongInput.setVisible(false);





        // button controls, parse input
        listReport.setOnAction(e -> {
            if (dataFilledCorrectly()) {
                dataValidation();
                Loan loan = new Loan(userInput);
                loan.display();
            }
        });

        graphic.setOnAction(e -> {
            if (dataFilledCorrectly()) {
                dataValidation();
                GraphicalRepr graph = new GraphicalRepr(userInput);
                graph.display();
            }
        });




        Pattern pattern = Pattern.compile("\\d{0,12}|\\d+\\.\\d{0,2}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        textField1.setTextFormatter(formatter);

        Pattern pattern1 = Pattern.compile("\\d{0,2}|\\d+\\.\\d{0,2}");
        TextFormatter formatter1 = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern1.matcher(change.getControlNewText()).matches() ? change : null;
        });
        annualInterest.setTextFormatter(formatter1);



        vbox.getChildren().addAll(name, grid, grid2, radioButtonGrid, percentGrid, buttons, wrongInput);
        root.getChildren().addAll(vbox);
        Scene scene = new Scene(root, 550, 500);
        scene.setFill(Color.rgb(255, 255, 240));
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private boolean dataFilledCorrectly() {
        boolean doubleParsed = parsingDouble();
        if (doubleParsed && monthBox.getValue() != null) {
            wrongInput.setVisible(false);
            return true;
        }
        if (textField1.getText().isEmpty() && annualInterest.getText().isEmpty()) {
            wrongInput.setText("Nėra įvesties");
        }
        else if (monthBox.getValue() != null) {
            wrongInput.setText("Netinkama įvestis");
        }
        else {
            wrongInput.setText("Nepažymėtas terminas");
        }
        //System.out.println("wrong input");
        wrongInput.setVisible(true);
        return false;
    }


    private boolean parsingDouble() {
        try {
            amount = Double.parseDouble(textField1.getText());
            interest = Double.parseDouble(annualInterest.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void dataValidation() {
        if (annuityButton.isSelected()) {
            annuet = true;
            linear = false;
        }
        else {
            linear = true;
            annuet = false;
        }
        if (yearBox.getValue() != null) months = ((int)yearBox.getValue()) * 12;
        else months = 0;
        months += (int)monthBox.getValue();
        userInput = new UserInput(amount,  interest, annuet, linear, months);
    }
}
