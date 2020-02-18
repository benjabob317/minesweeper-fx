import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;


public class MineLevel extends BorderPane
{
    public MineGrid grid;
    public int width;
    public int height;
    public int mines;
    public Pane center;
    public double tileSize;
    public MineTile hoveredOverTile;

    public MineLevel(int width, int height, int mines, double tileSize)
    {
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.tileSize = tileSize;

        this.grid = new MineGrid(width, height, mines, this);

        this.center = new Pane();
        center.setPrefSize(tileSize*width, tileSize*height);
        center.setMinSize(tileSize*width, tileSize*height);
        center.setMaxSize(tileSize*width, tileSize*height);
        setCenter(center);
        setTop(buildTopBar());

        grid.draw();
    }
    public HBox buildTopBar()
    {
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER);

        Button newGame = new Button("New Game");
        newGame.setOnAction( e -> {
            center.getChildren().clear();
            grid = new MineGrid(width, height, mines, this);
            grid.draw();
        });
        newGame.setFocusTraversable(false); // stops space from firing the button


        topBar.getChildren().addAll(newGame);
        return topBar;
    }
}