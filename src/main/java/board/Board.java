package board;

import java.util.List;
import java.util.Random;

public class Board {
    private Tile[][] tiles;
    private List<Tile> strongholdList;
    private final int width;
    private final int height;
    private final Random random;
    private int lastAppeal;

    public Board(int width, int height){
        random = new Random();
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        for (int i=0; i<width; i++){
          for (int j=0; j<height; j++){
            tiles[i][j] = new Tile(new Coordinates(i, j));
          }
        }
        for (int i=0; i<width; i++){
          for (int j=0; j<height; j++){
            if(i > 0 && j > 0) tiles[i][j].addNeighbour(tiles[i-1][j-1]);
            if(i > 0) tiles[i][j].addNeighbour(tiles[i-1][j]);
            if(i > 0 && j < height-1) tiles[i][j].addNeighbour(tiles[i-1][j+1]);
            if(j < height-1) tiles[i][j].addNeighbour(tiles[i][j+1]);
            if(i < width-1 && j < height-1) tiles[i][j].addNeighbour(tiles[i+1][j+1]);
            if(i < width-1) tiles[i][j].addNeighbour(tiles[i+1][j]);
            if(i < width-1 && j > 0) tiles[i][j].addNeighbour(tiles[i+1][j-1]);
            if(j > 0) tiles[i][j].addNeighbour(tiles[i][j-1]);
          }
        }
    }

    public Tile getTile(Coordinates coords){
        return tiles[coords.getX()][coords.getY()];
    }

    public void markAsStronghold(Coordinates coords){
        Tile tile = tiles[coords.getX()][coords.getY()];
        tile.setStronghold();
        strongholdList.add(tile);
    }

    public void markAndDelete(){
      int appeal = lastAppeal;
      while (appeal == lastAppeal){
        appeal = random.nextInt(10000);
      }
      lastAppeal = appeal;

      for (Tile stronghold: strongholdList){
        if(stronghold.isInhabitated()){
          stronghold.getInhabitant().setAppeal(appeal);
          stronghold.broadcastAppeal(appeal);
        }
      }

      for (int i=0; i<width; i++){
        for (int j=0; j<height; j++){
          if(tiles[i][j].isInhabitated() && tiles[i][j].getInhabitant().getAppeal() != appeal){
            tiles[i][j].setInhabitant(null);
          }
        }
      }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public List<Tile> getStrongholdList() {
        return strongholdList;
    }

    public int getWidth(){
      return width;
    }

    public int getHeight(){
      return height;
    }
}
