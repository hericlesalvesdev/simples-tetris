package main;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {

        // configuração da janela do jogo
        JFrame janela = new JFrame("Simples tetris");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);

        // Aqui vamos adicionar o "GamePanel" na janela
        GamePanel gp = new GamePanel();
        janela.add(gp);
        janela.pack();

       janela.setLocationRelativeTo(null);
       janela.setVisible(true);

       gp.startGame();

    }
}