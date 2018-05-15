import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Game
{
  private Grid grid;
  private int userRow;
  private int msElapsed;
  public int timesGet;
  public int timesAvoid;
  private String difficulty;
  
  public Game()
  {
    grid = new Grid(10, 20);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), "user.gif");
    playSound();
  }
  
  public void play()
  {
    while (!isGameOver())
    {
      grid.pause(50);
      handleKeyPress();
      setDifficulty();
      if (msElapsed % 300 == 0)
      {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 50;
    }
  }
  
  public void handleKeyPress()
  {
	  int key = grid.checkLastKeyPressed();
	  
	  if(key == 40 && userRow <= 8) {
		  handleCollision(new Location(userRow, 0));
		  grid.setImage(new Location(userRow, 0), null);
		  grid.setImage(new Location(++userRow, 0), "user.gif");
	  }
		  
	  if(key == 38 && userRow >= 1) {
		  handleCollision(new Location(userRow, 0));
		  grid.setImage(new Location(userRow, 0), null);
		  grid.setImage(new Location(--userRow, 0), "user.gif");
	  }
  }
  
  public void populateRightEdge()
  {
	  int num = (int) Math.floor(Math.random()*100);
	  
	  if(difficulty == "Easy") {
		  if(num % 2 == 0) {
			  int row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "avoid.gif");
		  } else {
			  int row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "get.gif");
		  }
	  }
	  
	  if(difficulty == "Medium") {
		  if(num % 2 == 0) {
			  int row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "avoid.gif");
			  row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "avoid.gif");
		  } else {
			  int row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "get.gif");
			  row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "get.gif");
		  }
	  }
	  
	  if(difficulty == "Hard") {
		  if(num % 2 == 0) {
			  int row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "avoid.gif");
			  row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "avoid.gif");
			  row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "avoid.gif");
		  } else {
			  int row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "get.gif");
			  row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "avoid.gif");
			  row = (int) Math.floor(Math.random()*10);
			  grid.setImage(new Location(row, 19), "avoid.gif");
		  }
	  }
	  
  }
  
  public void scrollLeft()
  {
      handleCollision(new Location(userRow, 0));
      grid.setImage(new Location(userRow, 0), "user.gif");
	  for(int i = 0; i <=9; i++) {
		  scrollRow(i);
	  }
  }
  
  public void handleCollision(Location loc)
  {
	  String check = grid.getImage(new Location(userRow, 0));  
	  if(check == "null") {
	  }
	  if(check == "get.gif") {
		  timesGet++;
	  }
	  if(check == "avoid.gif") {
		  timesAvoid++;
	  }
  }
  
  public int getScore()
  {
    return timesGet;
  }
  
  public void updateTitle()
  {
    grid.setTitle("Takin' a Gulp!   Drinks: " + getScore() + "   " + "Time: " + (msElapsed/1000) + "   " + "Difficulty: " + difficulty);
  }
  
  public boolean isGameOver()
  {
	if(timesAvoid == 1) {
		Color black = new Color(0, 0, 0);
		Cell.setColor(black);
		for(int j = 0; j < 10; j++) {
			for(int i = 0; i < 20; i++) {
				grid.setImage(new Location(j, i), null);
			}
		}
		newGame();
		return true;
	}
    return false;
  }
  
  public static void test()
  {
    Game game = new Game();
    game.play();
  }
  
  public void scrollRow(int row) {
	  
	  for(int i = 0; i <= 18; i++) {
		grid.setImage(new Location(row, i), grid.getImage(new Location(row, i+1)));
		grid.setImage(new Location(row, i+1), null);
		
		if(grid.getImage(new Location(userRow, 0)) == null) {
			grid.setImage(new Location(row, i), grid.getImage(new Location(row, i+1)));
			grid.setImage(new Location(userRow, 0), "user.gif");
		}
	  }
  }
  
  public void setDifficulty() {
	  
	  if(msElapsed/1000 < 20) {
		  difficulty = "Easy";
	  }
	  if(msElapsed/1000 >= 20) {
		  difficulty = "Medium";
	  }
	  if(msElapsed/1000 >= 40) {
		  difficulty = "Hard";
	  }
  }

  public void playSound() {
	  
	  File song = new File("src\\song.wav");
	  findFile(song);
  }
  
  public void findFile(File Sound) {
	  
	  try {
		  Clip clip = AudioSystem.getClip();
		  clip.open(AudioSystem.getAudioInputStream(Sound));
		  clip.start();
		  clip.loop(Clip.LOOP_CONTINUOUSLY);
	  } catch(Exception E) {
	  }
  }

  public void newGame() {

  	System.out.println("You lose! Press any key to play again.");
	try{
		System.in.read();
		test();
	} catch (Exception e) {
		System.out.println("Oops, there was an error!");
	}
  }
  
  public static void main(String[] args)
  {
    test();
  }
}