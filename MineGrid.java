import java.util.ArrayList;

public class MineGrid extends ArrayList<ArrayList<MineTile>>
{
    public int width;
    public int height;
    public int mines;
    public int unflaggedMines;
    public int unopenedMinelessTiles;
    public int startingTime;
    public MineLevel level;
    public boolean initialized;

    public MineGrid(int width, int height, int mines, MineLevel level)
    {
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.level = level;
        this.unflaggedMines = mines;
        this.unopenedMinelessTiles = width*height - mines;

        for (int x = 0; x < this.width; x++)
        {
            this.add(new ArrayList<MineTile>());
            for (int y = 0; y < this.height; y++)
            {
                this.get(x).add(new MineTile(x, y, this));
            }
        }
        this.initialized = false;
    } 
    public void draw()
    {
        for (ArrayList<MineTile> column: this)
        {
            for (MineTile tile: column)
            {
                tile.draw();
            }
        }
    }
    public void addRandomMine(int clickX, int clickY)
    {
        boolean incomplete = true;
        int x = 0;
        int y = 0;
        while (incomplete)
        {
            x = (int) Math.floor(Math.random()*width);
            y = (int) Math.floor(Math.random()*height);
            if (!(this.get(x).get(y).hasMine) && ((Math.abs(x - clickX) > 1) || (Math.abs(y - clickY) > 1))) // no mine and not within 1 tile of the click coordsx
            {
                this.get(x).get(y).hasMine = true;
                incomplete = false;
            }
        }
    }
    public void setMines(int clickX, int clickY)
    {
        for (int i = 0; i < mines; i++)
        {
            addRandomMine(clickX, clickY);
        }
    }
}