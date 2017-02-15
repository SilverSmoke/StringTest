package checks.controlls;

import checks.model.DBManager;
import checks.model.StringOfCheck;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Controller {


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

        initData();

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

                System.out.println(object.getValue().getId());
                System.out.println(object.getValue().getDate());

            }
        });
    }

    private void initData() {

        int i = 0;

        String query = "SELECT * FROM `transaction`";

        double sumPrice = 0;
        double sumProfit = 0;

        ResultSet resultSet = DBManager.getResult(query);

        try{
            while(resultSet.next()){

                i++;

                checkData.add(new StringOfCheck(resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        Double.parseDouble(resultSet.getString(5)), Double.parseDouble(resultSet.getString(6)),
                        LocalDate.ofEpochDay(resultSet.getLong(7) / 86400)));

                sumPrice += Double.parseDouble(resultSet.getString(5));

                sumProfit += Double.parseDouble(resultSet.getString(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        checkData.add(new StringOfCheck(null, "Итог:", null, null, sumPrice, sumProfit, null));

    }

    public void editStart(TableColumn.CellEditEvent<StringOfCheck, String> stringOfCheckStringCellEditEvent) {

        System.out.println(stringOfCheckStringCellEditEvent.getTablePosition());

    }
}