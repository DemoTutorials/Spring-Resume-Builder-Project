package com.avisys.email.util;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class Utility {

	public List<Order> createSortingList(String[] sort, List<Order> orders) {

		if (sort[0].contains(",")) {
			for (String sortOrder : sort) {
				if (sortOrder.contains(",")) {
					String[] _sort = sortOrder.split(",");
					for (int i = 0; i < _sort.length; i++) {
						orders.add(i, new Order(sort[1].contains("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
								_sort[i]));
					}
				} else {
					break;
				}
			}
		} else {
			orders.add(new Order(sort[1].contains("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sort[0]));
		}
		return orders;
	}

	public String createQueryWithSortOrder(List<Order> orders, String search, String sortByName, String query,
			String tableName) {

		StringBuilder sqlGetAll = null;

		sqlGetAll = new StringBuilder(query);
		sqlGetAll.append((search != null) ? search : "");
		sqlGetAll.append("%' order by " + tableName + ".");

		if (orders.size() > 1) {
			for (int i = 0; i < orders.size(); i++) {
				if (i == 0) {
					sqlGetAll.append("" + orders.get(i).getProperty().trim() + ",");
				} else {
					if (i < orders.size()) {
						sqlGetAll.append(tableName + "." + orders.get(i).getProperty().trim());
					} else {
						sqlGetAll.append(tableName + "." + orders.get(i).getProperty().trim() + " ");
					}
				}
			}
			sqlGetAll.append(" " + sortByName);
		} else {
			sqlGetAll.append(orders.get(0).getProperty().trim() + " " + sortByName);
		}

		sqlGetAll.append(" limit :limit offset :offset");
		return sqlGetAll.toString();
	}
}
