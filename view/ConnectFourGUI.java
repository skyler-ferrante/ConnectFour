package view;

import model.*;

import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.effect.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;

public class ConnectFourGUI extends Application {
    private static final Image BLANK_IMAGE = new Image("images/blank.png");
    private static final Image BLACK_IMAGE = new Image("images/black.png");
    private static final Image RED_IMAGE = new Image("images/red.png");
    private static final Font FONT = new Font("Comic Sans MS", 30);
    private static final Effect BLACK_EFFECT = new InnerShadow(200, Color.BLACK);
    private static final Effect RED_EFFECT = new InnerShadow(200, Color.RED);

    private ConnectFour model;
    
    private ImageView[][] images;

    @Override
    public void start(Stage stage) throws Exception {
        model = new ConnectFour();

        images = new ImageView[model.WIDTH][model.HEIGHT];

        model.register((x, y) -> {
            updateImage(x, y, model.getChecker(x, y));
        });

        VBox box = new VBox();

        GridPane boardPane = new GridPane();
        
        Text status = new Text();
        status.setFont(FONT);
        setCurrentPlayer(status);
        status.setText("Press top y spaces ("+status.getText()+")");

        Button reset = new Button("RESET");
        reset.setFont(FONT);
        reset.setOnAction((e) -> {
            model.reset();
            setCurrentPlayer(status);
        });
        
        box.getChildren().addAll(boardPane, status, reset);

        for(int x = 0; x < model.WIDTH; x++){
            for(int y = 0; y < model.HEIGHT; y++){
                Node node = makeConnectFourButton(x, y);
                if(y == 0){
                    makeNodeClickable(node, x, status);
                }
                boardPane.add(node, x, y);
            }
        }

        stage.setScene(new Scene(box));
        stage.setTitle("Connect Four");
        stage.show();
    }


    private Button makeConnectFourButton(int x, int y){
        ImageView squareView = new ImageView();
        squareView.setFitHeight(100);
        squareView.setFitWidth(100);
        
        images[x][y] = squareView;

        Checker checker = model.getChecker(x, y);
        updateImage(x, y, checker);

        Button button = new Button("", squareView);
        button.setBackground(
            new Background(
                new BackgroundImage(
                    BLANK_IMAGE,
                    BackgroundRepeat.NO_REPEAT, 
                    BackgroundRepeat.NO_REPEAT, 
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT
                )
            )
        );
        button.setPadding(new Insets(0));
        return button;
    }

    private void updateImage(int x, int y, Checker checker){
        ImageView squareView = images[x][y];
        switch(checker){
            case BLACK:
                squareView.setImage(BLACK_IMAGE);
                break;
            case RED:
                squareView.setImage(RED_IMAGE);
                break;
            default:
                squareView.setImage(BLANK_IMAGE);
        }
    }

    public void makeNodeClickable(Node button, int x, Text status){
        button.setOnMouseClicked((e) -> {
            try {
                model.move(x);
                setCurrentPlayer(status);
            } catch (ConnectFourException error) {
                setCurrentPlayer(status);
                String text = status.getText();
                status.setText(error.getMessage()+" ("+text+")");
            }
        });

        button.setOnMouseEntered((e) -> {
            Effect effect = (model.getCurrentPlayer() == Checker.RED) ? RED_EFFECT : BLACK_EFFECT;
            images[x][0].setEffect(effect);
        });
        button.setOnMouseExited((e) -> {
            images[x][0].setEffect(null);
        });
    }

    public void setCurrentPlayer(Text status){
        Checker currentPlayer = model.getCurrentPlayer();
        status.setText(
            (currentPlayer == Checker.RED) ? "RED" : "BLACK"
        );
        status.setFill(
            (currentPlayer == Checker.RED) ? Color.RED : Color.BLACK
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
