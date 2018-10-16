package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application
{

        PixelReader pixelReader;
        PixelWriter pixelWriter;
        ImageView imageView;
        Image image;

        WritableImage wImage;
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 310, 250);

        @Override public void start(Stage primaryStage)
        {
            image = new Image("/sample/image.png");
            imageView = new ImageView();
            wImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());

            pixelReader = image.getPixelReader();
            pixelWriter = wImage.getPixelWriter();
            // Display image on screen

            root.getChildren().add(imageView);
            primaryStage.setTitle("The World");
            primaryStage.setScene(scene);
            imageView.setImage(wImage);
            primaryStage.show();
            new lifeformThead().start();

        }

        class lifeformThead extends Thread
        {
            @Override public void run()
            {
                while (true)
                {
                    Lifeform lifeform;
                    for (int y = 0; y < 180; y++)
                    {
                        for (int x = 0; x < 280; x++)
                        {
                            lifeform = unknown(x, y);
                            if (lifeform.alive)
                            {
                                switch (lifeform.neighbors)
                                {
                                    case 0:
                                    case 1:
                                        flip(lifeform);
                                        break;
                                    case 2:
                                    case 3:
                                        break;

                                    default:
                                        flip(lifeform);
                                }
                            } else
                            {
                                if (lifeform.neighbors == 3) flip(lifeform);
                            }
                        }
                    }
                    pixelReader = wImage.getPixelReader();
                    try
                    {
                        Thread.sleep(50);
                    } catch (InterruptedException e)
                    {
                    }
                }
            }

            public void flip(Lifeform lifeform)
            {
                if (pixelReader.getColor(lifeform.here.x, lifeform.here.y).equals(Color.BLACK))
                { pixelWriter.setColor(lifeform.here.x, lifeform.here.y, Color.WHITE);} else
                {
                    pixelWriter.setColor(lifeform.here.x, lifeform.here.y, Color.BLACK);
                }

            }

            public Lifeform unknown(int a, int b)
            {
                Position where = new Position(a, b);
                Lifeform thing = new Lifeform();

                if (pixelReader.getColor(a, b).equals(Color.BLACK))
                {
                    thing.alive = true;
                } else thing.alive = false;


                int right = (where.x < 279) ? where.x + 1 : 279;
                int x;
                int y = (where.y > 0) ? where.y - 1 : 0;
                int btm = (where.y < 179) ? where.y + 1 : 179;
                int count;

                if (thing.alive) {count = -1; } //remove the life from from the count
                else { count = 0; }         //unless dead...

                for (; y <= btm; y++)
                {
                    x = (where.x > 0) ? where.x - 1 : 0;
                    for (; x <= right; x++)
                    {
                        if (pixelReader.getColor(x, y).equals(Color.BLACK)) { count++; }
                    }
                }
                thing.neighbors = count;
                thing.here = where;
                return thing;
            }
        }
    public static void main(String[] args) {
        launch(args);
    }
}
