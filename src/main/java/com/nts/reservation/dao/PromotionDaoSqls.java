package com.nts.reservation.dao;

public class PromotionDaoSqls {
	public static final String SELECT_ALL = "SELECT save_file_name promotion_image FROM file_info WHERE id IN (SELECT file_id FROM product_image WHERE product_id IN(SELECT id FROM product WHERE id IN (SELECT product_id FROM promotion)) AND type='th')";
}