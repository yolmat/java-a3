package testesdeinterfaceparaa3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class FlappyBirdGame extends JFrame implements ActionListener {
    private Timer timer;
    private int birdY = 200;
    private int birdVelocity = 0;
    private ArrayList<Barra> barras = new ArrayList<>();

    
    // Contrutor da classe para inicializar 
    public FlappyBirdGame() {
        setTitle("Flappy Bird");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        timer = new Timer(20, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    birdVelocity = -7; //velocidade da entidade que voa
                }
            }
        });

        criarBarras();
        setVisible(true);
    }

    
    // método para gerar as barras em que o pássaro terá que passar por dentro
    private void criarBarras() {
        barras.clear();
        for (int i = 0; i < 5; i++) {
            int height = (int) (Math.random() * (getHeight() - 100));
            barras.add(new Barra(getWidth() + i * 300, height)); //O espaço entre criar uma barra ou outra
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        birdVelocity += 1;
        birdY += birdVelocity;

        for (Barra barra : barras) {
            barra.setX(barra.getX() - 1);
        }

        if (birdY < 0) {
            birdY = 0;
        }

        if (birdY > getHeight() - 50) {
            birdY = getHeight() - 50;
        }

        if (barras.get(0).getX() < -50) {
            barras.remove(0);
            int height = (int) (Math.random() * (getHeight() - 100));
            barras.add(new Barra(getWidth(), height));
        }

        verificarColisao();
        repaint();
    }

  
    
    // método para verificar se bateu ou não na barra
    private void verificarColisao() {
    Rectangle birdRect = new Rectangle(50, birdY, 30, 30);
    for (Barra barra : barras) {                                  //esse 1 é o distanciamento máximo que a entidade pode chegar da barra
        Rectangle barraSuperiorRect = new Rectangle(barra.getX(), 1, barra.getLargura(), barra.getAltura());
        Rectangle barraInferiorRect = new Rectangle(barra.getX(), barra.getAltura() + 100, barra.getLargura(), getHeight() - barra.getAltura() - 100);
        if (birdRect.intersects(barraSuperiorRect) || birdRect.intersects(barraInferiorRect)) {
            JOptionPane.showMessageDialog(this, "Loser! Don't ever start again!");
            timer.stop();
            break;
        }
    }
}

    
    // Método para definir a interface de cor etc
    @Override
    public void paint(Graphics g) { //Método que atribui cores as entidades
        super.paint(g);
        g.setColor(Color.ORANGE);
        g.fillRect(50, birdY, 30, 30);

        g.setColor(Color.BLACK);
        for (Barra barra : barras) {
            g.fillRect(barra.getX(), 0, barra.getLargura(), barra.getAltura());
            g.fillRect(barra.getX(), barra.getAltura() + 100, barra.getLargura(), getHeight() - barra.getAltura() - 100);
        }
    } 
}