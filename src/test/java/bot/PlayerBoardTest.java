package bot;

import board.Board;
import board.Tile;
import gui.TurnCommunicator;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class PlayerBoardTest {

    @Test
    public void createPlayerBoard() {
        Player player = new Player(Color.BLUE, "abc", 2);
        Board board = mock(Board.class);
        when(board.getWidth()).thenReturn(2);
        when(board.getHeight()).thenReturn(3);
        when(board.getTile(any())).thenReturn(null);
        InOrder inOrder = inOrder(board);

        PlayerBoard playerBoard = PlayerBoard.createPlayerBoard(board, player);

        assertNotNull(playerBoard);
        assertEquals(2, playerBoard.getWidth());
        assertEquals(3, playerBoard.getHeight());
        inOrder.verify(board).getWidth();
        inOrder.verify(board).getHeight();
        inOrder.verify(board, times(2 * 3)).getTile(any());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void getGuiTile() {
        //todo - what does it even do?
    }

    @Test
    public void getGuiTiles() {
        //todo - same as above ^
    }

    @Test
    public void getWidth() {
        Player player = mock(Player.class);
        Board board = mock(Board.class);
        when(board.getWidth()).thenReturn(2);
        InOrder inOrder = inOrder(board);

        PlayerBoard playerBoard = PlayerBoard.createPlayerBoard(board, player);

        assertEquals(2, playerBoard.getWidth());
    }

    @Test
    public void getHeight() {
        Player player = mock(Player.class);
        Board board = mock(Board.class);
        when(board.getWidth()).thenReturn(2);
        when(board.getHeight()).thenReturn(3);
        InOrder inOrder = inOrder(board);

        PlayerBoard playerBoard = PlayerBoard.createPlayerBoard(board, player);

        assertEquals(3, playerBoard.getHeight());
    }

    @Test
    public void getAccessibleTiles() {
        Player player = mock(Player.class);
        PlayerTile[][] playerTiles = new PlayerTile[16][16];

        for(int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                playerTiles[i][j] = new PlayerTile(mock(Tile.class), player);
            }
        }

        List<PlayerTile> tmp_list = new ArrayList<>();
        for (PlayerTile[] array : playerTiles) {
            tmp_list.addAll(Arrays.asList(array));
        }
        List<PlayerTile> list = tmp_list.stream()
                .filter(PlayerTile::isAccessible)
                .collect(Collectors.toList());

        PlayerBoard playerBoard = new PlayerBoard(playerTiles);
        List<PlayerTile> accessibleTiles = playerBoard.getAccessibleTiles();

        for(PlayerTile pTile : list) {
            if(pTile.isAccessible()) {
                assertEquals(true, accessibleTiles.contains(pTile));
            }
            else
                assertEquals(false, accessibleTiles.contains(pTile));
        }
    }

    @Test
    public void updateVision() {
        //todo - there is still no vision
    }
}