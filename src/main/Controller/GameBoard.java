package main.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import main.Audio.AudioPlayer;
import main.Audio.AudioPlayerInterface;
import main.Controller.Collisions.CollisionCannonAlien;
import main.Controller.Collisions.CollisionLaserAlien;
import main.GameEntity.*;
import main.View.GameBoardUI;
import main.View.GameToolBar;

import java.time.Instant;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameBoard implements Observer {

	// TODO: Display in-game score
	// TODO: Display an end score on how boring the lecture was (lecture score)
	// TODO: Change the messages when starting/stopping/loosing and winning the game
	// (more creative)
	// TODO: put the speed of the other entities in their respective classes
	// TODO: Clean up code etc.
	// Optional: Add multiplayer mode (local or even online)
	// Optional: Add items that give the players cannon special abilities
	// Optional: Add a settings menu where things like speed, controls and music
	// volume can be changed
	// Optional: Add other types of aliens
	// Optional: Play animations when aliens/the cannon gets destroyed (I have no
	// idea how to do this)
	// Optional: Make the background dynamic/changing and moving (I have no idea how
	// to do this)

	private static final int UPDATE_PERIOD = 1000 / 25; // The update period of the game in ms, this gives us 25 fps
	private static final int NUMBER_OF_ALIENS = 8;


	private AudioPlayerInterface audioPlayer;
	private GameOutcome gameOutcome = GameOutcome.OPEN;

	private final Dimension2D size;

	private AtomicBoolean running = new AtomicBoolean();

	private Timer gameTimer; // Timer responsible for updating the game every frame that runs in a separate
	// thread

	private long shootingCooldownTimestamp = 0;

	// Collision detectors, so we dont need to instantiate them every single time
	private final CollisionLaserAlien laserVsAlien;
	private final CollisionCannonAlien cannonVsAlien;

	// Services
	private GameBoardUI gameBoardUI;
	private KeyListener keyListener;

	private List<Alien> aliens;
	private List<LaserBolt> laserBolts;
	private Player player;
	private DataCollector dataCollector;

	// The time in ms that has to pass, before the next shot can be done
	private static final long CANNON_COOLDOWN_TIMEOUT = 300;

	public GameBoard(Dimension2D size) {
		if (size.getHeight() <= 0 || size.getWidth() <= 0) {
			throw new IllegalArgumentException();
		}
		this.size = size;
		aliens = new ArrayList<>();
		laserBolts = new ArrayList<>();
		dataCollector = new DataCollector();
		// create entities
		player = new Player(new Cannon(this.size));

		// create UI, audioPlayer and keyListener
		gameBoardUI = new GameBoardUI(this);
		gameBoardUI.widthProperty().set(size.getWidth());
		gameBoardUI.heightProperty().set(size.getHeight());
		
		audioPlayer = new AudioPlayer();
		keyListener = new KeyListener();

		laserVsAlien = new CollisionLaserAlien();
		cannonVsAlien = new CollisionCannonAlien(gameBoardUI.getHeight());

		setup();
	}

	/**
	 * Creates Aliens and gives them positions
	 */
	private void createAliens() {
		for (int i = 0; i < NUMBER_OF_ALIENS; i++) {
			aliens.add(new Alien(this.size));
		}
	}

	private void setup() {
		player.setup();
		aliens.clear();
		laserBolts.clear();
		createAliens();
		player.getCannon().setPosition(size.getWidth() / 2.0, size.getHeight() - 200);
		// Add setup method call here that need to be reset when a game is started (e.g.
		// manual positions or lifepoints of entities)
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
		default -> {
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
		gameBoardUI.getToolBar().updateToolBarStatusText("Lives: " + player.getPlayerLifePoints());
		gameBoardUI.paint();
	}

	public void startGame() {
		gameOutcome = GameOutcome.OPEN;
		dataCollector.setStart(LocalTime.now());
		setup();
		playMusic();
		gameBoardUI.startGame();
		startTimer();
		this.running.set(true);
	}

	/**
	 * Stops the game. Cars stop moving and background music stops playing.
	 */
	public void stopGame() {

		stopMusic();
		gameBoardUI.stopGame();
		this.gameTimer.cancel();
		this.running.set(false);

		dataCollector.setEnd(LocalTime.now());
		try {
			dataCollector.writeData();
		} catch (Exception e) {
			System.err.println("Couldn't write data!");
		}

	}

	/**
	 * Moves all Aliens on this game board one step downwards and Cannon too if key
	 * is pressed.
	 */
	void updateEntities() {

		// Firstly, check whether the alien has been shot or not
		for (LaserBolt lb : laserBolts) {
			aliens.removeIf(alien -> {

				if (laserVsAlien.detectCollision(lb, alien)) {
					audioPlayer.playAlienCrashSound();
					return true;
				}

				return false;
			});
		}
//
//		aliens.removeIf(alien -> alien.getPosition().getY() > size.getHeight());
		final Cannon cannon = player.getCannon();
		aliens.removeIf(alien -> {
			boolean collision = cannonVsAlien.detectCollision(cannon, alien);
			if (collision) {
				player.decrementLifePoints();
				final boolean killed = player.getPlayerLifePoints() == 0;
				audioPlayer.playPlayerCrashSound(killed);

				if (killed) {
					gameOutcome = GameOutcome.LOST;
					this.running.set(false);
				}

			}
			return collision;
		});

		for (Alien alien : aliens) {
			alien.moveVertically();
		}

		// Adds a new Alien, if one got shot down, and was therefore removed from the
		// list
		if (aliens.size() < NUMBER_OF_ALIENS)
			aliens.add(new Alien(size));

		for (LaserBolt bolt : laserBolts)
			bolt.moveVertically();
		laserBolts.removeIf(laserBolt -> laserBolt.getPosition().getY() < -10);

		if (keyListener.getKeysPressed().contains(KeyCode.RIGHT) || keyListener.getKeysPressed().contains(KeyCode.D))
			player.getCannon().moveHorizontally(false);

		if (keyListener.getKeysPressed().contains(KeyCode.LEFT) || keyListener.getKeysPressed().contains(KeyCode.A))
			player.getCannon().moveHorizontally(true);

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

	public AtomicBoolean isRunning() {
		return this.running;
	}

	public void setRunning(boolean running) {
		this.running.set(running);
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
