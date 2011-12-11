package de.fhb.projects.chesstwitterbot.games.chess.player;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.games.chess.ChessProperties;
import de.fhb.projects.chesstwitterbot.games.chess.figures.Figure;
import de.fhb.projects.chesstwitterbot.games.chess.figures.King;

public final class Player {
	private Color color;
	private List<Figure> figuresInGame;
	private List<Figure> figuresOutOfGame;

	public Player(Color color) {
		figuresInGame = new ArrayList<Figure>();
		figuresOutOfGame = new ArrayList<Figure>();
		this.color = color;
	}

	public static Player getFullyInitializedPlayer(final Color color) {
		Player player = new Player(color);
		player.add(ChessProperties.getKnight(color));
		player.add(ChessProperties.getQueen(color));
		player.add(ChessProperties.getBishops(color));
		player.add(ChessProperties.getKnight(color));
		player.add(ChessProperties.getRooks(color));
		player.add(ChessProperties.getPawns(color));
		return player;
	}

	public void add(final Figure... figures) {
		for (int i = 0; i < figures.length; i++) {
			figures[i].setColor(color);
			figuresInGame.add(figures[i]);
		}
	}

	public List<Figure> getFiguresInGame() {
		return figuresInGame;
	}

	public List<Figure> getFiguresOutOfGame() {
		return figuresOutOfGame;
	}

	public Color getColor() {
		return color;
	}

	public King getKing() {
		for (int i = 0; i < figuresInGame.size(); i++)
			if (figuresInGame.get(i) instanceof King)
				return (King) figuresInGame.get(i);
		throw new RuntimeException("No King found.");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result
				+ ((figuresInGame == null) ? 0 : figuresInGame.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		Player other = (Player) obj;
		return color == other.color
				&& figuresInGame.equals(other.figuresInGame)
				&& figuresOutOfGame.equals(other.figuresOutOfGame);
	}
	@Override
	public String toString() {
		return "Player [color=" + color + ", figuresInGame=" + figuresInGame
				+ "]";
	}
}
