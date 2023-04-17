package telran.multithreading;

public class ThreadParams {
int winnerIndex = -1;
int index = 0;
public void setWinnerIndex(int winnerIndex) {
	if (this.winnerIndex < 0) {
		this.winnerIndex = winnerIndex;
	}
}

public int getWinnerIndex() {
	return winnerIndex;
}

public int getIndex() {
	return ++index;
}
}
