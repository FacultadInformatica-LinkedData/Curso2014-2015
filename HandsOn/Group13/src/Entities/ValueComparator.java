package Entities;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<Integer>{

	Map<Integer,Sitio> base;
	public ValueComparator(Map<Integer,Sitio> base){
		this.base = base;
	}
	@Override
	public int compare(Integer o1, Integer o2) {
		if(base.get(o1).distance > base.get(o2).distance){
			return 1;
		}
		else if(base.get(o1).distance > base.get(o2).distance){
			return 0;
		}
		else{
			return -1;
		}
	}
}

