package xyz.listener;

import xyz.model.BoardLocation;
import xyz.view.SquareComponent;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface GameListener {
    void onPlayerLeftClick(BoardLocation location) throws IOException, InterruptedException;
    void onPlayerRightClick(BoardLocation location) throws IOException, InterruptedException;
    void onPlayerMidClick(BoardLocation location) throws IOException;
    void onPlayerMove(BoardLocation location);
    void onPlayerExit(BoardLocation location);
}
