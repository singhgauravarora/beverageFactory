package com.tavisca.interview.beverageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.MenuItem;

public class BeverageFactory {

	static HashMap<String, MenuItem> menu = new HashMap<String, MenuItem>();
	static HashMap<String, Double> ingredientMap = new HashMap<String, Double>();
	static {
		initializeMenu();

		initializeIngredientMap();
	}

	public static void runApplication() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your customized order: ");
		String order = sc.nextLine();
		ArrayList<String> orderItems = extractOrder(order);
		double billAmount = getTotalBill(orderItems);
		System.out.println("Total Bill : $" + billAmount);
	}

	public static double getTotalBill(ArrayList<String> orderItems) {
		double billAmount = processbill(orderItems);
		return billAmount;
		
	}

	public static ArrayList<String> extractOrder(String order) {
		ArrayList<String> orderItems = new ArrayList<String>();
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(order);
		while (m.find()) {
			orderItems.add((m.group(1)));
		}
		return orderItems;
	}

	public static void initializeIngredientMap() {
		ingredientMap.put("milk", 1.0);
		ingredientMap.put("sugar", 0.5);
		ingredientMap.put("soda", 0.5);
		ingredientMap.put("mint", 0.5);
		ingredientMap.put("water", 0.5);
	}

	public static void initializeMenu() {
		menu.put("Coffee".toLowerCase(),
				new MenuItem("Coffee", Arrays.asList("coffee", "milk", "sugar", "water"), 5.0));
		menu.put("Chai".toLowerCase(), new MenuItem("Chai", Arrays.asList("Tea", "milk", "sugar", "water"), 4.0));
		menu.put("Banana Smoothie".toLowerCase(),
				new MenuItem("Banana Smoothie", Arrays.asList("banana", "milk", "sugar", "water"), 6.0));
		menu.put("Strawberry Shake".toLowerCase(),
				new MenuItem("Strawberry Shake", Arrays.asList("strawberries", "milk", "sugar", "water"), 7.0));
		menu.put("Mojito".toLowerCase(),
				new MenuItem("Mojito", Arrays.asList("lemon", "soda", "sugar", "water", "mint"), 7.5));
	}

	public static double processbill(ArrayList<String> orderItems) {
		// TODO Auto-generated method stub
		double orderBill = 0.0;
		for (String orderItem : orderItems) {
			orderBill += processOrder(orderItem.toLowerCase());
		}

		return orderBill;
	}

	public static double processOrder(String orderItem) {
		// TODO Auto-generated method stub
		double itemBill = 0.0;
		String[] orderExclusions = orderItem.split(",");
		String menuItem = orderExclusions[0].toLowerCase();
		if (validateOrderItem(menuItem))
			itemBill = menu.get(menuItem).getPrice();
		else
			throw new IllegalArgumentException("Invalid Order");
		itemBill = excludeIngredients(itemBill, orderExclusions, menuItem);
		return itemBill;
	}

	public static double excludeIngredients(double itemBill, String[] orderExclusions, String menuItem) {
		HashSet<String> excludedIngredients = new HashSet<String>();
		for (int i = 1; i < orderExclusions.length; i++) {
			if (orderExclusions[i].trim().startsWith("-")) {
				String excludedIngredient = orderExclusions[i].trim().substring(1).toLowerCase();
				if (!excludedIngredients.contains(excludedIngredient)) {
					if (validateExcludedIngredient(excludedIngredient, menuItem)) {
						itemBill -= ingredientMap.getOrDefault(excludedIngredient, 0.0);
						excludedIngredients.add(excludedIngredient);
					}
					else {
						throw new IllegalArgumentException("Invalid Ingredient" + excludedIngredient );
					}
				}
				if(excludedIngredients.size() == menu.get(menuItem).getIngredients().size())
					throw new  IllegalArgumentException("All Ingredients cannot be excluded");
			} 
		}
		return itemBill;
	}

	public static boolean validateOrderItem(String menuItem) {
		// TODO Auto-generated method stub
		if (menu.get(menuItem) != null)
			return true;
		return false;
	}

	public static boolean validateExcludedIngredient(String excludedIngredient, String menuItem) {
		// TODO Auto-generated method stub
		if (menu.get(menuItem).getIngredients().contains(excludedIngredient))
			return true;
		return false;
	}
}
