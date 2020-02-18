import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;



public class Minesweeper extends Application
{
    public void start(Stage primary)
    {
        MineLevel level = new MineLevel(31, 16, 99, 20);
        Scene scene = new Scene(level, 700, 400);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (!(level.hoveredOverTile.flagged) && !(level.hoveredOverTile.opened)) {
                    level.hoveredOverTile.flagged = true;
                } else {
                    level.hoveredOverTile.flagged = false;

                    if ((level.hoveredOverTile.opened) && (level.hoveredOverTile.surroundingFlaggedTiles() == level.hoveredOverTile.surroundingMines())) { 
                    for (MineTile tile: level.hoveredOverTile.getNeighbors())
                    {
                        if (!(tile.flagged))
                        {  
                            tile.open();
                        }
                    }
                }

                }
                level.hoveredOverTile.draw();
            }
        });
        primary.setScene(scene);
        primary.show();
    }
}