package scanners;

public interface Scanner {
	public void addObserver(ScannerObserver o);
	public void scan();
}
