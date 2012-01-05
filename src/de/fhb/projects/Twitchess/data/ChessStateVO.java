package de.fhb.projects.Twitchess.data;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ChessStateVO {
	private int id;
	private String playerName;
	private String fen;
	private String moves;
	private Date date;
	private Integer result;

	public ChessStateVO(String playerName, String fen, String moves, Date date,
			Integer result) {
		super();
		setPlayerName(playerName);
		setFen(fen);
		setMoves(moves);
		setDate(date);
		setResult(result);
	}

	public ChessStateVO() {
		// nothing to do here
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

	public String getMoves() {
		return moves;
	}

	public void setMoves(String moves) {
		this.moves = moves;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		return "ChessState [id=" + id + ", playerName=" + playerName + ", fen="
				+ fen + ", moves=" + moves + ", date="
				+ ((date == null) ? "null" : dateFormat.format(date))
				+ ", result=" + result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((fen == null) ? 0 : fen.hashCode());
		result = prime * result + id;
		result = prime * result + ((moves == null) ? 0 : moves.hashCode());
		result = prime * result
				+ ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
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

		ChessStateVO other = (ChessStateVO) obj;

		return other.id == id;
	}
}
