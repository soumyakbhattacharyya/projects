package link.rekruter.candidate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScratchPad {

	public static List<List<String>> accountsMerge(List<List<String>> accounts) {

		Map<String, List<String>> repository = new HashMap<>();

		for (List<String> innerList : accounts) {
			String name = innerList.get(0);
			List<String> emails = innerList.subList(1, innerList.size());
			// does any previous key in the map has any email that matches with the emails
			// we just got
			if (!repository.containsKey(name)) {
				repository.put(name, emails);
			}else {
				List<String> existingEmails = repository.get(name);
				
			}
		}

		return accounts;

	}

	public static void main(String[] args) {
		String[][] accounts1 = { { "John", "johnsmith@mail.com", "john_newyork@mail.com" },
				{ "John", "johnsmith@mail.com", "john00@mail.com" }, { "Mary", "mary@mail.com" },
				{ "John", "johnnybravo@mail.com" } };

		List<List<String>> accounts = listify(accounts1);
		accountsMerge(accounts);
	}

	private static List<List<String>> listify(String[][] accounts) {
		List<List<String>> outerList = new ArrayList<>();
		for (String[] outerElement : accounts) {
			List<String> innerList = new ArrayList<>();
			for (String innerElement : outerElement) {
				innerList.add(innerElement);
			}
			outerList.add(innerList);
		}
		return outerList;
	}

}
