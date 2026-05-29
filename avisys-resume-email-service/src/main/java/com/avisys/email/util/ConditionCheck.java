package com.avisys.email.util;

public class ConditionCheck {
	
	private ConditionCheck() {
	}

	public static boolean checkValues(Object obj1, Object obj2) {

//		if ((obj1 == null && obj2 == null) || ((obj2 != null && obj1 == null)) || (obj1.equals(obj2))) {
		try {
			if ((obj1.equals(obj2))) {
				// Returned false as no need to move inside the if condition if any of the above
				// conditions satisfies
				return false;
			}
		} catch (NullPointerException e) {
			if (obj1 == null && obj2 == null) {
				return false;
			} else if (obj1 == null && obj2 != null) {
				return true;
			}
		}
		return true;
	}

	
}
