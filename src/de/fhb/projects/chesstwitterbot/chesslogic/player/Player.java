package de.fhb.projects.chesstwitterbot.chesslogic.player;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;

public abstract class Player {
	protected Color color;
	public Player opponent;
	public boolean inCheck;
	protected List<Figure> figuresInGame;

	public Player() {
		figuresInGame = new ArrayList<Figure>();
		color = Color.NOCOLOR;
		inCheck = false;
	}

	public void add(Figure figure) {
		figure.color = color;
		figuresInGame.add(figure);
	}
	
	public List<Figure> getFiguresInGame() {
		return figuresInGame;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Position getKing() {
		for(int i = 0; i < figuresInGame.size(); i++)
			if(figuresInGame.get(i) instanceof King)
				return figuresInGame.get(i).position;
		throw new RuntimeException("No King found.");
	}
}
