package com.tavisca.interview.beverageFactory;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class BeverageFactoryTest extends BeverageFactory {

	@Test
	public void testingOrderBill() {
		String order = "\"chai,-milk\",\"coffee\"";
		ArrayList<String> orderItems = extractOrder(order);
		assertEquals(8.0, getTotalBill(orderItems), 0.0);
	}

	@Test
	public void testingOrderBillWithoutExclusions() {
		String order = "\"chai\",\"coffee\"";
		ArrayList<String> orderItems = extractOrder(order);
		assertEquals(9.0, getTotalBill(orderItems), 0.0);
	}

	@Test
	public void testingOrderBillWithoutAnyMenuItem() {
		String order = "\"dummy\"";
		ArrayList<String> orderItems = extractOrder(order);
		boolean thrown = false;

		try {
			getTotalBill(orderItems);
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testingOrderBillWithRepeatedExclusions() {
		String order = "\"coffee,-milk,-milk\"";
		ArrayList<String> orderItems = extractOrder(order);
		assertEquals(4.0, getTotalBill(orderItems), 0.0);
	}

	@Test
	public void testingOrderBillWithWrongExclusions() {
		String order = "\"coffee,-milk,-soda\"";
		ArrayList<String> orderItems = extractOrder(order);
		boolean thrown = false;

		try {
			getTotalBill(orderItems);
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void testingOrderBillWithAllExclusions() {
		String order = "\"Banana Smoothie,-banana,-milk,-sugar,-water\"";
		ArrayList<String> orderItems = extractOrder(order);
		boolean thrown = false;

		try {
			getTotalBill(orderItems);
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

}
