package xyz.view;

import xyz.listener.GameListener;
import xyz.model.BoardLocation;
import xyz.server.NetTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

public class SquareComponent extends JPanel {
    private static boolean first = true;
    private Image grid = Toolkit.getDefaultToolkit().getImage("src/xyz/view/pic/board1.png");
    private Image grid2 = Toolkit.getDefaultToolkit().getImage("src/xyz/view/pic/board2.png");
    private Image grid3 = Toolkit.getDefaultToolkit().getImage("src/xyz/view/pic/board3.png");
    private Image grid4 = Toolkit.getDefaultToolkit().getImage("src/xyz/view/pic/board4.png");
    private int size;
    private int x;
    private int y;
    private BoardLocation location;


    public SquareComponent(int x, int y, int size) {
        this.x = x;
        this.y = y;
        location = new BoardLocation(x, y);
        Random random = new Random();
        int a = random.nextInt(4);
        switch (a) {
            case 0:
                break;
            case 1:
                grid = grid2;
                break;
            case 2:
                grid = grid3;
                break;
            case 3:
                grid = grid4;
                break;
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (BeginFrame.mode == 4 && Save.gameController.getCurrentPlayer() == 2)
                        return;
                    if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null && !Save.gameController.first) {
                        NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "LEFT" + "," + location.getRow() + "," + location.getColumn() + "," + "local");
                    }
                    if (Function.Zhadan.open) {
                        if (BeginFrame.mode == 4 && Save.gameController.getCurrentPlayer() == 2)
                            return;
                        if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null && !Save.gameController.first) {
                            NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "LEFT" + "," + location.getRow() + "," + location.getColumn() + "," + "local");
                        }
                        if (Save.gameController.first) {
                            try {
                                Save.gameController.onPlayerLeftClick(location);
                            } catch (IOException | InterruptedException ioException) {
                                ioException.printStackTrace();
                            }
                        } else if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4) {
                            judgeZhadan(location.getRow(), location.getColumn());
                            judgeZhadan(location.getRow() + 1, location.getColumn());
                            judgeZhadan(location.getRow() - 1, location.getColumn());
                            judgeZhadan(location.getRow(), location.getColumn() + 1);
                            judgeZhadan(location.getRow(), location.getColumn() - 1);
                        } else if ((DifficultyDecision.model == 3 && Save.gameController.currentPlayer == 1) || DifficultyDecision.model == 1) {

                            try {
                                Save.gameController.onPlayerLeftClick(location);
                            } catch (IOException | InterruptedException ioException) {
                                ioException.printStackTrace();
                            }

                            judgeZhadan(location.getRow() + 1, location.getColumn());
                            judgeZhadan(location.getRow() - 1, location.getColumn());
                            judgeZhadan(location.getRow(), location.getColumn() + 1);
                            judgeZhadan(location.getRow(), location.getColumn() - 1);
                        }

                        Save.view1.repaint();
                    }

                    try {
                        Save.gameController.onPlayerLeftClick(location);
                    } catch (IOException | InterruptedException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    try {
                        if (BeginFrame.mode == 4 && Save.gameController.getCurrentPlayer() == 2)
                            return;

                        if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null) {
                            NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "RIGHT" + "," + location.getRow() + "," + location.getColumn() + "," + "local");
                        }
                        Save.gameController.onPlayerRightClick(location);
                    } catch (IOException | InterruptedException ioException) {
                        ioException.printStackTrace();
                    }

                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    try {
                        Save.gameController.onPlayerMidClick(location);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null) {
                        NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "Mid" + "," + location.getRow() + "," + location.getColumn() + "," + "local");
                    }
                }
            }


            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());

                if (BeginFrame.mode == 4 && Save.gameController.getCurrentPlayer() == 2)
                    return;
                else
                    Save.gameController.onPlayerMove(location);
                if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null) {
                    NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "MOVE" + "," + location.getRow() + "," + location.getColumn() + "," + "local");
                }

                if (Function.Zhadan.open) {
                    Save.gameController.onPlayerMove(location);
                    Save.gameController.onPlayerMove(new BoardLocation(location.getRow() + 1, location.getColumn()));
                    Save.gameController.onPlayerMove(new BoardLocation(location.getRow(), location.getColumn() + 1));
                    Save.gameController.onPlayerMove(new BoardLocation(location.getRow() - 1, location.getColumn()));
                    Save.gameController.onPlayerMove(new BoardLocation(location.getRow(), location.getColumn() - 1));
                } else Save.gameController.onPlayerMove(location);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
