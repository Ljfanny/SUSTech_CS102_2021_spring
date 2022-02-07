package xyz.view;

import xyz.Main;
import xyz.controller.GameController;
import xyz.model.Board;

import javax.swing.*;

public class MenuFunction {
    public static void setBefore(int i){
        DifficultyDecision.gameOverIsTurnUp=false;
        DifficultyDecision.gameOverIsTurnUp1=false;
        if (i==3){
            DifficultyDecision.isNew = true;
            DifficultyDecision.isReturn = true;
            DifficultyDecision.gameOver.setVisible(false);
            DifficultyDecision.gameOver1.setVisible(false);
//            Save.view2.setVisible(false);
            TypeFrame.typeFrame.setVisible(false);
            BeginFrame.choiceFrame.setVisible(true);
            TypeFrame.firsttime=true;

        } else if (i == 2) {
            DifficultyDecision.isNew = true;
            DifficultyDecision.isReturn = true;
            DifficultyDecision.gameOver.setVisible(false);
            DifficultyDecision.gameOver1.setVisible(false);
//            Save.view2.setVisible(false);
            MusicPlayer.aau5.play();
            BeginFrame.choiceFrame.setVisible(false);
            Main.background.setVisible(true);
            MusicPlayer.aau6.stop();

        } else if (i == 4) {
            if (DifficultyDecision.model == 4) {
                Object[] options = {"OK "};
                JOptionPane.showOptionDialog(null, "This model doesn't support it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            } else {
                GameController.counterrr=0;
                GameController.counterr=0;
                GameController.coun1=0;
                GameController.coun2=0;
                GameController.coun3=0;
                GameController.coun4=0;
                Save.view2.setVisible(false);
                DifficultyDecision.gameOver.setVisible(false);
                DifficultyDecision.gameOver1.setVisible(false);
                DifficultyDecision.gameFrame.setVisible(false);
                Save.gameController.setCurrentPlayer(1);
                TypeFrame.typeFrame.setVisible(true);
                MusicPlayer.aau6.play();
                MusicPlayer.aau8.stop();
                DifficultyDecision.AItype=1;

//                if (Save.model.getNumberOfMind() == Board.getNUMBER_0F_MIND_LOW_LEVEL()) {
//
//                    DifficultyDecision.setLevel(9, 9, Board.getNUMBER_0F_MIND_LOW_LEVEL(), 600, 600, DifficultyDecision.model);
//                    Save.gameController.first = true;
//                    Function.alwaysUse = false;
//                } else if (Save.model.getNumberOfMind() == Board.getNUMBER_0F_MIND_MEDIUM_LEVEL()) {
//                    DifficultyDecision.gameFrame.remove(DifficultyDecision.b1);
//                    DifficultyDecision.setLevel(16, 16, Board.getNUMBER_0F_MIND_MEDIUM_LEVEL(), 600, 600, DifficultyDecision.model);
//                    Save.gameController.first = true;
//                    Function.alwaysUse = false;
//                } else {
//                    DifficultyDecision.gameFrame.remove(DifficultyDecision.b1);
//                    DifficultyDecision.setLevel(DifficultyDecision.ro, DifficultyDecision.col, Save.model.getNumberOfMind(), DifficultyDecision.roll, DifficultyDecision.coll, DifficultyDecision.model);
//                    Save.gameController.first = true;
//                    Function.alwaysUse = false;
//                }

            }

        }

    }
}