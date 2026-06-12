package fr.univ_amu.iut.exercice8;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Exercice 8 (capstone) - Convertisseur de températures.
 *
 * <p>Cet exercice synthétise tous les types de bindings vus dans le TP :
 *
 * <ul>
 *   <li>Binding unidirectionnel (Labels de lecture)
 *   <li>Binding bidirectionnel (TextField ↔ Slider via {@link NumberStringConverter})
 *   <li>{@code ChangeListener} pour la conversion avec formule (C = (F-32)*5/9)
 *   <li>Sliders verticaux ({@code Orientation.VERTICAL})
 * </ul>
 *
 * <p>L'application affiche deux panneaux côte à côte : un pour Celsius, un pour Fahrenheit.
 * Modifier l'un met à jour l'autre automatiquement.
 */
public class ConvertisseurTemperatures extends Application {

  private boolean updating = false;

  @Override
  public void start(Stage primaryStage) {
    // TODO exercice 8 : construire le convertisseur de températures.
    //
    // 1. Créer le panneau Celsius (VBox) :
    //    - Label "°C" (style bold, 16px)
    //    - Slider vertical [0, 100], valeur initiale 0, id "slider-celsius"
    //    - TextField, id "tf-celsius", maxWidth 50
    //
    // 2. Créer le panneau Fahrenheit (VBox) :
    //    - Label "°F" (style bold, 16px)
    //    - Slider vertical [0, 212], valeur initiale 32, id "slider-fahrenheit"
    //    - TextField, id "tf-fahrenheit", maxWidth 50
    //
    // 3. Ajouter un ChangeListener sur le slider Celsius :
    //    fahrenheit = celsius * 9/5 + 32
    //    (utiliser un flag "updating" pour éviter les boucles infinies)
    //
    // 4. Ajouter un ChangeListener sur le slider Fahrenheit :
    //    celsius = (fahrenheit - 32) * 5/9
    //
    // 5. Lier chaque TextField à son slider via
    //    Bindings.bindBidirectional(tf.textProperty(), slider.valueProperty(),
    //        new NumberStringConverter())
    //
    // 6. Composer les panneaux dans un HBox, créer la Scene, afficher.
    Label labelC = new Label("Celsius (°C)");
    labelC.setMaxWidth(Double.MAX_VALUE);
    labelC.setStyle(
        "-fx-background-color: #4a90d9; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-alignment: center; -fx-padding: 10;");
    Slider sliderC = new Slider(0, 100, 0);
    sliderC.setId("slider-celsius");
    sliderC.setOrientation(Orientation.VERTICAL);
    sliderC.setShowTickMarks(true);
    sliderC.setShowTickLabels(true);
    sliderC.setMajorTickUnit(10);
    TextField tfC = new TextField();
    tfC.setId("tf-celsius");
    tfC.setMaxWidth(50);
    tfC.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
    Label labelF = new Label("Fahrenheit (°F)");
    labelF.setMaxWidth(Double.MAX_VALUE);
    labelF.setStyle(
        "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-alignment: center; -fx-padding: 10;");
    Slider sliderF = new Slider(32, 212, 32);
    sliderF.setId("slider-fahrenheit");
    sliderF.setOrientation(Orientation.VERTICAL);
    sliderF.setShowTickMarks(true);
    sliderF.setShowTickLabels(true);
    sliderF.setMajorTickUnit(20);
    TextField tfF = new TextField();
    tfF.setId("tf-fahrenheit");
    tfF.setMaxWidth(50);
    tfF.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
    Bindings.bindBidirectional(
        tfC.textProperty(), sliderC.valueProperty(), new NumberStringConverter());
    Bindings.bindBidirectional(
        tfF.textProperty(), sliderF.valueProperty(), new NumberStringConverter());
    sliderC
        .valueProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (!updating) {
                updating = true;
                sliderF.setValue(newVal.doubleValue() * 9.0 / 5.0 + 32);
                updating = false;
              }
            });
    sliderF
        .valueProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (!updating) {
                updating = true;
                sliderC.setValue((newVal.doubleValue() - 32) * 5.0 / 9.0);
                updating = false;
              }
            });
    VBox vboxC = new VBox(15, labelC, sliderC, tfC);
    vboxC.setAlignment(Pos.TOP_CENTER);
    vboxC.setPrefWidth(170);
    vboxC.setStyle(
        "-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-background-color: #f8f9fa;");
    vboxC.setPadding(new Insets(0, 0, 15, 0)); // Padding en bas pour décoller le TextField
    VBox vboxF = new VBox(15, labelF, sliderF, tfF);
    vboxF.setAlignment(Pos.TOP_CENTER);
    vboxF.setPrefWidth(170);
    vboxF.setStyle(
        "-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-background-color: #f8f9fa;");
    vboxF.setPadding(new Insets(0, 0, 15, 0));
    HBox root = new HBox(20, vboxC, vboxF);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(20));
    Scene scene = new Scene(root, 400, 400);
    primaryStage.setTitle("Convertisseur de Températures");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
