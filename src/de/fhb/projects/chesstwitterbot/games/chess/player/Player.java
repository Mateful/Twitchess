package de.fhb.projects.chesstwitterbot.games.chess.player;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.games.chess.figures.Figure;
import de.fhb.projects.chesstwitterbot.games.chess.figures.King;

public final class Player {
	private Color color;
	private List<Figure> figuresInGame;

	public Player(Color color) {
		figuresInGame = new ArrayList<Figure>();
		this.color = color;
	}

	public void add(Figure... figures) {
		for (int i = 0; i < figures.length; i++) {
			figures[i].setColor(color);
			figuresInGame.add(figures[i]);
		}
	}

	public List<Figure> getFiguresInGame() {
		return figuresInGame;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (color != other.color)
			return false;
		if (figuresInGame == null) {
			if (other.figuresInGame != null)
				return false;
		} else if (!figuresInGame.equals(other.figuresInGame))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [color=" + color + ", figuresInGame=" + figuresInGame
				+ "]";
	}
}
