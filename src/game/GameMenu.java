package game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Creates and organises the main menu page.
 *
 * @author Melina Zikou
 */
public class GameMenu implements MenuInterface {

    private boolean wordokuGame;
    private Stage window;
    private BorderPane mainPane;
    private Scene menuScene;
    private PlayerManagement playerManagement;
    private FileManagement fileManagement;

    /**
     * Implements essential actions for the game to begin
     * @param window stage window
     */
    public GameMenu(Stage window) {
        this.window = window;
        mainPane = createMenuScene();
        menuScene = new Scene(mainPane);
        menuScene.getStylesheets().add("app.css");

        playerManagement = new PlayerManagement();
        playerManagement.load("players.bin");

        fileManagement = new FileManagement();

        try{
            fileManagement.load(".\\resources\\games.csv");
        }
        catch (IOException e) {
            //TODO display error
            e.printStackTrace();
        }
    }

    /**
     * Creates all the buttons in the main menu and handles their actions
     * @return the panel
     */
    private BorderPane createMenuScene() {
        //create the logo text
        GridPane logoPane = new GridPane();
        Text logo = new Text("Sudoku");
        logoPane.getChildren().add(logo);
        logo.setId("mainLabel");
        logoPane.getStyleClass().add("main");


        //create language pane
        HBox languagePane = new HBox();
        Label lang = new Label();
        lang.setText("Language  ");

        Button buttonEn = new Button("English");
        Button buttonGr = new Button("Greek");

        languagePane.getChildren().addAll(lang, buttonEn, buttonGr);

        buttonEn.setOnAction(e -> {
            System.out.println("english");
        });
        buttonGr.setOnAction(e -> {
            System.out.println("greek");
        });

        // create main menu pane
        GridPane centerPane = new GridPane();
        centerPane.getStyleClass().add("main");

        // select game label
        Label selectGame = new Label("Select Game");
        selectGame.setId("selectGameLabel");

        // button Sudoku
        Button buttonSud = new Button("Sudoku");
        buttonSud.getStyleClass().add("gameButtons");

        // button killer sudoku
        Button buttonKillSud = new Button("Killer Sudoku");
        buttonKillSud.getStyleClass().add("gameButtons");

        // button duidoku
        Button buttonDuidoku = new Button("Duidoku");
        buttonDuidoku.getStyleClass().add("gameButtons");

        // button/checkbox wordoku
        CheckBox buttonWordoku = new CheckBox("Wordoku");

        // set position of each button
        centerPane.setAlignment(Pos.TOP_CENTER);

        centerPane.setMargin(buttonSud, new Insets(20, 0, 0, 0));
        buttonSud.setAlignment(Pos.CENTER);
        buttonSud.setPrefSize(500, 100);

        centerPane.setMargin(buttonKillSud, new Insets(10, 0, 0, 0));
        buttonKillSud.setAlignment(Pos.CENTER);
        buttonKillSud.setPrefSize(500, 100);

        centerPane.setMargin(buttonDuidoku, new Insets(10, 0, 0, 0));
        buttonDuidoku.setAlignment(Pos.CENTER);
        buttonDuidoku.setPrefSize(500, 100);

        centerPane.setMargin(buttonWordoku, new Insets(10, 0, 0, 0));

        centerPane.add(selectGame, 0, 0);
        centerPane.add(buttonSud, 0, 1);
        centerPane.add(buttonKillSud, 0, 2);
        centerPane.add(buttonDuidoku, 0, 3);
        centerPane.add(buttonWordoku, 0, 4);
        buttonSud.setOnAction(e -> sudokuGameButtonAction());
        buttonKillSud.setOnAction(e -> killerSudokuGameButtonAction());

        buttonSud.setLayoutX(70);
        buttonSud.setLayoutY(80);

        buttonWordoku.setOnAction(e -> {
            wordokuGame = buttonWordoku.isSelected();
        });

        // set the alignment of the logo text
        BorderPane.setAlignment(logo, Pos.TOP_CENTER);

        BorderPane root = new BorderPane();

        root.setTop(logo);
        root.setBottom(languagePane);
        root.setCenter(centerPane);

        root.setPrefSize(700, 700);

        return root;
    }

    /**
     *  Action when button Killer Sudoku is clicked
     */
    private void killerSudokuGameButtonAction() {
        ModelKillerSudoku model = new ModelKillerSudoku();
        GameController controller = new GameController(model);
        String s = "681974523245831697739652148857413962924786351316295784598127436473568219162349875," +
                "15:00:00:16:00:09:00:11:18:06:00:16:00:00:03:07:00:00:15:19:00:10:06:00:00:10:00:" +
                "00:07:00:00:00:09:12:00:03:12:00:10:26:00:00:00:13:00:00:10:00:00:00:12:00:00:10:" +
                "10:00:22:00:00:15:08:00:00:00:18:00:00:00:00:19:00:21:00:00:00:07:00:00:00:00:00," +
                "111223314223331214411241234421242431324112421314113324213332114244332413244224433";

        model.load(s);

        GameSudokuView gameSudoku = new GameSudokuView(model, controller, window,this);
        window.setScene(gameSudoku.scene);
    }

    /**
     *  Action when button Sudoku is clicked
     */
    private void sudokuGameButtonAction() {
        ModelSudoku model = new ModelSudoku(wordokuGame);
        GameController controller = new GameController(model);
        String s =
                "..3.....9" +
                        "2....43.." +
                        "461......" +
                        "...8..9.6" +
                        "...4..8.." +
                        ".......3." +
                        "..9....15" +
                        ".2.68..9." +
                        "63.5..2.8," +
                        "573268149298154367461793582342871956957436821816925734789342615125687493634519278";
        model.load(s);

        GameSudokuView gameSudoku = new GameSudokuView(model, controller, window, this);
        this.window.setScene(gameSudoku.scene);
        Player player = new Player();
    }

    /**
     * Shows the main menu
     */
    @Override
    public void showMainMenu() {
        window.setScene(this.menuScene);
        window.show();
    }
}