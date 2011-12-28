package de.fhb.projects.Twitchess.data;

public enum ResultType {
	WHITE_WINS(1), BLACK_WINS(-1), REMIS(0), ABORTED(2);
	
	private int number;
	
	private ResultType(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public static ResultType getResultType(int number) {
		for (ResultType r : ResultType.values()) {
			if (r.getNumber() == number)
				return r;
		}
		
		return null;
	}
}
