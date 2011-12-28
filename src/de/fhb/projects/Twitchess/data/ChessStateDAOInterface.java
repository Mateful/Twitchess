package de.fhb.projects.Twitchess.data;
import java.sql.SQLException;
import java.util.List;

public interface ChessStateDAOInterface {

	public abstract void close() throws SQLException;

	public abstract int countWinsOfPlayer(final String player)
			throws SQLException;

	public abstract List<ChessStateVO> findGameByPlayer(final String playerName)
			throws SQLException;

	public abstract List<ChessStateVO> findNotFinishedGameByPlayer(
			final String playerName) throws SQLException;

	public abstract int insertIntoTable(ChessStateVO vo) throws SQLException;

	public abstract void truncateTable() throws SQLException;

	public abstract void updateTable(final ChessStateVO vo) throws SQLException;

	public abstract void showAll() throws SQLException;

}