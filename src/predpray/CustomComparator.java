package predpray;

import java.util.Comparator;

public class CustomComparator implements Comparator<Fox> {
	@Override
	public int compare(Fox arg0, Fox arg1) {
		return arg1.killCount - arg0.killCount;
	}
}
