package checks.controlls;

import checks.model.DBManager;
import checks.model.StringOfCheck;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by belikov.a on 17.02.2017.
 */
public class ControllerEditFrame {
    @FXML
    public TextField number;
    @FXML
    public TextField market;
    @FXML
    public TextField section;
    @FXML
    public TextField price;
    @FXML
    public TextField sum;
    @FXML
    public DatePicker date;
    @FXML
    public TextField product;
    private Stage dialogStage;


    //private ReadOnlyObjectProperty<StringOfCheck> object = null;

    @FXML
    private void initialize(){
        sum.setText("1");
    }

    public void setTransaction(ReadOnlyObjectProperty<StringOfCheck> object){

        if(object == null)return;

        //this.object = object;
        number.setText(String.valueOf(object.getValue().getId()));
        market.setText(object.getValue().getMarket());
        section.setText(object.getValue().getSection());
        product.setText(object.getValue().getProduct());
        price.setText(String.valueOf(object.getValue().getPrice()));
        sum.setText("1");
        date.setValue(object.getValue().getDate());

    }

    @FXML
    public void savePosition(MouseEvent mouseEvent) {

        DBManager managerDB = new DBManager();

        if(number.getText().equals("")){

            String query = "INSERT INTO `transaction` (`id`, `market`, `section`, `product`, `price`, `profit`, `time`) VALUES (NULL, '" + this.market.getText() + "', '" + this.section.getText() + "', '" + this.product.getText() + "', '" + Double.parseDouble(this.price.getText()) + "', '" + 0.0 + "', UNIX_TIMESTAMP());";

            managerDB.updateDB(query);

            market.requestFocus();

            market.selectAll();

        }else if(Integer.parseInt(number.getText()) > 0 ){

            String query = "UPDATE `transaction` SET `market` = '" + market.getText() + "' ,`section` = '" + section.getText() + "' ,`product` = '" + product.getText() + "' ,`price` = '" + Double.parseDouble(price.getText()) + "' WHERE `id` = '" + Integer.parseInt(number.getText()) + "';";

            managerDB.updateDB(query);

            dialogStage.close();

        }

    }

    public void closeFrame(MouseEvent mouseEvent) {

        dialogStage.close();

    }

    public void setDialogStage(Stage dialogStage) {

        this.dialogStage = dialogStage;

        market.requestFocus();

        market.selectAll();

    }
}
