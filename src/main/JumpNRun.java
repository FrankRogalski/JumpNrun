package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JumpNRun extends Application {
	private Canvas can;
	private GraphicsContext gc;

	private Timeline tl_draw;
	
	private Spielfeld feld;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		tl_draw = new Timeline(new KeyFrame(Duration.millis(1000 / 60), e -> {
			draw();
		}));
		tl_draw.setCycleCount(Timeline.INDEFINITE);
		tl_draw.play();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		Scene scene = new Scene(root, 800, 800);

		stage.setTitle("Sample Text");

		can = new Canvas(scene.getWidth(), scene.getHeight());
		gc = can.getGraphicsContext2D();

		root.getChildren().add(can);
		// root.setStyle("-fx-background-color: #000000");

		scene.widthProperty().addListener((obsv, oldVal, newVal) -> {
			can.setWidth(newVal.doubleValue());
		});

		scene.heightProperty().addListener((obsv, oldVal, newVal) -> {
			can.setHeight(newVal.doubleValue());
		});

		stage.setScene(scene);
		stage.show();

		// setup
		feld = new Spielfeld(gc);
	}

	private void draw() {
		gc.clearRect(0, 0, can.getWidth(), can.getHeight());
		feld.show();
	}
}