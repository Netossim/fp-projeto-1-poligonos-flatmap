package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class PoligonosApp extends Application {
    private final List<List<Point>> pontosPoligonos = List.of(
        // Quadrilátero (Quadrado)
        List.of(
            new Point( 50,  50),
            new Point(150,  50),
            new Point(150, 150),
            new Point( 50, 150)
        ),

        // Quadrilátero (Retângulo)
        List.of(
            new Point(200,  50),
            new Point(400,  50),
            new Point(400, 100),
            new Point(200, 100)
        ),

        // Triângulo
        List.of(
            new Point(300, 250),
            new Point(350, 150),
            new Point(400, 250)
        ),

        // Pentágono
        List.of(
            new Point(200, 250),
            new Point(250, 300),
            new Point(250, 350),
            new Point(150, 350),
            new Point(150, 300)
        ),

        // Hexágono
        List.of(
            new Point(320, 270),
            new Point(370, 320),
            new Point(370, 370),
            new Point(320, 420),
            new Point(270, 370),
            new Point(270, 320)
        )
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage mainStage) {
        final var root = new Pane();
        final var scene = new Scene(root, 800, 600);

        for (final var listaPontos : pontosPoligonos) {
            final var poligono = new Polygon();
            for (final Point point : listaPontos) {
                poligono.getPoints().addAll(point.x(), point.y());
            }

            poligono.setFill(Color.BLUE);
            poligono.setStroke(Color.BLACK);
            root.getChildren().add(poligono);
        }

        final List<String> perimetros = perimetros().stream().map(p -> String.format("%.1f", p)).toList();
        final var label1 = newLabel("Perímetro dos Polígonos: " + perimetros, 500);
        final var label2 = newLabel("Tipo dos Polígonos: " + tipoPoligonos(), 530);
        root.getChildren().addAll(label1, label2);

        mainStage.setTitle("Polígonos");
        mainStage.setScene(scene);
        mainStage.setAlwaysOnTop(true);
        mainStage.show();
    }

    private static Label newLabel(final String title, final int y) {
        final var label = new Label(title);
        label.setLayoutX(10);
        label.setLayoutY(y);
        return label;
    }

    // Adicione o código dos métodos aqui:

    protected List<Double> perimetros() {
        return pontosPoligonos.stream()
            .map(pontos -> {
                Point firstPoint = pontos.get(0);
                return pontos.stream()
                    .reduce(firstPoint, Point::new)
                    .distance();
            })
            .toList();
    }

    protected List<String> tipoPoligonos() {
        return pontosPoligonos.stream()
            .map(pontos -> {
                int numPontos = pontos.size();
                return switch (numPontos) {
                    case 3 -> "Triângulo";
                    case 4 -> "Quadrilátero";
                    case 5 -> "Pentágono";
                    case 6 -> "Hexágono";
                    default -> "Polígono";
                };
            })
            .toList();
    }
}
