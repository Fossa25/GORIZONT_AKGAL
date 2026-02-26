package com.example.proburok.job;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

import com.example.proburok.MQ.DatabaseHandler;
import com.example.proburok.New_Class.Probnik;
import com.example.proburok.New_Class.Probnik1;
import com.example.proburok.New_Class.UserSession;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ProhodCOD extends Configs {
    @FXML private DatePicker data;
    @FXML private TextField gorizont;
    @FXML private TextField sehen;
    @FXML private TextField nameProhod;

    @FXML private TextField nomer;
    @FXML private TextField idi;
    @FXML private Button singUpButtun;
    private String NOMER;
    private Date DATA;
    private String SEHEN;
    private String GORIZONT;
    private String NAME;
    private String DLINA;
    private String PRIVAZKA;
    private String HIFR;
    @FXML private TableView<Probnik> Tablshen;
    @FXML private TableView<Probnik> TablshenV;
    @FXML private TableView<Probnik1> Tablshen1;
    @FXML private Label L_sehen;
    @FXML private TableColumn<Probnik, String> stolb1;
    @FXML private TableColumn<Probnik, Double> stolb2;
    @FXML private TableColumn<Probnik, Double> stolb3;
    @FXML private TableColumn<Probnik, String> stolb1V;
    @FXML private TableColumn<Probnik, Double> stolb2V;
    @FXML private TableColumn<Probnik, Double> stolb3V;

    @FXML private TableColumn<Probnik1, String> stolb11;
    @FXML private TableColumn<Probnik1, String> stolb21;
    @FXML private TableColumn<Probnik, Double> stolb31;

    @FXML private ImageView instr;
    @FXML private ImageView dokumGeolog;
    @FXML private ImageView dokumGeolog11;
    @FXML private ImageView pethat;
    @FXML private ImageView tabl;
    @FXML private ImageView othet;
    @FXML private ComboBox<String> ushatok;
    @FXML private TextField dlina;
    @FXML private TextField privazka;
    @FXML private ImageView PlanVKL;
    @FXML private ImageView PlanVKLNe;
    @FXML private ImageView planVNS;
    @FXML private ImageView planVNSNE;
    @FXML private ImageView planobnov;
  
    @FXML private ImageView PoperVKL;
    @FXML private ImageView PoperVKLNe;
    @FXML private ImageView poperVNS;
    @FXML private ImageView poperVNSNE;
    @FXML private ImageView poperobnov;
    @FXML
    private Label privaz_L;
    @FXML private Label L_dlina;
    private List<Node> nodesToAnimate_TEXT = new ArrayList<>();
    private Map<Node, Double> originalPositions_TEXT = new HashMap<>();
    @FXML
    void initialize() {
        nodesToAnimate_TEXT.addAll(Arrays.asList(privazka,privaz_L));
        saveOriginalPositions();
        data.setValue(LocalDate.now());
        String uchastok = UserSession.getUchastok();
        if (uchastok != null) {
            ushatok.setValue(uchastok);
            Avto_Nomber();
        }
        sehen.setVisible(false);
        L_sehen.setVisible(false);
        gorizont.setTextFormatter(new TextFormatter<>(change -> {
            // Всегда корректируем start и end
            int start = Math.min(change.getRangeStart(), change.getRangeEnd());
            int end = Math.max(change.getRangeStart(), change.getRangeEnd());
            change.setRange(start, end);
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("[0-9]*([.,][0-9]*)?")) {
                return change;
            }
            // Отклоняем невалидные изменения
            return null;
        }));
        dlina.setTextFormatter(new TextFormatter<>(change -> {
            // Всегда корректируем start и end
            int start = Math.min(change.getRangeStart(), change.getRangeEnd());
            int end = Math.max(change.getRangeStart(), change.getRangeEnd());
            change.setRange(start, end);
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("[0-9]*([.,][0-9]*)?")) {
                return change;
            }
            // Отклоняем невалидные изменения
            return null;
        }));
        idi.setVisible(false);
        tabltrabl();

        pethat.setOnMouseClicked(mouseEvent -> openNewScene("/com/example/proburok/Pehat.fxml"));
        openImagedok(tabl,instr,dokumGeolog,dokumGeolog11,"yes");
        othet.setOnMouseClicked(mouseEvent -> {OpenDok(Put_othet,"Отчет_");});


        ushatok.getItems().addAll("Центральный участок", "Восточный участок");

        ushatok.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && data.getValue() != null) {
                Avto_Nomber();
            }
        });
        singUpButtun.setOnMouseClicked(mouseEvent -> {
            NOMER = nomer.getText() != null ? nomer.getText().trim() : "";
            SEHEN = sehen.getText() != null ? sehen.getText().trim() : "";
            GORIZONT = gorizont.getText() != null ? gorizont.getText().trim() : "";
            DLINA = dlina.getText() != null ? dlina.getText().trim() : "";
            PRIVAZKA= privazka.getText() != null ? privazka.getText().trim() : "";
            NAME = nameProhod.getText() != null ? nameProhod.getText().trim() : "";
            HIFR = idi.getText() != null ? idi.getText().trim() : "";
            proverkaImage(Put + "/" + nomer.getText() + "/" + "План", planVNS, planVNSNE, PlanVKL, PlanVKLNe, planobnov);
            proverkaImage(Put + "/" + nomer.getText() + "/" + "Разрез", poperVNS, poperVNSNE, PoperVKL, PoperVKLNe, poperobnov);
           
            LocalDate selectedDate = data.getValue();
            try {
                StringBuilder errors = new StringBuilder();
                if (NOMER.isEmpty()) errors.append("- Не заполнен номер\n");
                if (selectedDate == null) {
                    errors.append("- Не выбрана дата\n");
                } else {
                    DATA = Date.valueOf(selectedDate); // Преобразуем только если дата выбрана
                }
                if (SEHEN.isEmpty()) errors.append("- Не выбрано сечение\n");
                if (GORIZONT.isEmpty()) errors.append("- Не заполнен горизонт\n");
                if (NAME.isEmpty()) errors.append("- Не заполнено название выработки\n");
                if (DLINA.isEmpty()) errors.append("- Не заполнена длинна выработки\n");
                if (PRIVAZKA.isEmpty()) errors.append("- Не заполнена привязка выработки\n");


                if (planVNS.isVisible() || planVNSNE.isVisible()) {
                    errors.append("- Не внесён план\n");
                }
                if (errors.length() > 0) {
                    showAlert("Заполните обязательные поля:\n" + errors);
                    return;
                }
                String nameBD = NAME + " № " + NOMER;
                String prim =  "Требуется геологическое описание" ;

                // Все данные валидны - сохраняем
                System.out.print(HIFR);
                DatabaseHandler Tabl = new DatabaseHandler();
                Tabl.Dobavlenie(NOMER, DATA, SEHEN, GORIZONT,  nameBD,NAME,HIFR,ushatok.getValue(),DLINA,PRIVAZKA,prim);
                ohistka();

            } catch (DateTimeException e) {
                showAlert("Ошибка в формате даты!");
            } catch (Exception e) {
                showAlert("Произошла ошибка: " + e.getMessage());
            }
        });

        setupTooltips(planVNS,PlanVKL,PlanVKLNe,planobnov,
                "Внести изображение плана","Показать план",
                "План не внесён","Обновить изображение плана"); // Добавляем подсказки
        setupTooltips(poperVNS,PoperVKL,PoperVKLNe,poperobnov,
                "Внести изображение  разреза","Показать  разрез",
                " Разрез не внесён","Обновить изображение  разреза");
      

        setupCursor(planVNS,PlanVKL,planVNSNE,PlanVKLNe,planobnov);   // Настраиваем курсор
        setupCursor( poperVNS, PoperVKL, poperVNSNE, PoperVKLNe, poperobnov);
      
        setupImageHandlers();
    }
    private void ohistka() {
        ushatok.setValue("");
        nomer.setText("");
        sehen.setText("");
        sehen.setVisible(false);
        L_sehen.setVisible(false);
        gorizont.setText("");
        dlina.setText("");
        privazka.setText("");
        nameProhod.setText("");
        idi.setText("");
        PlanVKL.setVisible(false);PlanVKLNe.setVisible(true);planobnov.setVisible(false);
        planVNS.setVisible(false); planVNSNE.setVisible(true);
        PoperVKL.setVisible(false);PoperVKLNe.setVisible(true);poperobnov.setVisible(false);
        poperVNS.setVisible(false); poperVNSNE.setVisible(true);
    }
    private void tabltrabl(){
        stolb1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVirobotka()));
        stolb2.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPlohad()).asObject());
        stolb3.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getId()).asObject());

        stolb1V.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVirobotka()));
        stolb2V.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPlohad()).asObject());
        stolb3V.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getId()).asObject());
        // Для второй таблицы
        stolb11.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVirobotka1()));
        stolb21.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNety()));
        stolb31.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getId()).asObject());

        ObservableList<Probnik> tabl = FXCollections.observableArrayList(
                new Probnik("Горно-капитальная", 15.1,1),
                new Probnik("Горно-капитальная", 14.8,2),
                new Probnik("Горно-капитальная", 14.6,3),
                new Probnik("Горно-капитальная", 11.7,4),

                new Probnik("Горно-подготовительная", 12.7,5),
                new Probnik("Горно-подготовительная", 12.2,6),
                new Probnik("Горно-подготовительная", 11.1,7),

                new Probnik("Нарезная выработка", 11.1,8),
                new Probnik("Нарезная выработка", 10.8,9)
        );
        ObservableList<Probnik> tablV = FXCollections.observableArrayList(
                new Probnik("МХВ", 14.2,10),
                new Probnik("ВХВ", 8.1,11)
        );
        ObservableList<Probnik1> tabl1 = FXCollections.observableArrayList(
                new Probnik1 ("«Т»-образное сопряжение","12,7/12,7",12),
                new Probnik1 ("«Т»-образное сопряжение","12,7/11,1",13),
                new Probnik1 ("«Т»-образное сопряжение","11,1/11,1",14),
                new Probnik1 ("«Х»-образное сопряжение","12,7/12,7",15),
                new Probnik1 ("«Х»-образное сопряжение","12,7/11,1",16),
                new Probnik1 ("«Х»-образное сопряжение","11,1/11,1",17)
        );

        stolb1.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px;");
            }
        });
        stolb2.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(item));
                setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px;");
            }
        });
        stolb1V.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px;");
            }
        });
        stolb2V.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(item));
                setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px;");
            }
        });
        stolb11.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px;");
            }
        });
        stolb21.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 14px;");
            }
        });
        Tablshen.setItems(tabl); TablshenV.setItems(tablV);
        Tablshen1.setItems(tabl1);

        Tablshen.setOnMouseClicked(mouseEvent -> {
            Probnik selectedPerson = Tablshen.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                // Отображаем значение из первого столбца ("Имя") в TextField
                sehen.setText(String.valueOf(selectedPerson.getPlohad()));
                idi.setText(String.valueOf(selectedPerson.getId()));
                sehen.setVisible(true);
                L_sehen.setVisible(true);
                dlina.setVisible(true);
                L_dlina.setVisible(true);
                animateNodes(false);

            }
        });
        TablshenV.setOnMouseClicked(mouseEvent -> {
            Probnik selectedPerson = TablshenV.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                // Отображаем значение из первого столбца ("Имя") в TextField
                sehen.setText(String.valueOf(selectedPerson.getPlohad()));
                idi.setText(String.valueOf(selectedPerson.getId()));
                sehen.setVisible(true);
                L_sehen.setVisible(true);
                dlina.setVisible(true);
                L_dlina.setVisible(true);
                animateNodes(false);
            }
        });
        Tablshen1.setOnMouseClicked(mouseEvent -> {
            Probnik1 selectedPerson = Tablshen1.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                // Отображаем значение из первого столбца ("Имя") в TextField
                sehen.setText(String.valueOf(selectedPerson.getNety()));
                idi.setText(String.valueOf(selectedPerson.getId()));
                sehen.setVisible(true);
                L_sehen.setVisible(true);
                dlina.setText("0");
                dlina.setVisible(false);
                L_dlina.setVisible(false);
                animateNodes(true);
            }
        });
    }
    private void saveOriginalPositions() {

        for (Node node : nodesToAnimate_TEXT) {
            originalPositions_TEXT.put(node, node.getTranslateY());
        }
    }

    private void animateNodes(boolean moveUp) {

        double targetY1 = moveUp ? -219 : 0;


        for (Node node : nodesToAnimate_TEXT) {
            animateSingleNode(targetY1, node);
        }
    }
    private void animateSingleNode(double targetY ,Node node ) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), node);
        transition.setToX(targetY);
        transition.setInterpolator(Interpolator.EASE_OUT);
        transition.play();
    }

    private void setupImageHandlers() {

        openImageHandler(PlanVKL, "План",PlanVKLNe,nomer.getText());
        openImageHandler(PoperVKL, "Разрез",PoperVKLNe,nomer.getText());
      

        obnovaImageHandler(planobnov, "План",nomer.getText());
        obnovaImageHandler(poperobnov, "Разрез",nomer.getText());
      

        sozdaniiImageHandler(planVNS, "План",PlanVKL,PlanVKLNe,planVNS,planobnov,planobnov,nomer.getText());
        sozdaniiImageHandler(poperVNS, "Разрез",PoperVKL,PoperVKLNe,poperVNS,poperobnov,planobnov,nomer.getText());
      

    }
    private void Avto_Nomber(){
        String year = String.valueOf(data.getValue().getYear());
        String prefix = "";
        if (ushatok.getValue().equals("Центральный участок")) prefix = "1";
        else if (ushatok.getValue().equals("Восточный участок")) prefix = "2";

        // Получаем следующий порядковый номер из БД
        DatabaseHandler dbHandler = new DatabaseHandler();
        int nextNumber = dbHandler.getNextSequenceNumber(prefix, year);

        // Устанавливаем номер в формате "1-5-2025"
        nomer.setText(prefix + "-" + nextNumber + "-" + year);
        proverkaImage(Put + "/" + nomer.getText() + "/" + "План", planVNS, planVNSNE, PlanVKL, PlanVKLNe, planobnov);
        proverkaImage(Put + "/" + nomer.getText() + "/" + "Разрез", poperVNS, poperVNSNE, PoperVKL, PoperVKLNe, poperobnov);
      
        setupImageHandlers();
    }

}






