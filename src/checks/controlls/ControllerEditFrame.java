package checks.controlls;

import checks.model.DBManager;
import checks.model.StringOfCheck;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

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
    public TextField product;
    @FXML
    public Button save;
    @FXML
    public TextField profit;

    private Stage dialogStage;

    private HashSet<String> markets = new HashSet<>();

    private HashSet<String> sections = new HashSet<>();

    private HashSet<String> products = new HashSet<>();


    //private ReadOnlyObjectProperty<StringOfCheck> object = null;

    @FXML
    private void initialize(){

        sum.setText("1");
        price.setText("0.0");
        profit.setText("0.0");

        markets = setSets("market");
        sections = setSets("section");
        products = setSets("product");
    }

    private HashSet<String> setSets(String id) {

        String query = "SELECT `" + id + "` FROM `transaction`";

        ResultSet resultSet = DBManager.getResult(query);

        HashSet setName = new HashSet();
        try {
            while (resultSet.next()) {

                Iterator iter = setName.iterator();

                int count = 0;

                while(iter.hasNext()){

                    if(iter.next().equals(resultSet.getString(1))){
                        count++;
                        break;
                    }

                }

                if(count == 0)setName.add(resultSet.getString(1));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return setName;
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
        profit.setText(String.valueOf(object.getValue().getProfit()));
//        date.setValue(object.getValue().getDate());

    }

    @FXML
    public void savePosition() {

        DBManager managerDB = new DBManager();

        if(number.getText().equals("")){

            String query = "INSERT INTO `transaction` (`id`, `market`, `section`, `product`, `price`, `profit`, `time`) VALUES (NULL, '" + this.market.getText() + "', '" + this.section.getText() + "', '" + this.product.getText() + "', '" + Double.parseDouble(this.price.getText()) + "', '" + Double.parseDouble(this.profit.getText()) + "', UNIX_TIMESTAMP());";

            managerDB.updateDB(query);

            market.requestFocus();

            profit.setText("0.0");

            market.selectAll();

        }else if(Integer.parseInt(number.getText()) > 0 ){

            String query = "UPDATE `transaction` SET `market` = '" + market.getText() + "' ,`section` = '" + section.getText() + "' ,`product` = '" + product.getText() + "' ,`price` = '" + Double.parseDouble(price.getText()) + "' ,`profit` = '" + Double.parseDouble(profit.getText()) + "' WHERE `id` = '" + Integer.parseInt(number.getText()) + "';";

            managerDB.updateDB(query);

            dialogStage.close();

        }

    }

    public void closeFrame() {

        dialogStage.close();

    }

    public void setDialogStage(Stage dialogStage) {

        this.dialogStage = dialogStage;

        market.requestFocus();

        market.selectAll();

    }

    @FXML
    public void nextPos(KeyEvent event) {

        if(!event.getCode().getName().equals("Enter"))return;

        TextField field = (TextField) event.getSource();

        String id = field.getId();

        System.out.println(field);

        if(id.equals("price")){
            sum.requestFocus();
            return;
        }
        if(id.equals("sum")){
            profit.requestFocus();
            return;
        }
        if(id.equals("profit")){
            save.requestFocus();
            return;
        }

        if(id.equals("market"))section.requestFocus();
        if(id.equals("section"))product.requestFocus();
        if(id.equals("product"))price.requestFocus();

    }

    @FXML
    public void typing(KeyEvent event) {

        TextField field = (TextField) event.getSource();

        ContextMenu menu = field.getContextMenu();

        MenuItem item = menu.getItems().get(0);

        System.out.println(event.getCode().getName());

        if(event.getEventType().getName().equals("KEY_RELEASED")) {

            if (event.getCode().getName().equals("Enter")) {

                if(!item.getText().equals("")) {

                    field.setText(item.getText());

                }

                item.setText("");

                field.getContextMenu().hide();

                nextPos(event);

                return;
            }

            String id = field.getId();

            String subS = "";

            try {

                subS = field.getText();

            }catch (Exception e){
                System.out.println(e);
            }

            String matchString = "";

            if (subS.length() != 0) {

                HashSet<String> hashSet = getHashSet(id);


                for (String s : hashSet) {

                    //System.out.println(s);

                    if (s.toLowerCase().contains(field.getText().toLowerCase())) {

                        System.out.println("Открыть");

                        matchString = s;

                        break;

                    }
                }

                if (!matchString.equals("")) {
                    item.setText(matchString);
                    menu.show(field, Side.BOTTOM, 0, 0);
                } else {
                    item.setText("");
                    menu.hide();
                }

            } else {
                item.setText("");
                menu.hide();
            }
        }
    }

    private HashSet<String> getHashSet(String id) {

        HashSet<String> hashSet = null;

        if(id.equals("market")){
            hashSet = markets;
        }
        else if(id.equals("section")){
            hashSet = sections;
        }
        else if(id.equals("product")){
            hashSet = products;
        }
        return hashSet;
    }
}
