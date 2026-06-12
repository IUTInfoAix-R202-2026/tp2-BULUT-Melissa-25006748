package fr.univ_amu.iut.exercice7;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Exercice 7 - Cercle interactif avec binding bidirectionnel.
 *
 * <p>Trois contrôles sont synchronisés en permanence :
 *
 * <ul>
 *   <li>Un {@link Circle} dont le rayon change visuellement
 *   <li>Un {@link Slider} qui contrôle le rayon
 *   <li>Un {@link TextField} qui affiche et permet de saisir le rayon
 * </ul>
 *
 * <p>Modifier l'un des trois met à jour les deux autres automatiquement grâce à {@code
 * bindBidirectional()}.
 *
 * <p>Concepts :
 *
 * <ul>
 *   <li>{@code bindBidirectional()} entre Slider et Circle
 *   <li>{@code Bindings.bindBidirectional()} avec {@link NumberStringConverter}
 *   <li>{@link TextFormatter} avec filtre de validation
 *   <li>Binding unidirectionnel pour le centrage du cercle
 * </ul>
 */
public class CercleInteractif extends Application {

  private final Circle cercle = new Circle();
  private final Slider slider = new Slider();
  private final TextField textField = new TextField();
  private final Pane panneauCercle = new Pane();
  private final BorderPane root = new BorderPane();

  @Override
  public void start(Stage primaryStage) {
    // TODO exercice 7 : assembler l'interface et créer les bindings.
    //
    // 1. Appeler ajouterPanneau() pour configurer le panneau avec le cercle.
    // 2. Appeler ajouterSlider() pour configurer le slider en haut.
    // 3. Appeler ajouterTextField() pour ajouter le champ de saisie en bas.
    // 4. Appeler creerBindings() pour lier les trois contrôles.
    // 5. Créer la Scene, l'attacher au Stage, afficher.
    ajouterPanneau();
    ajouterSlider();
    ajouterTextField();
    creerBindings();
    HBox bottomBox = new HBox(15); // Espacement de 15px entre le slider et le texte
    bottomBox.setPadding(new Insets(10));
    HBox.setHgrow(slider, Priority.ALWAYS);
    bottomBox.getChildren().addAll(slider, textField);
    root.setBottom(bottomBox);
    Scene scene = new Scene(root, 500, 550);
    primaryStage.setTitle("Cercle Interactif");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  void ajouterPanneau() {
    // TODO exercice 7 : ajouter le panneau avec le cercle.
    //
    // panneauCercle.getChildren().add(cercle)
    // panneauCercle.setPrefSize(500, 500)
    // panneauCercle.setId("panneau")
    // cercle.setId("cercle")
    // root.setCenter(panneauCercle)
    cercle.setId("cercle");
    cercle.setFill(Color.web("#cfe8fb"));
    cercle.setStroke(Color.web("#2980b9"));
    panneauCercle.setId("panneau");
    panneauCercle.setPrefSize(500, 500);
    panneauCercle.getChildren().add(cercle);
    root.setCenter(panneauCercle);
  }

  void ajouterSlider() {
    // TODO exercice 7 : configurer le slider [0, 250] et le placer en haut.
    //
    // slider.setMin(0), slider.setMax(250)
    // slider.setId("slider")
    // root.setTop(slider)
    slider.setId("slider");
    slider.setMin(0);
    slider.setMax(250);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(250);
    slider.setMinorTickCount(0);
    slider.setShowTickMarks(false);
  }

  void ajouterTextField() {
    // TODO exercice 7 : ajouter le TextField avec un TextFormatter en bas.
    //
    // Le TextFormatter filtre la saisie pour n'accepter que des nombres.
    // Ce code est fourni - ne pas modifier.
    textField.setId("rayon");
    textField.setMaxWidth(70);
  }

  void creerBindings() {
    // TODO exercice 7 : créer les bindings bidirectionnels.
    //
    // 1. Centrer le cercle dans le panneau (unidirectionnel) :
    //    cercle.centerXProperty().bind(panneauCercle.widthProperty().divide(2))
    //    cercle.centerYProperty().bind(panneauCercle.heightProperty().divide(2))
    //
    // 2. Lier le rayon du cercle au slider (bidirectionnel) :
    //    cercle.radiusProperty().bindBidirectional(slider.valueProperty())
    //
    // 3. Lier le slider au TextField via NumberStringConverter :
    //    Bindings.bindBidirectional(textField.textProperty(),
    //        slider.valueProperty(), new NumberStringConverter())
    //
    // 4. Initialiser le rayon à 150 :
    //    slider.setValue(150)
    cercle.centerXProperty().bind(panneauCercle.widthProperty().divide(2));
    cercle.centerYProperty().bind(panneauCercle.heightProperty().divide(2));
    cercle.setRadius(150);
    slider.valueProperty().bindBidirectional(cercle.radiusProperty());
    TextFormatter<Number> formatter = new TextFormatter<>(new NumberStringConverter());
    textField.setTextFormatter(formatter);
    Bindings.bindBidirectional(
        textField.textProperty(), cercle.radiusProperty(), new NumberStringConverter());
  }

  public static void main(String[] args) {
    launch(args);
  }
}
