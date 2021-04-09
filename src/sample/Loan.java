package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.Math.pow;

public class Loan {

    protected UserInput userInput;
    protected TableView table;

    Loan(UserInput userInput){
        this.userInput = userInput;
    }

    public void display() {
        Stage window = new Stage();
        VBox vbox = new VBox();
        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Paskolos suma: "), 0, 0);
        gridPane.add(new Label("Metinis procentas: "), 0, 1);
        String type = String.valueOf(userInput.isAnnuet() ? "Anuitetas" : "Linijinis");
        gridPane.add(new Label(type.toString()), 0, 2);
        gridPane.add(new Label("Laikotarpis: "), 0, 3);

        gridPane.add(new Label(String.valueOf(userInput.getAmount())), 1, 0);
        gridPane.add(new Label(String.valueOf(userInput.getInterest())), 1, 1);
        gridPane.add(new Label(String.valueOf(userInput.getMonths())), 1, 3);
        gridPane.setPadding(new Insets(10, 10, 10 ,10));
        gridPane.setStyle("-fx-font-size: 15");

        ObservableList<Payment> payments = formatTable();

        gridPane.add(new Label("               "), 2, 0);
        gridPane.add(new Label("Pinigų pasiskolinta: "), 3, 0);
        gridPane.add(new Label("Pinigų grąžinta: "), 3, 1);
        gridPane.add(new Label("Palūkanos: "), 3, 2);
        gridPane.add(new Label(Payment.toString(userInput.getAmount())), 4, 0);
        Payment paym = payments.get(userInput.getMonths() - 1);
        gridPane.add(new Label(Payment.toString(paym.getCurrentSum())), 4, 1);
        gridPane.add(new Label(Payment.toString(paym.getCurrentSum() - userInput.getAmount())), 4, 2);
        Button saveButton = new Button("Išsaugoti ataskaitą");
        gridPane.add(saveButton, 3, 3);

        String graph = userInput.isAnnuet() ? "Anuitetas" : "Linijinis";

        saveButton.setOnAction(e -> {
            String content =
                    "Pinigų paskolinta: " +  Payment.toString(userInput.getAmount()) +
                    "\nGrafikas: " + graph +
                    "\nPinigų grąžinta: " + Payment.toString(paym.getCurrentSum()) +
                    "\nPalūkanos: " + Payment.toString(paym.getCurrentSum() - userInput.getAmount());
            saveTable(content, payments);
        });


        vbox.getChildren().addAll(gridPane, table);
        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.setTitle("ataskaita");
        window.show();
    }
    protected ObservableList<Payment> getPayment(boolean isAnnuity) {
        ObservableList<Payment> payments = FXCollections.observableArrayList();
        if (isAnnuity) {
            double montlyInterestPercent = userInput.getMonthlyPercent()/100;
            double monthlyPayment = (
                    montlyInterestPercent* userInput.getAmount()
                    /
                    (1 - pow((1 + montlyInterestPercent), (-1*userInput.getMonths())))
            );
            double notRoundedMonth = monthlyPayment;
            monthlyPayment = Math.round(monthlyPayment * 100.0) / 100.0;
            double creditAmount = userInput.getAmount();
            double credit = montlyInterestPercent*creditAmount;
            credit = Math.round(credit * 100.00) / 100.00;
            double currentSum = 0;
            for (int i = 1; i <= userInput.getMonths(); i++) {
                currentSum += monthlyPayment;
                if (i == userInput.getMonths()) {
                    creditAmount = monthlyPayment;
                }
                payments.add(new Payment(monthlyPayment - credit, monthlyPayment, i, creditAmount, creditAmount, credit, currentSum));
                creditAmount -= (monthlyPayment - montlyInterestPercent*creditAmount);
                //creditAmount = Math.round(creditAmount * 100.00) / 100.00;
                credit = montlyInterestPercent*creditAmount;
                //credit = Math.round(credit * 100.00) / 100.00;
            }
        }
        else {
            double creditAmount = userInput.getAmount();
            double creditPerMonth = creditAmount / userInput.getMonths();
            double monthlyInterestPercent = userInput.getMonthlyPercent()/100;
            double monthlyPayment;
            double credit;
            double currentSum = 0;
            for (int i = 1; i <= userInput.getMonths(); i++) {
                credit = creditAmount * monthlyInterestPercent;
                monthlyPayment = credit + creditPerMonth;
                currentSum += monthlyPayment;
                if (i == userInput.getMonths()) {
                    creditAmount = monthlyPayment;
                }
                payments.add(new Payment(creditPerMonth, monthlyPayment, i, creditAmount, creditAmount, credit, currentSum));
                creditAmount -= creditPerMonth;
            }
        }
        return payments;
    }
    private ObservableList<Payment> formatTable()
    {
        TableColumn<Payment, String> numberColl = new TableColumn<>("Nr.");
        numberColl.setMinWidth(30);
        numberColl.setCellValueFactory(new PropertyValueFactory<>("numberStr"));

        TableColumn<Payment, String> monthlyPaymentColl = new TableColumn<>("Mėnesio įmoką");
        monthlyPaymentColl.setMinWidth(200);
        monthlyPaymentColl.setCellValueFactory(new PropertyValueFactory<>("monthlyPaymentStr"));

        TableColumn<Payment, String> creditAmountColl = new TableColumn<>("Kreditas");
        creditAmountColl.setMinWidth(100);
        creditAmountColl.setCellValueFactory(new PropertyValueFactory<>("creditAmountStr"));

        TableColumn<Payment, String> interestPaymentColl = new TableColumn<>("Palūkanos");
        interestPaymentColl.setMinWidth(100);
        interestPaymentColl.setCellValueFactory(new PropertyValueFactory<>("interestPaymentStr"));

        TableColumn<Payment, String> amountLeftColl = new TableColumn<>("Mokėti liko");
        amountLeftColl.setMinWidth(100);
        amountLeftColl.setCellValueFactory(new PropertyValueFactory<>("amountLeftStr"));

        ObservableList<Payment> temp;
        table = new TableView<>();
        table.setItems(temp = getPayment(userInput.isAnnuet()));
        table.getColumns().addAll(numberColl, monthlyPaymentColl, creditAmountColl, interestPaymentColl, amountLeftColl);
        return temp;
    }

    protected void saveTable(String content, ObservableList<Payment> observableList){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage saveStage = new Stage();
        File file = fileChooser.showSaveDialog(saveStage);

        if (file != null) {
            saveTextToFile(content, observableList, file);
        }
    }

    private void saveTextToFile(String content, ObservableList<Payment> observableList, File file) {
        try {

            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.write(content + "\n");
            writer.write(Payment.printHeader());
            for (Payment payments : observableList) {
                writer.write(payments.toString() + "\n");
            }
            writer.close();
        } catch (IOException ex) { }
    }

}
