package main.Controller;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import main.Audio.AudioPlayerInterface;
import main.Controller.Collisions.Collision;
import main.Controller.Collisions.CollisionAlienCannon;
import main.Controller.Collisions.CollisionAlienLaser;
import main.GameEntity.Alien;
import main.GameEntity.Cannon;
import main.GameEntity.Entity;
import main.GameEntity.LaserBolt;
import main.GameEntity.Player;
import main.View.GameBoardUI;
import main.View.GameToolBar;

public class GameBoard implements Observer {

	private static final int UPDATE_PERIOD = 1000 / 25; // The update period of the game in ms, this gives us 25 fps
	private static final int NUMBER_OF_ALIENS = 8;
	private static final double DEFAULT_ALIEN_STEP = 4.5;
	private final static double DEFAULT_CANNON_STEP = 14.0;
	private final static double DEFAULT_BOLT_STEP = -17.0;

	private final static int PLAYER_LIFE_POINTS = 3;
	private AudioPlayerInterface audioPlayer;
	private GameOutcome gameOutcome = GameOutcome.OPEN;

	private final Dimension2D size;

	private boolean running;

	private Timer gameTimer; // Timer responsible for updating the game every frame that runs in a separate
								// thread

	private long shootingCooldownTimestamp = 0;

	// Collision detectors, so we dont need to instantiate them every single time
	private final Collision laserVSalien = new CollisionAlienLaser();
	private final Collision cannonVSalien = new CollisionAlienCannon();

	// Services
	private GameBoardUI gameBoardUI;
	private KeyListener keyListener;

	private List<Alien> aliens = new ArrayList<>();
	private List<LaserBolt> laserBolts = new ArrayList<>();
	private Player player;
	private DataCollector dataCollector = new DataCollector();

	// The time in ms that has to pass, before the next shot can be done
	private static final long CANNON_COOLDOWN_TIMEOUT = 300;

	public GameBoard(Dimension2D size) {
		this.size = size;

		// create entities
		player = new Player(PLAYER_LIFE_POINTS, new Cannon(this.size));
		player.setup();

		// Manually update start position of Cannon
		player.getCannon().setPosition(size.getWidth() / 2.0, size.getHeight() - 200);

		createAliens();

		// create UI and keyListener
		gameBoardUI = new GameBoardUI(this);
		keyListener = new KeyListener();
	}

	/**
	 * Creates Aliens and gives them positions
	 */
	private void createAliens() {
		for (int i = 0; i < NUMBER_OF_ALIENS; i++) {
			aliens.add(new Alien(this.size));

		}
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

	public void onAction(GameToolBar.GAME_TOOLBAR_ACTION action) {
		switch (action) {
		case START -> startGame();
		case STOP -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to stop the game?",
					ButtonType.YES, ButtonType.NO);
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

		updateEntities();
		gameBoardUI.paint();
	}

	public void startGame() {

		dataCollector.setStart(LocalTime.now());

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

		dataCollector.setEnd(LocalTime.now());
		try {
			dataCollector.writeData();
		} catch (Exception e) {
			System.err.println("Couldn't wright data!");
		}

	}

	/**
	 * Moves all Aliens on this game board one step downwards and Cannon too if key
	 * is pressed.
	 */
	void updateEntities() {
		for (Alien alien : aliens) {
			// Firstly, check wether the alien has been shot or not
			boolean collided = false;

			for (LaserBolt lb : laserBolts) {
				if (laserVSalien.detectCollision(lb, alien)) {
					aliens.remove(alien);
					collided = true;
					break;
				}
			}

			// if not, then move alien and check, if an alien collided with the canon
			// BIG TODO: WE normally dont have to check if an alien collides with the
			// cannon, we just have to check if an alien reached the certain y point, if yes
			// we can decrement the life of the player.
			if (!collided) {
				alien.moveVertically(DEFAULT_ALIEN_STEP);

				if (cannonVSalien.detectCollision(player.getCannon(), alien)) {
					player.decrementLifePoints();

					if (player.getPlayerLifePoints() == 0) {
						gameOutcome = GameOutcome.LOST;
						running = false;
					}
				}
			}
		}

		aliens.removeIf(alien -> alien.getPosition().getY() > size.getHeight());

		// Adds a new Alien, if one got shot down, and was therefore removed from the
		// list
		if (aliens.size() < NUMBER_OF_ALIENS)
			aliens.add(new Alien(size));

		for (LaserBolt bolt : laserBolts)
			bolt.moveVertically(DEFAULT_BOLT_STEP);
		laserBolts.removeIf(laserBolt -> laserBolt.getPosition().getY() < -10);

		if (keyListener.getKeysPressed().contains(KeyCode.RIGHT) || keyListener.getKeysPressed().contains(KeyCode.D))
			player.getCannon().moveHorizontally(DEFAULT_CANNON_STEP);

		if (keyListener.getKeysPressed().contains(KeyCode.LEFT) || keyListener.getKeysPressed().contains(KeyCode.A))
			player.getCannon().moveHorizontally(-1 * DEFAULT_CANNON_STEP);

		if (keyListener.getKeysPressed().contains(KeyCode.SPACE)
				&& Instant.now().toEpochMilli() - shootingCooldownTimestamp > CANNON_COOLDOWN_TIMEOUT) {
			shootingCooldownTimestamp = Instant.now().toEpochMilli();
			shoot();
		}
	}

	public void shoot() {
		LaserBolt newBolt = new LaserBolt(size);
		newBolt.setPosition(player.getCannon().getPosition().add(player.getCannon().getSize().scale(0.5).toPoint()));
		this.laserBolts.add(newBolt);
	}

	public AudioPlayerInterface getAudioPlayer() {
		return this.audioPlayer;
	}

	public void setAudioPlayer(AudioPlayerInterface audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public void playMusic() {
		this.audioPlayer.playBackgroundMusic();
	}

	public void stopMusic() {
		this.audioPlayer.stopBackgroundMusic();
	}

	public boolean isRunning() {
		return this.running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public GameOutcome getGameOutcome() {
		return gameOutcome;
	}

	public Dimension2D getSize() {
		return size;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<>();

		entities.addAll(aliens);
		entities.addAll(laserBolts);
		entities.add(player.getCannon());

		return entities;
	}

	public List<Alien> getAliens() {
		return aliens;
	}

	public GameBoardUI getGameBoardUI() {
		return gameBoardUI;
	}

	public KeyListener getKeyListener() {
		return this.keyListener;
	}
}
