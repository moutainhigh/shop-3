
public class SQLTest {

	/**
	 * 刷ProdSpec
	 * 
	 * #色丁布   96
select * from shop_product_spec where product_id= 96;
#亚光冰稠布
select * from shop_product_spec where product_id= 100;
#珠光冰丝
select * from shop_product_spec where product_id= 101;
#美人纱 
select * from shop_product_spec where product_id= 102;
#冰绒布
select * from shop_product_spec where product_id= 98;
#绒布 
select * from shop_product_spec where product_id= 97;
#玫瑰花地毯
select * from shop_product_spec where product_id= 103;
#无纺布地毯
select * from shop_product_spec where product_id= 105;
#彩色毛毡地毯
select * from shop_product_spec where product_id= 92;
#珠光地毯
select * from shop_product_spec where product_id= 95;
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "insert into shop_product_spec(product_id, color, size, type, price, now_price, del_flag) values";
//		for (int i = 1; i <= 82; i++) {
//			sql += "(96, '色卡" + i + "', null, '1卷/100米', 2.50, 4.00, 0),";
//		}
		
//		for (int i = 1; i <= 75; i++) {
//			sql += "(100, '色卡" + i + "', null, '1卷/60米', 2.33, 3.73, 0),";
//		}

//		for (int i = 1; i <= 75; i++) {
//			sql += "(101, '色卡" + i + "', null, '1卷/70米', 2.11, 3.38, 0),";
//		}
		
//		for (int i = 1; i <= 10; i++) {
//			sql += "(102, '色卡" + i + "', null, null, 2.80, 4.48, 0),";
//		}
		
//		for (int i = 1; i <= 26; i++) {
//			sql += "(98, '色卡" + i + "', null, null, 7.00, 11.20, 0),";
//		}
		
//		for (int i = 1; i <= 21; i++) {
//			sql += "(97, '色卡" + i + "', null, '薄1卷/90米', 4.50, 7.20, 0),";
//			sql += "(97, '色卡" + i + "', null, '中厚1卷/70米', 6.00, 9.60, 0),";
//			sql += "(97, '色卡" + i + "', null, '厚1卷/50米', 8.00, 12.80, 0),";
//		}
		
//		for (int i = 1; i <= 18; i++) {
//			sql += "(103, '色卡" + i + "', null, null, 9.00, 14.40, 0),";
//		}
		
//		for (int i = 1; i <= 25; i++) {
//			sql += "(105, '色卡" + i + "', null, '1卷/30米', 10.00, 16.00, 0),";
//		}
		
//		for (int i = 1; i <= 17; i++) {
//			sql += "(92, '色卡" + i + "', '1.5米', '50米', 200.00, 290.00, 0),";
//			sql += "(92, '色卡" + i + "', '2米', '50米', 260.00, 377.00, 0),";
//		}
		
		for (int i = 1; i <= 20; i++) {
			sql += "(95, '色卡" + i + "', '1.4米', '皮革底', 10.80, 17.28, 0),";
			sql += "(95, '色卡" + i + "', '1.5米', '无纺布', 10.00, 16.00, 0),";
			sql += "(95, '色卡" + i + "', '1米', '无纺布', 7.00, 11.20, 0),";
			sql += "(95, '色卡" + i + "', '1.5米', '布底', 9.20, 14.72, 0),";
		}
			
		System.out.println(sql);
	}

}
