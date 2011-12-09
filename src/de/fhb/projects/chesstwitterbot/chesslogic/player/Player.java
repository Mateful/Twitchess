package de.fhb.projects.chesstwitterbot.chesslogic.player;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;

public class Player {
	protected Color color;
	public Player opponent;
	public boolean inCheck;
	protected List<Figure> figuresInGame;

	public Player(Color color) {
		figuresInGame = new ArrayList<Figure>();
		this.color = color;
		inCheck = false;
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
	public String toString() {
		return "Player [color=" + color + ", opponent=" + opponent
				+ ", inCheck=" + inCheck + ", figuresInGame=" + figuresInGame
				+ "]";
	}
}
