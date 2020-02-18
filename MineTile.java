import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.ArrayList;
import javafx.scene.input.*;

public class MineTile
{
    public int x;
    public int y;
    public boolean hasMine;
    public boolean opened;
    public boolean flagged;
    private MineGrid grid;
    private ImageView image;
    
    public MineTile(int x, int y, MineGrid grid)
    {
        this.x = x;
        this.y = y;
        this.hasMine = false;
        this.opened = false;
        this.flagged = false;
        this.grid = grid;
        setImage();

    }
    public ArrayList<MineTile> getNeighbors() // all surrounding (up to) 8 tiles
    {
        ArrayList<MineTile> neighbors = new ArrayList<MineTile>();

        if (x > 0) // not on leftmost column
        {
            neighbors.add(grid.get(x-1).get(y)); // left
            if (y > 0) // not on topmost row
            {
                neighbors.add(grid.get(x-1).get(y-1)); // top-left
            }
            if (y < grid.get(x).size() - 1) // not on bottom row
            {
                neighbors.add(grid.get(x-1).get(y+1)); // bottom-left
            }
        }
        if (x < grid.size() - 1) // not on rightmost column
        {
            neighbors.add(grid.get(x+1).get(y)); // right
            if (y > 0) // not on topmost row
            {
                neighbors.add(grid.get(x+1).get(y-1)); // top-right
            }
            if (y < grid.get(x).size() - 1) // not on bottom row
            {
                neighbors.add(grid.get(x+1).get(y+1)); // bottom-right
            }
        }
        if (y > 0) // not on top row
        {
            neighbors.add(grid.get(x).get(y-1)); // top
        }
        if (y < grid.get(x).size() - 1) // not on bottom row
        {
            neighbors.add(grid.get(x).get(y+1)); // bottom
        }

        return neighbors;
    }

    public int surroundingMines()
    {
        int mines = 0;
        for (MineTile tile: getNeighbors())
        {
            if (tile.hasMine)
            {
                mines++;
            }
        }
        return mines;
    }
    
    public int surroundingFlaggedTiles()
    {
        int flags = 0;
        for (MineTile tile: getNeighbors())
        {
            if (tile.flagged)
            {
                flags++;
            }
        }
        return flags;
    }

    public void open()
    {
        if (!(this.opened) && !(this.flagged))
        {
            this.flagged = false;
            this.opened = true;
            if ((this.surroundingMines() == 0) && !(hasMine))
            {
                for (MineTile tile: getNeighbors())
                {
                    tile.open();
                }
            }
            this.draw();
        }
    }

    public void setImage()
    {
        ImageView iv = new ImageView();
        if (!(opened)) {
            if (flagged) {
                iv.setImage(new Image("./assets/flagged.png"));
            } else {
                iv.setImage(new Image("./assets/unopened.png"));
            }
        } else {
            if (hasMine) {
                iv.setImage(new Image("./assets/opened-mine.png"));
            }
            else if (surroundingMines() > 0) {
                iv.setImage(new Image("./assets/" + Integer.toString(surroundingMines()) + ".png"));
            } else {
                iv.setImage(new Image("./assets/opened.png"));
            }
        }

        iv.setX(x*grid.level.tileSize);
        iv.setY(y*grid.level.tileSize);

        iv.setOnMouseEntered( e -> {
            grid.level.hoveredOverTile = this;
        });

        iv.setOnMouseClicked( e -> {
            if (e.getButton() != MouseButton.SECONDARY) { // left click
                
                if (!(this.grid.initialized))
                {
                    this.grid.setMines(x, y);
                    this.grid.initialized = true;
                }
                
                this.open();
                if ((opened) && (surroundingFlaggedTiles() == surroundingMines())) { // clear non-flagged tiles if enough nearby are flagged
                    for (MineTile tile: getNeighbors())
                    {
                        if (!(tile.flagged))
                        {  
                            tile.open();
                        }
                    }
                }
            } else { // right click
                if (!(this.flagged) && !(this.opened))
                {
                    this.flagged = true;
                } else {
                    this.flagged = false;
                }
                this.draw();
            }
        });
        this.image = iv;
    }
    public void draw()
    {
        if (grid.level.center.getChildren().contains(image))
        {
            grid.level.center.getChildren().remove(image);
        }
        setImage();
        grid.level.center.getChildren().add(image);
    }
}