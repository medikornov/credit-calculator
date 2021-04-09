package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GraphicalRepr extends Loan {

    GraphicalRepr(UserInput userInput) {
        super(userInput);
    }

    @Override
    public void display()
    {
        NumberAxis xAxis = new NumberAxis(1, userInput.getMonths(), 1);
        xAxis.setLabel("Mėnesiai");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Suma (€)");
        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        Stage window = new Stage();
        VBox vBox = new VBox();
        ObservableList<Payment> payments = getPayment(userInput.isAnnuet());


        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        for (int i = 0; i < userInput.getMonths(); i++) {
            Payment paym = payments.get(i);
            series1.getData().add(new XYChart.Data(paym.getNumber(), paym.getMonthlyPayment()));
            series2.getData().add(new XYChart.Data(paym.getNumber(), paym.getInterestPayment()));
            series3.getData().add(new XYChart.Data(paym.getNumber(), paym.getCreditAmount()));
        }
        series1.setName("Bendra įmoka");
        series2.setName("Palūkanų įmoką");
        series3.setName("Kredito įmoką");
        lineChart.getData().addAll(series1, series2, series3);
        lineChart.getStyleClass().add("thick-chart");

        GridPane gridPane = new GridPane();
        String graph = userInput.isAnnuet() ? "Anuitetas" : "Linijinis";
        gridPane.add(new Label("Pinigų pasiskolinta: "), 0, 0);
        gridPane.add(new Label("Grafikas: "), 0, 1);
        gridPane.add(new Label(graph), 1, 1);
        gridPane.add(new Label("Pinigų grąžinta: "), 0, 2);
        gridPane.add(new Label("Palūkanos: "), 0, 3);
        gridPane.add(new Label(Payment.toString(userInput.getAmount())), 1, 0);
        Payment paym = payments.get(userInput.getMonths() - 1);
        gridPane.add(new Label(Payment.toString(paym.getCurrentSum())), 1, 2);
        gridPane.add(new Label(Payment.toString(paym.getCurrentSum() - userInput.getAmount())), 1, 3);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Button saveButton = new Button("Išsaugoti ataskaitą");

        GridPane gridPaneButton = new GridPane();
        gridPaneButton.setPadding(new Insets(0, 0, 0, 400));
        gridPaneButton.add(saveButton, 0, 0);

        saveButton.setOnAction(e -> {
            String content =
                    "Pinigų paskolinta: " +  Payment.toString(userInput.getAmount()) +
                    "\nGrafikas: " + graph +
                    "\nPinigų grąžinta: " + Payment.toString(paym.getCurrentSum()) +
                    "\nPalūkanos: " + Payment.toString(paym.getCurrentSum() - userInput.getAmount());
            saveTable(content, payments);
        });

        vBox.getChildren().addAll(lineChart, gridPane, gridPaneButton);
        Scene scene = new Scene(vBox, 1000, 600);
        window.setTitle("grafikas");
        window.setScene(scene);
        window.showAndWait();
    }
}
