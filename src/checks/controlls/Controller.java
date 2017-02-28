package checks.controlls;

import checks.Main;
import checks.model.DBManager;
import checks.model.StringOfCheck;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Controller {


    @FXML
    public DatePicker interval;
    @FXML
    public Label sumPrice;
    @FXML
    public Label balance;
    @FXML
    public Label sumProfit;

    private ObservableList<StringOfCheck> checkData = FXCollections.observableArrayList();

    @FXML
    public TableView<StringOfCheck> area;
    @FXML
    public TableColumn<StringOfCheck, String> id;
    @FXML
    public TableColumn<StringOfCheck, String> market;
    @FXML
    public TableColumn<StringOfCheck, String> section;
    @FXML
    public TableColumn<StringOfCheck, String> product;
    @FXML
    public TableColumn<StringOfCheck, Double> price;
    @FXML
    public TableColumn<StringOfCheck, Double> profit;
    @FXML
    public TableColumn<StringOfCheck, LocalDate> date;

    @FXML
    public void initialize(){



        interval.setValue(LocalDate.now());

        id.setCellValueFactory(new PropertyValueFactory<StringOfCheck, String>("id"));
        market.setCellValueFactory(new PropertyValueFactory<StringOfCheck, String>("market"));
        section.setCellValueFactory(new PropertyValueFactory<StringOfCheck, String>("section"));
        product.setCellValueFactory(new PropertyValueFactory<StringOfCheck, String>("product"));
        price.setCellValueFactory(new PropertyValueFactory<StringOfCheck, Double>("price"));
        profit.setCellValueFactory(new PropertyValueFactory<StringOfCheck, Double>("profit"));
        date.setCellValueFactory(new PropertyValueFactory<StringOfCheck, LocalDate>("date"));

        area.setItems(checkData);
        area.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {

                ReadOnlyObjectProperty<StringOfCheck> object = area.getSelectionModel().selectedItemProperty();

                try {

                    System.out.println(object.getValue());
                    System.out.println(object.getValue().getDate());

                }catch (NullPointerException er) {
                    System.out.println(er);

                }

            }
        });
        initData("");
    }

    @FXML
    public void initData(String query) {

        checkData.clear();



        if(query.equals("")) {

            LocalDate date = interval.getValue();
            LocalDateTime dateTime = LocalDate.of(date.getYear(), date.getMonth(), 1).atStartOfDay();
            Long secondStart = dateTime.toEpochSecond(ZoneOffset.ofHours(6));
            Long secondEnd = dateTime.plusMonths(1).toEpochSecond(ZoneOffset.ofHours(6));

            query = "SELECT * FROM `transaction` WHERE `time` >= " + secondStart + " AND `time` < " + secondEnd + " ORDER BY `time`;";

        }

        double priceSum = 0;
        double profitSum = 0;

        ResultSet resultSet = DBManager.getResult(query);

        //int i = 0;

        try{
            while(resultSet.next()){

                //i++;

                checkData.add(new StringOfCheck(Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        Double.parseDouble(resultSet.getString(5)), Double.parseDouble(resultSet.getString(6)),
                        LocalDate.ofEpochDay(resultSet.getLong(7) / 86400)));

                priceSum += Double.parseDouble(resultSet.getString(5));

                profitSum += Double.parseDouble(resultSet.getString(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        sumPrice.setText(String.valueOf(priceSum));

        sumProfit.setText(String.valueOf(profitSum));

        balance.setText(String.valueOf(profitSum - priceSum));


    }

    public void editStart(TableColumn.CellEditEvent<StringOfCheck, String> stringOfCheckStringCellEditEvent) {

        System.out.println(stringOfCheckStringCellEditEvent.getTablePosition());

    }

    public void openDialogForEdit(MouseEvent mouseEvent) {

        ReadOnlyObjectProperty<StringOfCheck> object = area.getSelectionModel().selectedItemProperty();

        if(object.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            //alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Selected");
            alert.setContentText("Please select a string in the table.");

            alert.showAndWait();

            return;
        }

        //System.out.print(Main.showEditFrame(object));
        if(Main.showEditFrame(object)){
            initData("");
        }

    }

    public void openDialogEditForNew(MouseEvent mouseEvent) {

        //System.out.print(Main.showEditFrame(null));

        if(Main.showEditFrame(null)){
            initData("");
        }

    }


    public void deleteTransaction(MouseEvent mouseEvent) {

        ReadOnlyObjectProperty<StringOfCheck> object = area.getSelectionModel().selectedItemProperty();

        if(object.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            //alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Selected");
            alert.setContentText("Please select a string in the table.");

            alert.showAndWait();

            return;
        }else {

            String query = "DELETE FROM `transaction` WHERE `id` = " + object.getValue().getId() + "";

            DBManager managerDB = new DBManager();

            managerDB.updateDB(query);

            initData("");

        }

    }

    @FXML
    public void goForm() {

        /*LocalDate date = interval.getValue();
        LocalDateTime dateTime = LocalDate.of(date.getYear(), date.getMonth(), 1).atStartOfDay();
        Long secondStart = dateTime.toEpochSecond(ZoneOffset.ofHours(6));
        Long secondEnd = dateTime.plusMonths(1).toEpochSecond(ZoneOffset.ofHours(6));

        String query = "SELECT * FROM `transaction` WHERE `time` >= " + secondStart + " AND `time` < " + secondEnd + " ORDER BY `time`;";
*/
        initData("");

    }
}