//                System.out.println(2222);
//                JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());


                if (BeginFrame.mode == 4 && Save.gameController.getCurrentPlayer() == 2)
                    return;
                else
                    Save.gameController.onPlayerExit(location);
                if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null) {
                    NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "EXIT" + "," + location.getRow() + "," + location.getColumn() + "," + "local");
                }

                if (Function.Zhadan.open) {
                    Save.gameController.onPlayerExit(location);
                    Save.gameController.onPlayerExit(new BoardLocation(location.getRow() + 1, location.getColumn()));
                    Save.gameController.onPlayerExit(new BoardLocation(location.getRow(), location.getColumn() + 1));
                    Save.gameController.onPlayerExit(new BoardLocation(location.getRow() - 1, location.getColumn()));
                    Save.gameController.onPlayerExit(new BoardLocation(location.getRow(), location.getColumn() - 1));

                } else Save.gameController.onPlayerExit(location);
            }

        });
        setLayout(new GridLayout(1, 1));
        setSize(size, size);
        this.size = size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintSquare(g);
    }

    private void paintSquare(Graphics g) {
        g.drawImage(grid, size, size, getWidth() - 2 * size, getHeight() - 2 * size, this);
    }

    public static void judgeZhadan(int x, int y) {
        if (x >= 0 && y >= 0 && y < Save.model.getColumn() && x < Save.model.getRow() && (Save.view2.founedmine != Save.model.getNumberOfMind() || Save.gameController.Winner().equals(""))) {
            if (!Save.model.getGrid()[x][y].hasLandMine() && !Save.model.getGrid()[x][y].isOpened()) {
                Save.model.getGrid()[x][y].setOpened(true);
                Save.model.getGrid()[x][y].setClick(false);
                Save.model.calculateNum(x, y);
                Save.view1.setItemAt(Save.model.getGrid()[x][y].getLocation(), Save.model.getGrid()[x][y].getNumberOfLandMine());
                if (Save.model.getGrid()[x][y].getNumberOfLandMine() == 0) {
                    int[][] visited = new int[Save.model.getRow()][Save.model.getColumn()];
                    Save.gameController.fill(Save.model, Save.getModel().getGrid(), x, y, visited);
                }
            } else if (!Save.model.getGrid()[x][y].isOpened() && (Save.view2.founedmine != Save.model.getNumberOfMind() || Save.gameController.Winner().equals(""))) {
                Save.model.getGrid()[x][y].setOpened(true);
                Save.model.getGrid()[x][y].setFlag(true);
                Save.view2.findMine();
                if (DifficultyDecision.model != 1 && DifficultyDecision.model != 4) {
//                    if (Save.gameController.currentPlayer == 1)
//                        Save.view2.Goal(Save.gameController.currentPlayer);
//                    else if (Save.gameController.currentPlayer == 2)
                        Save.view2.Goal(Save.gameController.currentPlayer - 1);
                }

                Save.model.calculateNum(x, y);
                Save.view1.setItemAt(Save.model.getGrid()[x][y].getLocation(), ItemUtil.flagNum);
                if (DifficultyDecision.model != 1)
                    Save.gameController.mindShow(Save.model.getGrid()[x][y].getLocation());
                Save.view2.repaint();
            }
        }
    }
}
