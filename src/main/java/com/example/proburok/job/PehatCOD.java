package com.example.proburok.job;

import com.example.proburok.New_Class.Baza;
import com.example.proburok.MQ.DatabaseHandler;
import com.example.proburok.New_Class.TemplateResource;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import org.apache.xmlbeans.XmlCursor;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PehatCOD extends Configs {
    private static final Logger log = LogManager.getLogger(PehatCOD.class);
    private String Photosxemtefat;
    private String Photovidkrepi;
    private String Photoilement;
    private String Photosxemprovet;
    private String Photovidkrepi2;
    private String ankerPM;
    private String ankerR;

    private String setkaPM;
    private String setkaR;
    private String karkasPM;
    private String karkasR;
    private String ampulaPM;
    private String ampulaR;
    private String ampulaPM2;
    private String ampulaR2;

    List<String> soprigenii = Arrays.asList("12","13","14","15","16","17");
    List<String> ov2 = Arrays.asList(  "13", "15","17","19","21","23","25","27","29","33","35");

    List<String> zabutovka = Arrays.asList( "0");
    private static final Map<String, String> PLACEHOLDER_MAP;
    static {
        PLACEHOLDER_MAP = new HashMap<>();
        PLACEHOLDER_MAP.put("${nomer}", "nomer");
        PLACEHOLDER_MAP.put("${name}", "name");
        PLACEHOLDER_MAP.put("${sehen}", "sehen");
        PLACEHOLDER_MAP.put("${gorizont}", "gorizont");
        PLACEHOLDER_MAP.put("${opisanie}", "opisanie");
        PLACEHOLDER_MAP.put("${kategorii}", "kategorii");
        PLACEHOLDER_MAP.put("${privazka}", "privazka");
        PLACEHOLDER_MAP.put("${plan}", "plan");
        PLACEHOLDER_MAP.put("${poper}", "poper");
        PLACEHOLDER_MAP.put("${dlina}", "dlina");
        PLACEHOLDER_MAP.put("${tipovoi}", "tipovoi");
        PLACEHOLDER_MAP.put("${sxematexfakt}", "sxematexfakt");
        PLACEHOLDER_MAP.put("${obvid}", "obvid");
        PLACEHOLDER_MAP.put("${konstrk}", "konstrk");
        PLACEHOLDER_MAP.put( "${ukazanie}", "tfops");
        PLACEHOLDER_MAP.put("${obvid2}", "obvid2");
        PLACEHOLDER_MAP.put("${sxema}", "sxema");
        PLACEHOLDER_MAP.put("${dnomer}", "dnomer");
    }
    @FXML private ImageView instr;
    @FXML private TextField cehen;
    @FXML private TextField bdname;
    @FXML private ComboBox<String> gorbox;
    @FXML private TextField katigoria;
    @FXML private TextArea opisanie;
    @FXML private ComboBox<String> namebox;
    @FXML private TextField nomer;
    @FXML private TextField nomer1;

    @FXML private Button singUpButtun;
    @FXML private TextField privazka;
    @FXML private TextField idi;
    @FXML private TextField ush;
    @FXML private TextField dlina;
    private final DatabaseHandler dbHandler = new DatabaseHandler();
    @FXML private TextArea primhanie;
    @FXML private CheckBox cb;
    @FXML private Button singUpButtun1;
    @FXML private ImageView sxemaVKL;
    @FXML private ImageView sxemaVKLNe;
    @FXML private ImageView sxemaVNS;
    @FXML private ImageView sxemaVNSNE;
    @FXML private ImageView sxemaobnov;
    @FXML private ImageView PlanVKL;
    @FXML private ImageView PlanVKLNe;
    @FXML private ImageView PoperVKL;
    @FXML private ImageView PoperVKLNe;
    @FXML private ImageView poperVNS;
    @FXML private ImageView poperVNSNE;
    @FXML private ImageView poperobnov;

    @FXML private TextField katigoria1;
    @FXML private TextArea opisanie1;
    @FXML private AnchorPane GM;
    private Popup popup;
    @FXML private AnchorPane DOP_MENU;
    @FXML private Button singUpButtun2;
    @FXML private ImageView PlanVKL1;
    @FXML private ImageView PlanVKLNe1;
    @FXML private ImageView planVNS1;
    @FXML private ImageView planVNSNE1;
    @FXML private ImageView planobnov1;
    @FXML private TextField nomer11;
    @FXML private TextField interval;
    @FXML private TextField dop;
    @FXML private TableView<ObservableList<StringProperty>> dataTable;
    @FXML private TableColumn<ObservableList<StringProperty>, String>  column1;
    @FXML private TableColumn<ObservableList<StringProperty>, String>  column2;
    @FXML private TableColumn<ObservableList<StringProperty>, String> column3;
    @FXML private TableColumn<ObservableList<StringProperty>, String> column4;
    @FXML private TableColumn<ObservableList<StringProperty>, String> column5;
    @FXML private TableColumn<ObservableList<StringProperty>, String> column6;
    @FXML private TableColumn<ObservableList<StringProperty>, String> column7;
    @FXML private TableColumn<ObservableList<StringProperty>, String> column8;
    private String OT;
    private String DO;


    @FXML
    void initialize() {
        primhanie.visibleProperty().bind(cb.selectedProperty());
        singUpButtun1.visibleProperty().bind(cb.selectedProperty());

        singUpButtun2.setVisible(false);
        DOP_MENU.setVisible(false);
        cb.setVisible(false);

        idi.setVisible(false);
        bdname.setVisible(false);
        ush.setVisible(false);
        singUpButtun1.setOnMouseClicked(mouseEvent -> {
            String selectedGor = gorbox.getValue();
            String selectedName = namebox.getValue();
            try {
                // Проверка полей по очереди
                StringBuilder errors = new StringBuilder();
                if (selectedGor == null || selectedGor.isEmpty()) {
                    errors.append("- Не выбран горизонт\n");
                }
                if (selectedName == null || selectedName.isEmpty()) {
                    errors.append("- Не выбрано название выработки\n");
                }
                // Если есть ошибки - показываем их
                if (errors.length() > 0) {
                    showAlert("Заполните обязательные поля:\n" + errors.toString());
                    return;
                }
                new DatabaseHandler().DobavleniePRIM(primhanie.getText(), selectedGor, selectedName);
                clearFields();
                //gorbox.setValue(null);
            } catch (Exception e) {
                showAlert("Произошла ошибка: " + e.getMessage());
            }
        });
        setupComboBoxes();
        setupButtonAction();
        setupCursor(sxemaVNS,sxemaVKL,sxemaVNSNE,sxemaVKLNe,sxemaobnov);
        setupCursor( poperVNS, PoperVKL, poperVNSNE, PoperVKLNe, poperobnov);
        setupCursor_2(PlanVKL,PlanVKLNe);
        setupCursor(planVNS1,PlanVKL1,planVNSNE1,PlanVKLNe1,planobnov1);
        setupTooltips(sxemaVNS,sxemaVKL,sxemaVKLNe,sxemaobnov,
                "Внести изображение схемы вентиляции", "Показать схему вентиляции",
                "Схема вентиляции не внесена","Обновить изображение схемы вентиляции");
        setupTooltips(poperVNS,PoperVKL,PoperVKLNe,poperobnov,
                "Внести изображение  разреза","Показать  разрез",
                " Разрез не внесён","Обновить изображение  разреза");
        setupTooltips(planVNS1,PlanVKL1,PlanVKLNe1,planobnov1,
                "Внести изображение плана","Показать план",
                "План не внесён","Обновить изображение плана");

        setupImageHandlers();
        singUpButtun.setVisible(false);
        instr.setOnMouseClicked(mouseEvent -> {OpenDok(Put_instr, "Инструкция_");});

        popup = new Popup();
        popup.getContent().add(GM);
        // Обработчик для кнопки
        singUpButtun2.setOnMouseEntered(e -> {
            if (!popup.isShowing()) {
                // Получаем экранные координаты кнопки
                Point2D point = singUpButtun2.localToScreen(0, 0);

                // Показываем popup рядом с кнопкой
                popup.show(
                        singUpButtun2.getScene().getWindow(),
                        point.getX()-804, // справа от кнопки
                        point.getY() -752
                );
            }
        });
        singUpButtun2.setOnMouseExited(e -> popup.hide());
        setupTable();
    }
    private void setupComboBoxes() {
        ObservableList<String> horizons = FXCollections.observableArrayList(
                dbHandler.getAllBaza().stream()
                        .map(Baza::getGORIZONT)
                        .distinct()
                        .collect(Collectors.toList())
        );
        gorbox.setItems(horizons);

        gorbox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                updateNamesComboBox(newVal);
            }
        });
        namebox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null  && !newVal.isEmpty() && gorbox.getValue() != null) {
                populateFields(gorbox.getValue(), newVal);
                cb.setVisible(true);

            }
        });

        addTextChangeListener(nomer);addTextChangeListener(bdname);
        addTextChangeListener(katigoria);addTextChangeListener(cehen);
        addTextChangeListener(nomer1);addTextChangeListener(privazka);
        addTextChangeListener(dlina);
    }

    private void addTextChangeListener(TextField field) {
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            checkFieldsAndUpdateButton();
        });
    }
    private void checkFieldsAndUpdateButton() {
        boolean allValid = validateRequiredFields() &&
                gorbox.getValue() != null &&
                namebox.getValue() != null;
        singUpButtun.setDisable(!allValid);
    }
    private void updateNamesComboBox(String horizon) {
        ObservableList<String> names = FXCollections.observableArrayList(
                dbHandler.poiskName(horizon).stream()
                        .map(Baza::getNAME)
                        .distinct()
                        .collect(Collectors.toList())
        );
        clearFields();
        namebox.setItems(names);
    }

    private void populateFields(String horizon, String name) {
        Baza data = dbHandler.danii(horizon, name);
        if (data != null) {
            updateUI(data);
        } else {
            clearFields();
            singUpButtun.setVisible(false);
        }
    }
    private void updateUI(Baza data) {
        Platform.runLater(() -> {
            try {
                nomer.setText(data.getNOMER());
                bdname.setText(data.getNAME_BD());
                katigoria.setText(data.getKATEGORII());
                opisanie.setText(data.getOPISANIE());
                cehen.setText(data.getSEHEN());
                privazka.setText(data.getPRIVIZKA());
                nomer1.setText(data.getTIPPAS());
                dlina.setText(data.getDLINA());
                primhanie.setText(data.getPRIM());
                idi.setText(data.getIDI());
                ush.setText(data.getUHASTOK());
                setupImageHandlers();
                // Обновляем изображения
                proverkaImageGeolg(Put + "/" + nomer.getText() + "/" + "План", PlanVKL, PlanVKLNe);
                proverkaImage(Put + "/" + nomer.getText() + "/" + "Разрез", poperVNS, poperVNSNE, PoperVKL, PoperVKLNe, poperobnov);
                proverkaImage(Put + "/" + nomer.getText() + "/" + "Схема", sxemaVNS, sxemaVNSNE, sxemaVKL, sxemaVKLNe, sxemaobnov);

                // Проверяем заполненность полей
                boolean allValid = validateRequiredFields();
                singUpButtun.setVisible(allValid);
                singUpButtun.setDisable(!allValid);

                if (!allValid && primhanie.getText() != null && !primhanie.getText().isEmpty()) {
                    showAlert(primhanie.getText());
                }
            } catch (Exception e) {
                log.error("Error updating UI", e);
                showAlert("Ошибка при обновлении данных: " + e.getMessage());
            }

            loadDataFromDatabase(data.getNOMER(),dataTable);
        });
    }
    private boolean validateRequiredFields() {
        return isFieldValid(nomer) &&
                isFieldValid(bdname) &&
                isFieldValid(katigoria) &&
                isFieldValid(cehen) &&
                isFieldValid(nomer1) &&
                isFieldValid(dlina) &&
                isFieldValid(ush) &&
                isFieldValid(privazka)&&
                isPrimValid(primhanie.getText());
    }
    private boolean isFieldValid(TextField field) {
        return field != null && field.getText() != null && !field.getText().trim().isEmpty();
    }
    private boolean isPrimValid(String tx) {
        if (tx == null) return true;

        return switch(tx.trim()) {
            case "Требуется геологическое описание",
                 "Необходима привязка выработки" -> false;
            default -> true;
        };
    }
    private void clearFields() {
      //  namebox.setValue(null);
        singUpButtun2.setVisible(false);
        DOP_MENU.setVisible(false);
        nomer.clear();bdname.clear();
        katigoria.clear();opisanie.setText("");
        cehen.clear();nomer1.clear();
        privazka.clear();dlina.clear();
        primhanie.clear();cb.setSelected(false);
        idi.clear();ush.clear();
        this.Photosxemtefat = "";this.Photovidkrepi = "";
        this.Photoilement = "";this.Photosxemprovet = "";this.Photovidkrepi2 = "";
        this.ankerPM = "";this.ankerR = "";
        this.setkaPM = "";this.setkaR = "";
        this.karkasPM = "";this.karkasR = "";
        this.ampulaPM = "";this.ampulaR = "";
        this.ampulaPM2 = "";this.ampulaR2 = "";
        katigoria1.clear();opisanie1.setText("");
        nomer11.clear(); dop.clear();
        proverkaImage(Put + "/" + nomer.getText() + "/" + "Схема", sxemaVNS, sxemaVNSNE, sxemaVKL, sxemaVKLNe, sxemaobnov);
        proverkaImageGeolg(Put + "/" + nomer.getText() + "/" + "План", PlanVKL,PlanVKLNe);
        proverkaImage(Put + "/" + nomer.getText() + "/" + "Разрез", poperVNS, poperVNSNE, PoperVKL, PoperVKLNe, poperobnov);
        proverkaImage(Put + "/" + nomer.getText() + "/" + "План_"+interval.getText()+"_"+dop.getText(), planVNS1, planVNSNE1, PlanVKL1, PlanVKLNe1, planobnov1);
    }
    private void setupButtonAction() {
        singUpButtun.setOnAction(event -> handleDocumentGeneration());
        singUpButtun2.setOnAction(event -> handleDocumentGeneration_dop());
    }
    private void handleDocumentGeneration() {
        String selectedGor = gorbox.getValue();
        String selectedName = namebox.getValue();
        String kategoriyaValue = katigoria.getText();
        String opisanieValue = opisanie.getText();
        String selectPrivizka = privazka.getText();
        String dlinaValue = dlina.getText();
        proverkaImage(Put + "/" + nomer.getText() + "/" + "Схема", sxemaVNS, sxemaVNSNE, sxemaVKL, sxemaVKLNe, sxemaobnov);
        proverkaImageGeolg(Put + "/" + nomer.getText() + "/" + "План", PlanVKL,PlanVKLNe);
        proverkaImage(Put + "/" + nomer.getText() + "/" + "Разрез", poperVNS, poperVNSNE, PoperVKL, PoperVKLNe, poperobnov);
        try {
            StringBuilder errors = new StringBuilder();
            if (selectedGor == null || selectedGor.isEmpty()) {
                errors.append("- Не выбран горизонт\n");
            }
            if (selectedName == null || selectedName.isEmpty()) {
                errors.append("- Не выбрано название выработки\n");
            }
            if (selectPrivizka == null || selectPrivizka.isEmpty()) {
                errors.append("- Не заполнена привязка\n");
            }
            if (dlinaValue == null || dlinaValue.isEmpty()) {
                errors.append("- Не заполнена длина выработки\n");
            }
            if (kategoriyaValue == null || kategoriyaValue.isEmpty()) {
                errors.append("- Не заполнено поле категория \n");
            }
            if (opisanieValue == null || opisanieValue.isEmpty()) {
                errors.append("- Не заполнено поле описание\n");
            }
            if (sxemaVNS.isVisible() || sxemaVNSNE.isVisible()) {
                errors.append("- Не внесена схема вентиляции\n");
            }
            if (poperVNS.isVisible() || poperVNSNE.isVisible()) {
                errors.append("- Не внесен разрез\n");
            }

            if (errors.length() > 0) {
                showAlert("Заполните обязательные поля:\n" + errors.toString());
                return;
            }
            String vidkripi = nomer1.getText() + ".jpg";
            if (soprigenii.contains(idi.getText())) {
                int ps2 = Integer.parseInt(nomer1.getText())+1;
                String vidkripi2 = ps2 + ".jpg";
                this.Photovidkrepi2 = getRESURS(HABLON_PATH_SOPR, vidkripi2);
               this.Photovidkrepi = getRESURS(HABLON_PATH_SOPR, vidkripi);
                this.Photoilement = getRESURS(HABLON_PATH_ILIMENT, "2.jpg");
                this.Photosxemtefat = getUstanovka_SOPR(nomer1.getText());
            } else {
                this.Photovidkrepi = getRESURS(HABLON_PATH_VID, vidkripi);
              if  (ov2.contains(nomer1.getText())) {
                    int ps2 = Integer.parseInt(nomer1.getText())+1;
                  String vidkripi2 = ps2 + ".jpg";
                  this.Photovidkrepi2 = getRESURS(HABLON_PATH_VID, vidkripi2);
              }
                this.Photosxemtefat = getUstanovka(nomer1.getText());
                this.Photoilement = getIlement(nomer1.getText());
            }
            this.Photosxemprovet = getPoto(Put + "/" + nomer.getText() + "/" + "Схема", 0);
            try {
                if (validateInput()) {
                    generateWordDocument(
                            nomer.getText(),

                            this.Photosxemtefat,
                            this.Photovidkrepi,
                            this.Photoilement,
                            this.Photovidkrepi2,
                            ""
                    );
                }
            } catch (IOException | InvalidFormatException e) {
                handleError(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
           showAlert("Произошла ошибка: " + e.getMessage());
        }
    }
    private void handleDocumentGeneration_dop() {
        String selectedGor = gorbox.getValue();
        String selectedName = namebox.getValue();
        String kategoriyaValue = katigoria1.getText();
        String opisanieValue = opisanie1.getText();
        String selectPrivizka = privazka.getText();
        String dlinaValue = interval.getText();
        proverkaImage(Put + "/" + nomer.getText() + "/" + "План_"+interval.getText()+"_"+dop.getText(), planVNS1, planVNSNE1, PlanVKL1, PlanVKLNe1, planobnov1);


        try {
            StringBuilder errors = new StringBuilder();
            if (selectedGor == null || selectedGor.isEmpty()) {
                errors.append("- Не выбран горизонт\n");
            }
            if (selectedName == null || selectedName.isEmpty()) {
                errors.append("- Не выбрано название выработки\n");
            }
            if (selectPrivizka == null || selectPrivizka.isEmpty()) {
                errors.append("- Не заполнена привязка\n");
            }
            if (dlinaValue == null || dlinaValue.isEmpty()) {
                errors.append("- Не заполнен интервал выработки\n");
            }
            if (kategoriyaValue == null || kategoriyaValue.isEmpty()) {
                errors.append("- Не заполнено поле категория \n");
            }
            if (opisanieValue == null || opisanieValue.isEmpty()) {
                errors.append("- Не заполнено поле описание\n");
            }
            if (planVNS1.isVisible() || planVNSNE1.isVisible()) {
                errors.append("- Не внесен дополнительный план\n");
            }
            if (errors.length() > 0) {
                showAlert("Заполните обязательные поля:\n" + errors.toString());
                return;
            }
            String vidkripi = nomer11.getText() + ".jpg";
            if (soprigenii.contains(idi.getText())) {
                int ps2 = Integer.parseInt(nomer11.getText())+1;
                String vidkripi2 = ps2 + ".jpg";
                this.Photovidkrepi2 = getRESURS(HABLON_PATH_SOPR, vidkripi2);
                this.Photovidkrepi = getRESURS(HABLON_PATH_SOPR, vidkripi);

            } else {
                this.Photovidkrepi = getRESURS(HABLON_PATH_VID, vidkripi);
                if  (ov2.contains(nomer11.getText())) {
                    int ps2 = Integer.parseInt(nomer11.getText())+1;
                    String vidkripi2 = ps2 + ".jpg";
                    this.Photovidkrepi2 = getRESURS(HABLON_PATH_VID, vidkripi2);
                }
            }
            try {
                if (validateInput()) {
                    generateWordDocument_dop(
                            nomer.getText(),
                            this.Photosxemtefat,
                            this.Photovidkrepi,
                            this.Photoilement,
                            this.Photovidkrepi2,
                            dop.getText()
                    );
                }
            } catch (IOException | InvalidFormatException e) {
                handleError(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            showAlert("Произошла ошибка: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        if (gorbox.getValue() == null || namebox.getValue() == null) {
            showAlert( "Выберите горизонт и название!");
            return false;
        }
        return true;
    }
    private void rashet(String list,String DD) throws ParseException {
        // Проверка входных данных
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Значение list не может быть пустым");
        }
        if (dlina.getText() == null || dlina.getText().trim().isEmpty()) {
            throw new ParseException("Значение длины не может быть пустым", 0);
        }

        switch (list) {

            case "25","27","33" -> setTabl(4.0, 3.4, 4.4);
            case "1","2","4","5","7","8","10","11" -> setTabl(5.0, 4.4, 0.0);
            case "13","15","19","21" -> setTabl(5.0, 4.4, 5.6);
            case "35" -> setTabl(5.0, 4.4, 5.5);
            case "3", "6", "9","12" -> setTabl(6.0, 5.4, 0.0);
            case"17","23","29" -> setTabl(6.0, 5.4, 6.7);
            case "40","41","42" -> setTabl(10.0, 10.4, 0.0);
            case "37","38","39"  -> setTabl(14.0, 14.8, 0.0);

            default ->  setTabl(0.0, 0.0, 0.0);
        }

        // Обработка ввода с заменой запятых на точки
        String input = DD.trim().replace(',', '.');

        try {
            double dlina_Dobl = Double.parseDouble(input);
            double ankerPM_Dobl = Double.parseDouble(this.ankerPM);
            double setkaPM_Dobl = Double.parseDouble(this.setkaPM);
            double karkasPM_Dobl = Double.parseDouble(this.karkasPM);
            double ampulaPM_Dobl = ankerPM_Dobl * 5;
            double ampulaPM2_Dobl = ankerPM_Dobl * 4;
            double ankerR_Doble = dlina_Dobl * ankerPM_Dobl;
            double setkaR_Doble = dlina_Dobl * setkaPM_Dobl;
            double karkasR_Doble = dlina_Dobl * karkasPM_Dobl;
            double ampulaR_Doble = dlina_Dobl * ampulaPM_Dobl;
            double ampulaR2_Doble = dlina_Dobl * ampulaPM2_Dobl;

            this.ankerR = String.format(Locale.US, "%.0f", Math.ceil(ankerR_Doble));
            this.setkaR = String.format(Locale.US, "%.1f", setkaR_Doble);
            this.ampulaPM = String.format(Locale.US, "%.0f", ampulaPM_Dobl);
            this.ampulaR = String.format(Locale.US, "%.0f", Math.ceil(ampulaR_Doble));
            this.ampulaPM2 = String.format(Locale.US, "%.0f", ampulaPM2_Dobl);
            this.ampulaR2 = String.format(Locale.US, "%.0f", Math.ceil(ampulaR2_Doble));
            this.karkasR = String.format(Locale.US, "%.0f", karkasR_Doble);
            System.out.println(this.ampulaPM + "  "+ this.ampulaR );

        } catch (NumberFormatException e) {
            throw new ParseException("Некорректный числовой формат", 0);
        }
    }
    private void setTabl(Double anker, Double setka, Double nabrizk) {
        this.ankerR = "";this.setkaR = "";
        this.karkasR = "";this.ampulaPM = "";
        this.ampulaR = "";
        this.ankerPM = String.valueOf(anker);
        this.setkaPM = String.valueOf(setka);
        this.karkasPM = String.valueOf(nabrizk);
    }
    private void setTabl_SOPR(Double anker, Double setka, Double amp,Double amp2,Double kar) {
        this.ankerR = "";this.setkaR = "";
        this.karkasR = "";
        this.ampulaR = "";this.ampulaR2 = "";
        this.ankerPM = String.valueOf(anker);
        this.setkaPM = String.valueOf(setka);
        this.karkasPM = String.valueOf(kar);
        this.ampulaPM=String.valueOf(amp);
        this.ampulaPM2=String.valueOf(amp2);
    }

    private void rashet_SOPR(String list) throws ParseException {
        // Проверка входных данных
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Значение list не может быть пустым");
        }
        switch (list) {
            case "13","15" -> setTabl_SOPR(86.0, 92.7, 430.0,344.0,96.0);
            case "1","3" -> setTabl_SOPR(97.0,109.0,485.0,388.0,109.0);
            case "7","9" -> setTabl_SOPR(100.0,99.8,500.0,400.0,106.0);
            case "5" -> setTabl_SOPR(106.0,113.2,530.0,424.0,116.0);
            case "11" -> setTabl_SOPR(106.0,108.1,530.0,424.0,116.0);
            case "17" -> setTabl_SOPR(106.0,100.2,530.0,424.0,110.0);
            case "31","33" -> setTabl_SOPR(116.0,124.3,580.0,464.0,136.0);
            case "19","21" -> setTabl_SOPR(125.0,134.1,625.0,500.0,147.0);
            case "23"-> setTabl_SOPR(127.0,145.3,635.0,508.0,152.0);
            case "35" -> setTabl_SOPR(128.0,134.7,640.0,512.0,136.0);
            case "25","27" -> setTabl_SOPR(130.0,131.9,650.0,520.0,138.0);
            case "29" -> setTabl_SOPR(132.0,142.9,660.0,528.0,148.0);
            default ->  setTabl(0.0, 0.0, 0.0);
        }

    }

    private void generateWordDocument(String... params) throws IOException, InvalidFormatException, ParseException {

        Map<String, String> tableData = new HashMap<>();

        tableData.put("${name}", bdname.getText());
        tableData.put("${sehen}", cehen.getText());
        tableData.put("${ush}", ush.getText().replace("участок","").trim());
        tableData.put("${ush1}", ush.getText().replace("участок","").trim());
        tableData.put("${gorizont}", gorbox.getValue());
        tableData.put("${privazka}", privazka.getText());
        tableData.put("${dlina}", dlina.getText());
        tableData.put("${kategorii}", katigoria.getText());
        tableData.put("${opisanie}", opisanie.getText());
        tableData.put("${interval}","0 ÷ "+ dlina.getText());

        tableData.put("${plan}",   getPoto(Put + "/" + nomer.getText() + "/" + "План", 0));
        tableData.put("${poper}",  getPoto(Put + "/" + nomer.getText() + "/" + "Разрез", 0));
        tableData.put("${sxema}",  getPoto(Put + "/" + nomer.getText() + "/" + "Схема", 0));

        if (soprigenii.contains(idi.getText())) {
            tableData.put("${interval}","");
            rashet_SOPR(nomer1.getText());
        } else {
            rashet(nomer1.getText(),dlina.getText());
            tableData.put("${interval}","0 ÷ "+ dlina.getText());
        }



        // Заменяем плейсхолдер на таблицу
        String outputFileName = OUTPUT_PATH + nomer.getText() + "_" + gorbox.getValue() + ".docx";
        File outputFile = new File(outputFileName);
        try {
            TemplateResource templateResource;
            // Получаем поток входных данных для ресурса
            //InputStream inputStream = getClass().getResourceAsStream(TEMPLATE_PATH);
            if (soprigenii.contains(idi.getText())) {
                templateResource = new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH2S), TEMPLATE_PATH2S);
            } else {
                templateResource = getDokHablon(nomer1.getText());
            }

            InputStream inputStream = templateResource.getInputStream();
            String templatePath = templateResource.getTemplatePath();
            System.err.println("Берем шаблон: " + templatePath);
            if (inputStream == null) {
                throw new FileNotFoundException("Ресурс не найден: " + templatePath);
            }
            // Создаем временный файл
            Path tempFile = Files.createTempFile("hablon_", ".docx");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

            try (FileInputStream template = new FileInputStream(tempFile.toFile());
                 XWPFDocument document = new XWPFDocument(template)) {
                replacePlaceholderWithTableSimpleFixed(document, "${table_placeholder}", get_WorTablr(nomer1.getText()));
                replaceTablePlaceholders(document, tableData);
                replacePlaceholders(document, params);


                try (FileOutputStream out = new FileOutputStream(outputFile)) {
                    document.write(out);
                }
                // Автоматическое открытие файла после сохранения
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (outputFile.exists()) {
                        desktop.open(outputFile);
                    } else {
                        System.err.println("Файл не найден: " + outputFile.getAbsolutePath());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Ошибка генерации документа: " + e.getMessage());
            }
            // Опционально: удаление временного файла после использования
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                    System.err.println("Не удалось удалить временный файл: " + tempFile);
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при открытии документа", e);
        }
        String prim = "Паспорт создан";
        new DatabaseHandler().DobavleniePRIM(prim, gorbox.getValue(), namebox.getValue());
        clearFields();
    }
    private void generateWordDocument_dop(String... params) throws IOException, InvalidFormatException, ParseException {

        Map<String, String> tableData = new HashMap<>();

        tableData.put("${name}", bdname.getText());
        tableData.put("${sehen}", cehen.getText());
        tableData.put("${ush}", ush.getText().replace("участок","").trim());
        tableData.put("${ush1}", ush.getText().replace("участок","").trim());
        tableData.put("${gorizont}", gorbox.getValue());
        tableData.put("${privazka}", privazka.getText());
        tableData.put("${kategorii}", katigoria1.getText());
        tableData.put("${opisanie}", opisanie1.getText());
        tableData.put("${interval}", interval.getText());
        tableData.put("${interval1}", interval.getText());
        tableData.put("${dlina}", dlina.getText());
        tableData.put("${plan}",   getPoto(Put + "/" + nomer.getText() + "/" + "План_"+interval.getText()+"_"+dop.getText(), 0));

        if (soprigenii.contains(idi.getText())) {
            tableData.put("${interval}","");
            rashet_SOPR(nomer11.getText());
        } else {
            double OT_Dobl = Double.parseDouble(this.OT);
            double DO_Dobl = Double.parseDouble(this.DO);
            String SUMOTDO = String.format(Locale.US, "%.1f",DO_Dobl-OT_Dobl );

            rashet(nomer11.getText(),SUMOTDO);
            System.out.print(SUMOTDO);
            tableData.put("${interval}",interval.getText());
        }

        // Заменяем плейсхолдер на таблицу
        String outputFileName = OUTPUT_PATH + nomer.getText() + "_" + gorbox.getValue() + ".docx";
        File outputFile = new File(outputFileName);
        try {
            TemplateResource templateResource;
            // Получаем поток входных данных для ресурса

            if (soprigenii.contains(idi.getText())) {
                templateResource = new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH5_1), TEMPLATE_PATH5_1);
            } else {
                templateResource = getDokHablon_DOP(nomer1.getText());
            }

            InputStream inputStream = templateResource.getInputStream();
            String templatePath = templateResource.getTemplatePath();
            System.err.println("Берем шаблон: " + templatePath);
            if (inputStream == null) {
                throw new FileNotFoundException("Ресурс не найден: " + templatePath);
            }
            // Создаем временный файл
            Path tempFile = Files.createTempFile("hablon_", ".docx");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

            try (FileInputStream template = new FileInputStream(tempFile.toFile());
                 XWPFDocument document = new XWPFDocument(template)) {
                replacePlaceholderWithTableSimpleFixed(document, "${table_placeholder}",  get_WorTablr(nomer11.getText()));
                replaceTablePlaceholders(document, tableData);
                replacePlaceholders(document, params);
                try (FileOutputStream out = new FileOutputStream(outputFile)) {
                    document.write(out);
                }
                // Автоматическое открытие файла после сохранения
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (outputFile.exists()) {
                        desktop.open(outputFile);
                    } else {
                        System.err.println("Файл не найден: " + outputFile.getAbsolutePath());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Ошибка генерации документа: " + e.getMessage());
            }
            // Опционально: удаление временного файла после использования
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                    System.err.println("Не удалось удалить временный файл: " + tempFile);
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при открытии документа", e);
        }
        String prim = "Дополнение создано";
        new DatabaseHandler().DobavleniePRIM(prim, gorbox.getValue(), namebox.getValue());
        clearFields();
    }
    private List<List<String>> get_WorTablr(String listPas){
        List<List<String>> tableData_1 = new ArrayList<>();
        // Заголовок таблицы
        if (soprigenii.contains(idi.getText())){
            tableData_1.add(Arrays.asList("№", "Наименование", "Параметры",  "Расход на выработку", "Примечание"));

            // Данные таблицы
            tableData_1.add(Arrays.asList("", "", "", "", "", ""));
            tableData_1.add(Arrays.asList("1", "Анкер СПА", "2,0 м, ø22 мм", this.ankerPM,"--- шт. в ряду"));
            tableData_1.add(Arrays.asList("2", "Шайба", "150х150 мм 4 мм", this.ankerPM, ""));
            tableData_1.add(Arrays.asList("3", "Гайка сферическая", "D22", this.ankerPM, ""));
            tableData_1.add(Arrays.asList("4.1", "Ампула полимерная ", "Ø28 мм, 300 мм", this.ampulaPM, "5 шт. на шпур"));
            tableData_1.add(Arrays.asList("4.2", "Ампула полимерная ", "Ø30 мм, 400 мм", this.ampulaPM2,"4 шт. на шпур"));
            tableData_1.add(Arrays.asList("5", "Сетка металлическая ", "ø4 мм 100х100 мм", this.setkaPM,"2200 х 1200 мм"));
            tableData_1.add(Arrays.asList("", "", "", "", "", ""));
            tableData_1.add(Arrays.asList("1", "Анкер ФА ", "2,0 м, ø46 мм", this.karkasPM, "--- шт. в ряду"));
            tableData_1.add(Arrays.asList("2", "Шайба ", "200х200 мм 4 мм",this.karkasPM, ""));
            tableData_1.add(Arrays.asList("3", "Армокаркас", "950х950 мм 10÷12 мм", this.karkasPM,""));
        }
        else {
            tableData_1.add(Arrays.asList("№", "Наименование", "Параметры", "Расход на 1,0 п.м.", "Расход на выработку", "Примечание"));

            // Данные таблицы
            tableData_1.add(Arrays.asList("", "", "", "", "", ""));
            tableData_1.add(Arrays.asList("1", "Анкер СПА", "2,0 м, ø22 мм", this.ankerPM, this.ankerR,"--- шт. в ряду"));
            tableData_1.add(Arrays.asList("2", "Шайба", "150х150 мм 4 мм", this.ankerPM, this.ankerR,""));
            tableData_1.add(Arrays.asList("3", "Гайка сферическая", "D22", this.ankerPM, this.ankerR,""));
            tableData_1.add(Arrays.asList("4.1", "Ампула полимерная ", "Ø28 мм, 300 мм", this.ampulaPM, this.ampulaR,"5 шт. на шпур"));
            tableData_1.add(Arrays.asList("4.2", "Ампула полимерная ", "Ø30 мм, 400 мм", this.ampulaPM2, this.ampulaR2,"4 шт. на шпур"));
            tableData_1.add(Arrays.asList("5", "Сетка металлическая ", "ø4 мм 100х100 мм", this.setkaPM, this.setkaR,"2200 х 1200 мм"));

            if ( ov2.contains(listPas)) {
                tableData_1.add(Arrays.asList("", "", "", "", "", ""));
                tableData_1.add(Arrays.asList("1", "Анкер ФА ", "2,0 м, ø46 мм", this.karkasPM, this.karkasR,"--- шт. в ряду"));
                tableData_1.add(Arrays.asList("2", "Шайба ", "200х200 мм 4 мм",this.karkasPM, this.karkasR,""));
                tableData_1.add(Arrays.asList("3", "Армокаркас", "950х950 мм 10÷12 мм", this.karkasPM, this.karkasR,""));
            }}
        return tableData_1;
    }
    private void replaceTablePlaceholders(XWPFDocument doc, Map<String, String> tableData) {
        // Перебираем все таблицы в документе
        for (XWPFTable table : doc.getTables()) {
            // Перебираем все строки в таблице
            for (XWPFTableRow row : table.getRows()) {
                // Перебираем все ячейки в строке
                for (XWPFTableCell cell : row.getTableCells()) {
                    // Перебираем все абзацы в ячейке
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        // Перебираем все плейсхолдеры
                        for (Map.Entry<String, String> entry : tableData.entrySet()) {
                            String placeholder = entry.getKey();
                            String value = entry.getValue();

                            // Если абзац содержит плейсхолдер - заменяем его
                            if (paragraph.getText().contains(placeholder)) {
                                if (placeholder.equals("${plan}") || placeholder.equals("${poper}")){
                                    replaceImageInParagraph(paragraph, placeholder, value, 470, 330, false);}
                                else if (placeholder.equals("${sxema}")){
                                    replaceImageInParagraph(paragraph, placeholder, value, 700, 368, false);}
                                else{ replaceTextInParagraph(paragraph, placeholder, value);}

                            }
                        }
                    }
                }
            }
        }
    }
    private void replacePlaceholderWithTableSimpleFixed(XWPFDocument doc, String placeholder, List<List<String>> tableData) {
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            String text = paragraph.getText();

            if (text != null && text.contains(placeholder)) {
                // Эмулируем "Backspace" - удаляем placeholder из параграфа
                removePlaceholderFromParagraph(paragraph, placeholder);

                // Создаем таблицу в позиции, где был placeholder
                XmlCursor cursor = paragraph.getCTP().newCursor();
                XWPFTable table = doc.insertNewTbl(cursor);
                table.setWidth("100%");

                // Установить границы для таблицы
                table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "000000");
                table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "000000");
                table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "000000");
                table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "000000");
                table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "000000");
                table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 4, 0, "000000");

                // Заполняем таблицу без лишних пробелов
                for (int rowIdx = 0; rowIdx < tableData.size(); rowIdx++) {
                    List<String> rowData = tableData.get(rowIdx);
                    XWPFTableRow row = (rowIdx == 0) ? table.getRow(0) : table.createRow();

                    // Убеждаемся, что в строке достаточно ячеек
                    while (row.getTableCells().size() < rowData.size()) {
                        row.addNewTableCell();
                    }

                    for (int colIdx = 0; colIdx < rowData.size(); colIdx++) {
                        XWPFTableCell cell = row.getCell(colIdx);
                        if (cell != null) {
                            // Полностью очищаем ячейку (как Backspace)
                            clearCellCompletely(cell);

                            // Создаем чистый параграф
                            XWPFParagraph para = cell.addParagraph();

                            // Убираем все отступы и интервалы
                            para.setAlignment(ParagraphAlignment.LEFT);
                            para.setIndentationLeft(0);
                            para.setIndentationRight(0);
                            para.setIndentationFirstLine(0);
                            para.setSpacingBetween(1);

                            // Создаем run и устанавливаем текст
                            XWPFRun run = para.createRun();
                            String cellText = rowData.get(colIdx);

                            // Убираем все пробелы по краям
                            if (cellText != null) {
                                cellText = cellText.trim();
                            }
                            run.setText(cellText == null ? "" : cellText);

                            // Убираем пробелы в самом run
                            run.setFontFamily("Times New Roman ");
                            run.setFontSize(11);

                            // Жирный для заголовка
                            if (rowIdx == 0) {
                                run.setBold(true);
                            }
                            if (rowIdx == 1) {
                                mergeCellsHorizontal(cell.getTableRow(),0,5,"При применении СПА:");
                            }
                            if (rowIdx == 8) {
                                mergeCellsHorizontal(cell.getTableRow(),0,5,"При применении ФА:");
                            }

                        }
                    }
                }
                cursor.dispose();
                if (paragraph.getText() != null && paragraph.getText().trim().isEmpty()) {
                    int pos = doc.getPosOfParagraph(paragraph);
                    if (pos >= 0) {
                        doc.removeBodyElement(pos);
                    }
                }
                break;
            }
        }
    }

    private void clearCellCompletely(XWPFTableCell cell) {
        // Удаляем все параграфы
        for (int i = cell.getParagraphs().size() - 1; i >= 0; i--) {
            cell.removeParagraph(i);
        }

        // Также можно очистить свойства ячейки
        cell.getCTTc().setTcPr(null);
    }
    private void removePlaceholderFromParagraph(XWPFParagraph paragraph, String placeholder) {
        String text = paragraph.getText();
        if (text != null && text.contains(placeholder)) {
            // Получаем все runs в параграфе
            List<XWPFRun> runs = paragraph.getRuns();

            // Собираем текст из всех runs
            StringBuilder fullText = new StringBuilder();
            for (XWPFRun run : runs) {
                if (run.getText(0) != null) {
                    fullText.append(run.getText(0));
                }
            }

            // Если placeholder найден в собранном тексте
            int placeholderIndex = fullText.indexOf(placeholder);
            if (placeholderIndex >= 0) {
                // Удаляем все runs
                for (int i = runs.size() - 1; i >= 0; i--) {
                    paragraph.removeRun(i);
                }

                // Создаем новый run с оставшимся текстом (без placeholder)
                String remainingText = fullText.toString().replace(placeholder, "");
                if (!remainingText.trim().isEmpty()) {
                    XWPFRun newRun = paragraph.createRun();
                    newRun.setText(remainingText);
                }
            }
        }
    }

    private void mergeCellsHorizontal(XWPFTableRow row, int startCol, int endCol, String mergedText) {
        if (startCol < 0 || endCol >= row.getTableCells().size() || startCol > endCol) {
            return;
        }

        // Очищаем все ячейки в диапазоне
        for (int i = startCol; i <= endCol; i++) {
            XWPFTableCell cell = row.getCell(i);
            clearCellCompletely(cell);
        }

        // Настраиваем объединение для первой ячейки
        XWPFTableCell firstCell = row.getCell(startCol);
        setCellMerge(firstCell, true, startCol == endCol ? 0 : endCol - startCol);

        // Заполняем текст в объединенной ячейке
        XWPFParagraph para = firstCell.addParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = para.createRun();
        run.setText(mergedText == null ? "" : mergedText.trim());
        run.setItalic(true);

        // Настраиваем остальные ячейки как объединенные
        for (int i = startCol + 1; i <= endCol; i++) {
            XWPFTableCell cell = row.getCell(i);
            setCellMerge(cell, false, 0);
        }
    }

    // Метод для установки объединения ячейки
    private void setCellMerge(XWPFTableCell cell, boolean isFirst, int span) {
        CTTc cttc = cell.getCTTc();
        CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();

        if (isFirst && span > 0) {
            // Устанавливаем горизонтальный спан (объединение)
            CTHMerge hMerge = tcPr.addNewHMerge();
            hMerge.setVal(STMerge.RESTART);

            // Также можно установить gridSpan
            CTDecimalNumber gridSpan = CTDecimalNumber.Factory.newInstance();
            gridSpan.setVal(BigInteger.valueOf(span + 1));
            tcPr.setGridSpan(gridSpan);
        } else if (!isFirst) {
            // Продолжение объединения
            CTHMerge hMerge = tcPr.addNewHMerge();
            hMerge.setVal(STMerge.CONTINUE);
        }
    }

    private void replacePlaceholders(XWPFDocument doc, String[] values) {
        // Списки плейсхолдеров для разных типов замен
        Set<String> textPlaceholders = Set.of("${nomer}","${dnomer}");
        Set<String> VidhablonPlaceholders = Set.of("${obvid}","${obvid2}","${sxematexfakt}", "${konstrk}");

        // Обработка текстовых плейсхолдеров
        textPlaceholders.forEach(placeholder -> {
            String fieldName = PLACEHOLDER_MAP.get(placeholder);
            for (XWPFParagraph p : doc.getParagraphs()) {
                if (p.getText().contains(placeholder)) {
                    String value = getValueByFieldName(fieldName, values);
                    System.out.println("[ТЕКСТ] Замена " + placeholder + " → " + value);
                    replaceTextInParagraph(p, placeholder, value);
                }
            }
        });
        // Обработка плейсхолдеров изображений

        VidhablonPlaceholders.forEach(placeholder -> {
            int H_IMG;
            String fieldName = PLACEHOLDER_MAP.get(placeholder);
            for (XWPFParagraph p : doc.getParagraphs()) {
                if (p.getText().contains(placeholder)) {
                    String imagePath = getValueByFieldName(fieldName, values);
                    System.out.println("[Vid] Замена " + placeholder + " → " + imagePath);
                    if (placeholder.equals("${sxematexfakt}")){
                        replaceImageInParagraph(p, placeholder, imagePath, 470, 600, true);
                    }
                    else if (placeholder.equals("${konstrk}")){

                        if (soprigenii.contains(idi.getText()) || ov2.contains(nomer1.getText())) {
                            H_IMG=700;
                        } else{H_IMG=420;}
                        replaceImageInParagraph(p, placeholder, imagePath, 470, H_IMG, true); }
                    else {
                        replaceImageInParagraph(p, placeholder, imagePath, 700, 420, true);
                    }

                }
            }
        });

    }

    private String getValueByFieldName(String name, String[] values) {
        return switch (name) { //зависимости от ключа возвращает значение из массива который вводим
            case "nomer" -> values[0];
            case "sxematexfakt" -> values[1];
            case "obvid" -> values[2];
            case "konstrk" -> values[3];
            case "obvid2" -> values[4];
            case "dnomer" -> values[5];
            default -> "";
        };
    }

    private void replaceTextInParagraph(XWPFParagraph p, String placeholder, String replacement) {
        String text = p.getText(); // получаем весь текст параметра
        p.getRuns().forEach(r -> r.setText("", 0)); //удаляем существующий из всех ронов и заменяем полностью
        XWPFRun newRun = p.createRun(); // создаем новый для нового текста
        String safeReplacement = replacement != null ? replacement : "";

        newRun.setText(text.replace(placeholder, safeReplacement));
        newRun.setFontFamily("Times New Roman ");
       if (placeholder.equals("${ush}") || placeholder.equals("${kategorii}") ||placeholder.equals("${opisanie}") || placeholder.equals("${interval}") ){
           newRun.setFontSize(12);
       }else {newRun.setFontSize(14);}
       // заменяем "${nomer}", "123",
    }

    private void replaceImageInParagraph(XWPFParagraph p, String placeholder, String imagePath, int width, int height, boolean isResource) {
        replaceTextInParagraph(p, placeholder, "");

        if (imagePath == null || imagePath.isEmpty()) {
            System.err.println("Путь к изображению пуст");
            return;
        }
        try (InputStream is = isResource
                ? getClass().getResourceAsStream(imagePath.startsWith("/") ? imagePath : "/" + imagePath)
                : new FileInputStream(imagePath)) {
            if (is == null) {
                System.err.println("Источник не найден: " + imagePath);
                return;
            }
            byte[] imageBytes = IOUtils.toByteArray(is);
            PictureData.PictureType type = isResource
                    ? determineImageType(imageBytes)
                    : getImageType(imagePath);

            XWPFRun run = p.createRun();
            run.addPicture(
                    new ByteArrayInputStream(imageBytes),
                    type.ordinal(),
                    "image",
                    Units.toEMU(width),
                    Units.toEMU(height)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private PictureData.PictureType determineImageType(byte[] imageData) {
        if (imageData.length >= 4 &&
                imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8) {
            return PictureData.PictureType.JPEG;
        } else if (imageData.length >= 8 &&
                new String(imageData, 0, 8).equals("\211PNG\r\n\032\n")) {
            return PictureData.PictureType.PNG;
        }
        return PictureData.PictureType.JPEG; // fallback
    }
    // Определение типа изображения
    private PictureData.PictureType getImageType(String imagePath) {
        String ext = imagePath.substring(imagePath.lastIndexOf(".") + 1).toLowerCase();
        return switch (ext) {
            case "jpg", "jpeg" -> PictureData.PictureType.JPEG;
            case "png" -> PictureData.PictureType.PNG;
            case "gif" -> PictureData.PictureType.GIF;
            case "emf" -> PictureData.PictureType.EMF;
            case "wmf" -> PictureData.PictureType.WMF;
            default -> throw new IllegalArgumentException("Unsupported image type: " + ext);
        };
    }
    // Сформируйте полный путь к ресурсу
    public String getRESURS(String resourcePath, String fileName) {
        // Возвращаем путь в формате "/папка/файл"
        if (fileName == null) return "";
        return (resourcePath.startsWith("/") ? "" : "/") +
                resourcePath.replace("\\", "/") +
                "/" + fileName;
    }
    public String getUstanovka(String list) {

        return switch (list) {
            case "1","2",  "4", "5",  "7","8","10","11"->
                    getRESURS(HABLON_PATH_USTANOVKA, "1.jpg");

            case "13", "15", "19","21"-> getRESURS(HABLON_PATH_USTANOVKA, "2.jpg");
            case "17","23" -> getRESURS(HABLON_PATH_USTANOVKA, "3.jpg");
            case "25", "27","33" -> getRESURS(HABLON_PATH_USTANOVKA, "4.jpg");
            case "29", "35" -> getRESURS(HABLON_PATH_USTANOVKA, "5.jpg");

            case "37","38","40","41" -> getRESURS(HABLON_PATH_USTANOVKA, "6.jpg");
            case "39","42" -> getRESURS(HABLON_PATH_USTANOVKA, "7.jpg");
            case "3",  "6", "9","12" -> getRESURS(HABLON_PATH_USTANOVKA, "9.jpg");
            default ->  null;
        };
    }
    public String getUstanovka_SOPR(String list) {
        return switch (list) {

            case "5", "11",  "17", "23", "29","35" ->
                    getRESURS(HABLON_PATH_USTANOVKA, "8.jpg");

            default ->  getRESURS(HABLON_PATH_USTANOVKA, "6.jpg");
        };
    }
    public String getIlement(String list) {

        return switch (list) {

            case "1","2", "3", "4", "5", "6", "7","8","9","10","11","12" -> getRESURS(HABLON_PATH_ILIMENT, "1.jpg");

            case "13", "15","17", "19","21","23" -> getRESURS(HABLON_PATH_ILIMENT, "2.jpg");
            case "25", "27","29","33","35"  -> getRESURS(HABLON_PATH_ILIMENT, "3.jpg");
            case "37","38","39", "40","41","42"  -> getRESURS(HABLON_PATH_ILIMENT, "4.jpg");
            default -> null;
        };
    }

    public TemplateResource getDokHablon(String list) {
        return switch (list) {
            case "1","2", "3", "4", "5", "6", "7","8","9","10","11","12"
                    -> new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH1), TEMPLATE_PATH1);
            case "13", "15","17", "19","21","23","25", "27","29","33","35"  ->
                    new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH2), TEMPLATE_PATH2);
            case "31","32" -> new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH3), TEMPLATE_PATH3);
            case "37","38","39","40","41","42" -> new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH4), TEMPLATE_PATH4);

            default -> null;
        };
    }
    public TemplateResource getDokHablon_DOP(String list) {
        return switch (list) {
            case "1","2", "3", "4", "5", "6", "7","8","9","10","11","12"
                    -> new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH1_1), TEMPLATE_PATH1_1);
            case "13", "15","17", "19","21","23","25", "27","29","33","35"  ->
                    new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH2_1), TEMPLATE_PATH2_1);
            case "31","32" -> new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH3_1), TEMPLATE_PATH3_1);
            case "37","38","39","40","41","42" ->
                    new TemplateResource(getClass().getResourceAsStream(TEMPLATE_PATH4_1), TEMPLATE_PATH4_1);

            default -> null;
        };
    }
    public  String getPoto(String imagePath, int i) {
        if (imagePath == null || imagePath.isEmpty()) return "";
        try {
            File folder = new File(imagePath);

            // Проверяем, существует ли папка
            if (!folder.exists() || !folder.isDirectory()) {
                System.err.println("Папка не найдена: " + imagePath);
                return "";
            }

            // Получаем список файлов в папке
            File[] files = folder.listFiles((dir, name) -> {
                // Фильтруем файлы по расширению (изображения)
                String lowerCaseName = name.toLowerCase();
                return lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".png") ||
                        lowerCaseName.endsWith(".jpeg") || lowerCaseName.endsWith(".gif");
            });

            // Проверяем, есть ли изображения в папке
            if (files == null || files.length == 0) {
                System.err.println("Нет изображений в: " + imagePath);
                return "";
            }

            return files[i].getAbsolutePath();
        } catch (SecurityException e) {
            showAlert( "Нет доступа к папке: " + imagePath);
            return "";
        }
    }
   private void setupImageHandlers() {
        openImageHandler(PlanVKL, "План",PlanVKLNe,nomer.getText());


         openImageHandler(sxemaVKL, "Схема", sxemaVKLNe,nomer.getText());
        obnovaImageHandler(sxemaobnov, "Схема", nomer.getText());
        sozdaniiImageHandler(sxemaVNS, "Схема", sxemaVKL, sxemaVKLNe, sxemaVNS, sxemaobnov, sxemaobnov,nomer.getText());

       openImageHandler(PoperVKL, "Разрез",PoperVKLNe,nomer.getText());
       obnovaImageHandler(poperobnov, "Разрез",nomer.getText());
       sozdaniiImageHandler(poperVNS, "Разрез",PoperVKL,PoperVKLNe,poperVNS,poperobnov,poperobnov,nomer.getText());



    }
    private void handleError(Exception e) {
        e.printStackTrace();
        showAlert( "Произошла ошибка: " + e.getMessage());
    }
     void proverkaImageGeolg(String imagePath,ImageView VKL,ImageView VKLNE) {
        File folder = new File(imagePath);
        // Проверяем, существует ли папка
        if (!folder.exists() || !folder.isDirectory()) {
            // System.err.println("Папка не найдена: " + imagePath);

            VKL.setVisible(false);VKLNE.setVisible(true);
            singUpButtun.setVisible(false);

            return;
        }
        // Получаем список файлов в папке
        File[] files = folder.listFiles((dir, name) -> {
            // Фильтруем файлы по расширению (изображения)
            String lowerCaseName = name.toLowerCase();
            return lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".png") ||
                    lowerCaseName.endsWith(".jpeg") || lowerCaseName.endsWith(".gif");
        });
        // Проверяем, есть ли изображения в папке
        if (files != null && files.length > 0) {
            VKL.setVisible(true);VKLNE.setVisible(false);

            singUpButtun.setVisible(true);

        } else {
            System.err.println("В папке нет изображений.");
            singUpButtun.setVisible(false);

            VKL.setVisible(false);VKLNE.setVisible(true);
        }
    }


    private void setupTable() {
        // 1. Делаем таблицу редактируемой
        dataTable.setEditable(false);
        // 2. Настраиваем, как данные извлекаются для каждого столбца
        setupCellValueFactories();
        // 3. Настраиваем редактирование ячеек
        setupCellEditing();

        dataTable.setOnMouseClicked(mouseEvent -> {

            int selectedIndex = dataTable.getSelectionModel().getSelectedIndex();

            if (selectedIndex >= 0) {
                ObservableList<StringProperty> row = dataTable.getItems().get(selectedIndex);
                 this.OT=(row.get(1).get()).trim().replace(',', '.');;
                this.DO=(row.get(2).get()).trim().replace(',', '.');;
                katigoria1.setText(row.get(3).get());
                opisanie1.setText(row.get(4).get());
                interval.setText(row.get(5).get());
                nomer11.setText(row.get(6).get());
                dop.setText(row.get(0).get());
                singUpButtun2.setVisible(true);
                DOP_MENU.setVisible(true);
                proverkaImage(Put + "/" + nomer.getText() + "/" + "План_"+interval.getText()+"_"+dop.getText(), planVNS1, planVNSNE1, PlanVKL1, PlanVKLNe1, planobnov1);
                openImageHandler(PlanVKL1, "План_"+interval.getText()+"_"+dop.getText(),PlanVKLNe1,nomer.getText());
                obnovaImageHandler(planobnov1, "План_"+interval.getText()+"_"+dop.getText(),nomer.getText());
                sozdaniiImageHandler(planVNS1, "План_"+interval.getText()+"_"+dop.getText(),PlanVKL1,PlanVKLNe1,planVNS1,planobnov1,planobnov1,nomer.getText());
                // Сохраняем индекс для последующего обновления
               // editingRowIndex = selectedIndex;

            }
        });
    }
    private void setupCellValueFactories() {
        // Столбец 1: берет значение из первой ячейки строки
        column1.setCellValueFactory(param -> {
            // param.getValue() - получаем всю строку (ObservableList<StringProperty>)
            ObservableList<StringProperty> row = param.getValue();
            // Если в строке есть хотя бы 1 элемент, возвращаем его значение
            // Если нет - возвращаем пустую строку
            if (row.size() > 0) {
                return row.get(0);  // StringProperty первой ячейки
            } else {
                return new SimpleStringProperty("");  // Пустая строка
            }
        });

        // Аналогично для остальных столбцов
        column2.setCellValueFactory(param -> {
            ObservableList<StringProperty> row = param.getValue();
            return row.size() > 1 ? row.get(1) : new SimpleStringProperty("");
        });
        column3.setCellValueFactory(param -> {
            ObservableList<StringProperty> row = param.getValue();
            return row.size() > 2 ? row.get(2) : new SimpleStringProperty("");
        });
        column4.setCellValueFactory(param -> {
            ObservableList<StringProperty> row = param.getValue();
            return row.size() > 3 ? row.get(3) : new SimpleStringProperty("");
        });
        column5.setCellValueFactory(param -> {
            ObservableList<StringProperty> row = param.getValue();
            return row.size() > 4 ? row.get(4) : new SimpleStringProperty("");
        });
        column6.setCellValueFactory(param -> {
            ObservableList<StringProperty> row = param.getValue();
            return row.size() > 5 ? row.get(5) : new SimpleStringProperty("");
        });
        column7.setCellValueFactory(param -> {
            ObservableList<StringProperty> row = param.getValue();
            return row.size() > 6 ? row.get(6) : new SimpleStringProperty("");
        });
        column8.setCellValueFactory(param -> {
            ObservableList<StringProperty> row = param.getValue();
            return row.size() > 7 ? row.get(7) : new SimpleStringProperty("");
        });
    }

    private void setupCellEditing() {
        // Для каждого столбца настраиваем возможность редактирования
        setupColumnForEditing(column1, 0);  // 0 - индекс столбца
        setupColumnForEditing(column2, 1);  // 1 - индекс столбца
        setupColumnForEditing(column3, 2);  // 2 - индекс столбца
        setupColumnForEditing(column4, 3);  // 3 - индекс столбца
        setupColumnForEditing(column5, 4);  // 3 - индекс столбца
        setupColumnForEditing(column6, 5);  // 3 - индекс столбца
        setupColumnForEditing(column7, 6);  // 3 - индекс столбца
        setupColumnForEditing(column8, 7);  // 3 - индекс столбца
    }

}
