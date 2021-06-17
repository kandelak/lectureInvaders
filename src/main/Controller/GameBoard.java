package main.Controller;

import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.Audio.AudioPlayerInterface;
import main.GameEntity.Alien;
import main.GameEntity.Cannon;
import main.GameEntity.Player;
import main.View.GameBoardUI;
import main.View.GameToolBar;


public class GameBoard {

	private static final int UPDATE_PERIOD = 1000 / 25; // The update period of the game in ms, this gives us 25 fps
	private static final int NUMBER_OF_ALIENS = 15;
	private static final double DEFAULT_ALIEN_STEP = 1.0;
	private final static double DEFAULT_CANNON_STEP = 3.0;
	private final static int PLAYER_LIFE_POINTS = 3;
	private AudioPlayerInterface audioPlayer;
	private GameOutcome gameOutcome = GameOutcome.OPEN;


	private final Dimension2D size;

	private boolean running;
	private Timer gameTimer; // Timer responsible for updating the game every frame that runs in a separat thread

	// Services
	private GameBoardUI gameBoardUI;
	private KeyListener keyListener;
	List<Alien> aliens = new ArrayList<>();
	Player player;

	public GameBoard(Dimension2D size) {
		this.size = size;

		// create entities
		player = new Player(PLAYER_LIFE_POINTS, new Cannon(this.size));
		player.setup();
		player.getCannon().setPosition(size.getWidth() / 2.0, size.getHeight() * 0.85);
		createAliens();

		// create UI and keyListener
		gameBoardUI = new GameBoardUI(this);
		keyListener = new KeyListener(gameBoardUI);
	}

	/**
	 * Creates Aliens and gives them positions
	 */
	private void createAliens() {
		for (int i = 0; i < NUMBER_OF_ALIENS; i++) {
			aliens.add(new Alien(this.size));
		}
		positionAliens();
	}

	private void positionAliens() {

	}

	private void startTimer() {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				update();
			}
		};
		if (this.gameTimer != null) {
			this.gameTimer.cancel();
		}
		this.gameTimer = new Timer();
		this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
	}

	public void onAction(GameToolBar.GAME_TOOLBAR_ACTION action){
		switch (action) {
			case START -> startGame();
			case STOP -> {
				stopGame();

				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to stop the game?", ButtonType.YES,
						ButtonType.NO);
				alert.setTitle("Stop Game Confirmation");
				// By default the header additionally shows the Alert Type (Confirmation)
				// but we want to disable this to only show the question
				alert.setHeaderText("");

				Optional<ButtonType> result = alert.showAndWait();
				// reference equality check is OK here because the result will return the same
				// instance of the ButtonType
				if (result.isPresent() && result.get() == ButtonType.YES) {
					// TODO: reset game?
					System.exit(0);
				} else {
					// continue running
					gameBoardUI.startGame();
				}
			}
		}
	}

	/**
	 * Updates the position of each Alien.
	 */
	public void update() {
		// when this.gameBoard.getOutcome() is OPEN, do nothing
		if (gameOutcome == GameOutcome.LOST) {
			gameBoardUI.showAsyncAlert("Oh.. you lost.");
			stopGame();
		} else if (gameOutcome == GameOutcome.WON) {
			gameBoardUI.showAsyncAlert("Congratulations! You won!!");
			stopGame();
		}

		moveEntities();
		gameBoardUI.paint();
	}

	public void startGame() {
		playMusic();
		gameBoardUI.startGame();
		startTimer();
		this.running = true;
	}

	/**
	 * Stops the game. Cars stop moving and background music stops playing.
	 */
	public void stopGame() {
		stopMusic();
		gameBoardUI.stopGame();
		this.gameTimer.cancel();
		this.running = false;
	}

	/**
	 * Moves all Aliens on this game board one step downwards and Cannon too if key
	 * is pressed.
	 */
	void moveEntities() {
		for (Alien alien : aliens) {
			alien.moveDown(DEFAULT_ALIEN_STEP);
		}

		if(keyListener.getKeyPressed() != null)
			switch (keyListener.getKeyPressed()) {
				case RIGHT:
				case D:
					player.getCannon().moveHorizontally(DEFAULT_CANNON_STEP);
					break;

				case LEFT:
				case A:
					player.getCannon().moveHorizontally(-1 * DEFAULT_CANNON_STEP);
					break;

				case SPACE:
					player.getCannon().shoot();
					break;

				default: break; // ignore other input
			}
	}

	public AudioPlayerInterface getAudioPlayer() { return this.audioPlayer; }
	public void setAudioPlayer(AudioPlayerInterface audioPlayer) { this.audioPlayer = audioPlayer; }
	public void playMusic() { this.audioPlayer.playBackgroundMusic(); }
	public void stopMusic() { this.audioPlayer.stopBackgroundMusic(); }

	public boolean isRunning() { return this.running; }
	public void setRunning(boolean running) { this.running = running; }

	public GameOutcome getGameOutcome() { return gameOutcome; }
	public Dimension2D getSize() { return size; }
	public Player getPlayer() { return player; }
	public List<Alien> getAliens() { return aliens; }
	public GameBoardUI getGameBoardUI() {
		return gameBoardUI;
	}
	public KeyListener getKeyListener() { return this.keyListener; }
}
