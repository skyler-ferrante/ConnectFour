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
    /**
     * Homemade in MS-Paint, so not masterpieces
     */
    private static final Image BLANK_IMAGE = new Image("images/blank.png");
    private static final Image BLACK_IMAGE = new Image("images/black.png");
    private static final Image RED_IMAGE   = new Image("images/red.png");
    
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
                if(y == model.HEIGHT-1){
                    makeNodeClickable(node, x, status);
                }
                boardPane.add(node, x, model.HEIGHT-1-y);
            }
        }

        stage.setScene(new Scene(box));
        stage.setTitle("Connect Four");
        stage.show();
    }

    /**
     * Creates buttons that will update their background based on the ConnectFour model
     * 
     * @param x X position of button to create
     * @param y Y position of button to create
     * @return Button that was created
     */
    private Button makeConnectFourButton(int x, int y){
        ImageView squareView = new ImageView();
        squareView.setFitHeight(100);
        squareView.setFitWidth(100);
        
        images[x][y] = squareView;

        Checker checker = model.getChecker(x, y);
        updateImage(x, y, checker);

        Button button = new Button("", squareView);
        button.setPadding(new Insets(0));
        return button;
    }

    /**
     * Update image of checker that changed
     * 
     * @param x X position of checker
     * @param y Y position of checker
     * @param checker Checker value to set Checker at (x,y) to
     */
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

    /**
     * Makes node clickable, and adds highlighted effect
     * 
     * @param button Node to make clickable
     * @param x X position of Node
     * @param status The status text box
     */
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
            images[x][model.HEIGHT-1].setEffect(effect);
        });
        button.setOnMouseExited((e) -> {
            images[x][model.HEIGHT-1].setEffect(null);
        });
    }

    /**
     * Set status text box to the current players name
     * 
     * @param status Status text box to change to player name
     */
    public void setCurrentPlayer(Text status){
        Checker currentPlayer = model.getCurrentPlayer();
        status.setText(currentPlayer.name());

        status.setFill(
            (currentPlayer == Checker.RED) ? Color.RED : Color.BLACK
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
